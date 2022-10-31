package flame.customers

import bitframe.actor.Comm
import bitframe.actor.UNSET
import geo.Country
import kotlinx.collections.interoperable.List
import kotlinx.collections.interoperable.iListOf
import kotlinx.collections.interoperable.iMutableListOf
import kotlinx.collections.interoperable.toInteroperableList
import kotlinx.datetime.TimeZone
import geo.GeoLocation
import geo.LatLng

private fun PiOneCustomer.comms(): List<Comm> = setOf(email, mobile, telephone).mapNotNull {
    try {
        Comm.of(it ?: error(0), UNSET)
    } catch (err: Throwable) {
        null
    }
}.toInteroperableList()

private fun PiOneCustomer.coords(): LatLng? {
    val loc = locationData ?: return null
    val lat = loc.latitude ?: return null
    val lng = loc.longitude ?: return null
    return LatLng(lat, lng)
}

private fun PiOneCustomer.country(): String {
//    val addr = locationData?.address ?: country
//    return addr?.split(", ")?.lastOrNull() ?: "ZA"
    return country ?: "ZA"
}

private fun PiOneCustomer.location(): GeoLocation {
    val addr = locationData?.address ?: address
    val splits = addr?.split(", ")?.toInteroperableList() ?: iListOf()
    val cntry = country()
    val lines = (splits.toSet() - cntry)
    return GeoLocation(
        lines = lines.toInteroperableList(),
        country = Country.valueOf(cntry),
        cords = coords()
    )
}

private fun PiOneCustomer.toIndividualCustomer() = IndividualCustomer(
    uid = id.toString(),
    name = name,
    title = null,
    dob = dateOfBirth?.toLocalDate(TimeZone.UTC), // TODO: Use the company's timezone
    gender = null,
    comms = comms(),
    gid = number ?: UNSET,
    idDocumentNumber = idNumber,
    idDocumentType = DocumentType.UNKNOWN,
    location = location()
)

private fun PiOneCustomer.contacts(): List<Contact> {
    val cts = iMutableListOf<Contact>()
    val cms = comms()
    if (pointOfContact == null && cms.isEmpty()) {
        return cts
    }
    cts.add(Contact(name = pointOfContact ?: "Unknown", comms = cms))
    return cts
}

private fun PiOneCustomer.toCorporateCustomer() = CorporateCustomer(
    uid = id.toString(),
    name = name,
    headQuarters = CorporateBranch(
        name = "Head Quarters",
        contacts = contacts(),
        location = location()
    ),
    registrationNo = registrationNumber,
    registrationDate = dateOfBirth?.toLocalDate(TimeZone.UTC), // TODO: Use the company's timezone
    taxPayerIdentificationNo = null,
    vatNo = vatNumber,
    website = website,
    type = when (customerType) {
        "COMPANY" -> CorporateCustomer.Type.COMPANY
        "GOVERNMENT" -> CorporateCustomer.Type.GOVERNMENT_INSTITUTION
        "NGO" -> CorporateCustomer.Type.NGO
        else -> throw IllegalStateException("Corporate customer type can never be null")
    },
    industry = Industry.values().firstOrNull { it.name.contentEquals(industry, ignoreCase = true) },
    gid = number ?: UNSET
)

fun PiOneCustomer.toCustomer(): Customer = when (customerType) {
    in CorporateCustomer.Type.values().map { it.name } -> toCorporateCustomer()
    else /* INDIVIDUAL */ -> toIndividualCustomer()
}