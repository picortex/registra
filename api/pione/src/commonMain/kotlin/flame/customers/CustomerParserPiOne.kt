package flame.customers

import kotlinx.collections.interoperable.toInteroperableList
import kotlinx.serialization.json.JsonObject
import pione.PiOneParser

class CustomerParserPiOne : PiOneParser() {
//
//    inline fun JsonObject.toCustomerLocationOrNull() = try {
//        toGeoLocation()
//    } catch (_: Throwable) {
//        null
//    }
//
//    inline fun JsonObject.toGeoLocation(): GeoLocation? {
//        val splits = address?.split(", ") ?: return null
//        val country = splits.lastOrNull() ?: return null
//        val lines = (splits.toSet() - country)
//        return GeoLocation(
//            country = country,
//            lines = lines.toInteroperableList(),
//            coords = toCoordinateOrNull()
//        )
//    }
//
//    inline fun JsonObject.toCoordinate() = GeoLocation.Coordinate(
//        lat = latitude,
//        lng = longitude
//    )
//
//    inline fun JsonObject.toCoordinateOrNull() = try {
//        toCoordinate()
//    } catch (_: Throwable) {
//        null
//    }
//
//    private fun JsonObject.toIndividualCustomer() = IndividualCustomer(
//        uid = id,
//        name = name,
//        gid = number ?: "",
//        dob = dateOfBirth,
//        location = locationData?.toCustomerLocationOrNull()
//    )
//
//    private fun JsonObject.toCorporateCustomer() = CorporateCustomer(
//        uid = id,
//        name = name,
//        registrationNo = registrationNumber,
//        taxPayerIdentificationNo = vatNumber,
//        industry = Industry.values().first { it.name.contentEquals(industry, ignoreCase = true) },
//        website = website,
//        headQuarters = CorporateBranch(
//            name = "Head Quarters",
//            location = locationData?.toCustomerLocationOrNull()
//        )
//    )
//
//    private fun JsonObject.toCustomer(): Customer = when (customerType) {
//        "COMPANY", "GOVERNMENT", "NGO" -> toCorporateCustomer()
//        else /* INDIVIDUAL */ -> toIndividualCustomer()
//    }
//
//    fun parseCustomers(objects: Map<String, JsonObject>) = objects.values.map {
//        it.toCustomer()
//    }.toInteroperableList()
//
//    fun parseCustomer(res: JsonObject) = res.toCustomer()
}