package com.miniatc.mobile

data class FlightPlan(
    val callsign: String = "AFR123",
    val aircraft: String = "A320",
    val departure: String = "FMEE",
    val destination: String = "FMMI",
    val cruiseAltitude: String = "FL350",
    val route: String = "",
    val departureRunway: String = "",
    val arrivalRunway: String = ""
) {
    fun isValid(): Boolean {
        return callsign.isNotBlank() &&
            aircraft.isNotBlank() &&
            departure.matches(Regex("[A-Z]{4}")) &&
            destination.matches(Regex("[A-Z]{4}")) &&
            departure != destination &&
            cruiseAltitude.isNotBlank()
    }

    fun summary(): String {
        return """
            CALLSIGN: $callsign
            AIRCRAFT: $aircraft
            DEPARTURE: $departure
            DESTINATION: $destination
            CRUISE ALTITUDE: $cruiseAltitude
            ROUTE: ${route.ifBlank { "NOT SPECIFIED" }}
        """.trimIndent()
    }
}
