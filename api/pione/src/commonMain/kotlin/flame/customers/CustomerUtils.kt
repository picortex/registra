package flame.customers

import flame.customers.params.CorporateCustomerParams
import flame.customers.params.IndividualCustomerParams
import kotlinx.datetime.TimeZone
import kotlinx.serialization.json.Json
import geo.GeoLocation

private fun GeoLocation.toPiOneAddress() = "$address, $country"
private fun GeoLocation.toPiOneLocation() = PiOneLocation(
    latitude = cords?.lat,
    longitude = cords?.lng,
    address = toPiOneAddress()
)

private fun GeoLocation.toPiOneJSONLocation() = Json.encodeToString(PiOneLocation.serializer(), toPiOneLocation())

private fun IndividualCustomer.toPiOneCustomer() = PiOneCustomer(
    id = uid.toInt(),
    name = name,
    address = location?.toPiOneAddress(),
    country = location?.country?.code,
    customerType = "INDIVIDUAL",
    dateOfBirth = dob?.toPiOneDate(TimeZone.UTC), // TODO: Use customer's timezone
    number = gid,
    idNumber = idDocumentNumber,
    locationData = location?.toPiOneLocation(),
    email = emails.firstOrNull()?.value,
    mobile = phones.getOrNull(0)?.value,
    telephone = phones.getOrNull(1)?.value,
    industry = null,
    website = null,
    pointOfContact = null,
    vatNumber = null,
    registrationNumber = null,
    location = location?.toPiOneJSONLocation(),
)

private fun CorporateCustomer.toPiOneCustomer() = PiOneCustomer(
    id = uid.toInt(),
    name = name,
    address = headQuarters.location?.toPiOneAddress(),
    country = headQuarters.location?.country?.code,
    customerType = type.name,
    number = gid,
    idNumber = null,
    dateOfBirth = registrationDate?.toPiOneDate(TimeZone.UTC), // TODO: Use customer's timezone
    industry = industry?.name,
    website = website,
    pointOfContact = headQuarters.contacts.firstOrNull()?.name,
    email = headQuarters.contacts.firstOrNull()?.emails?.firstOrNull()?.value,
    mobile = headQuarters.contacts.firstOrNull()?.phones?.getOrNull(0)?.value,
    telephone = headQuarters.contacts.firstOrNull()?.phones?.getOrNull(1)?.value,
    vatNumber = vatNo,
    registrationNumber = registrationNo,
    locationData = headQuarters.location?.toPiOneLocation(),
    location = headQuarters.location?.toPiOneJSONLocation(),
)

internal fun Customer.toPiOneCustomer(): PiOneCustomer = when (this) {
    is CorporateCustomer -> toPiOneCustomer()
    is IndividualCustomer -> toPiOneCustomer()
}

internal fun IndividualCustomerParams.toPiOneCustomer(id: String? = null) = PiOneCustomer(
    id = id?.toIntOrNull(),
    name = name,
    address = location?.toPiOneAddress(),
    country = location?.country?.code,
    customerType = "INDIVIDUAL",
    dateOfBirth = dob?.toPiOneDate(TimeZone.UTC), // TODO: Use customer's timezone
    number = null,
    idNumber = idDocumentNumber,
    locationData = location?.toPiOneLocation(),
    email = email,
    mobile = phone,
    telephone = phone,
    industry = null,
    website = null,
    pointOfContact = null,
    vatNumber = null,
    registrationNumber = null,
    location = location?.toPiOneJSONLocation(),
)

internal fun CorporateCustomerParams.toPiOneCustomer(id: String? = null) = PiOneCustomer(
    id = id?.toIntOrNull(),
    name = name,
    address = hqLocation?.toPiOneAddress(),
    country = hqLocation?.country?.code,
    customerType = businessType?.name,
    dateOfBirth = registrationDate?.toPiOneDate(TimeZone.UTC), // TODO: Use customer's timezone
    number = null,
    idNumber = null,
    locationData = hqLocation?.toPiOneLocation(),
    email = contactEmail,
    mobile = contactPhone,
    telephone = contactPhone,
    industry = industry?.name,
    website = website,
    pointOfContact = contactName,
    vatNumber = vat,
    registrationNumber = registrationNo,
    location = hqLocation?.toPiOneJSONLocation(),
)