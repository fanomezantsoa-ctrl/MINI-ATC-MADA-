package com.miniatc.mobile

enum class AtcStation {
    GROUND,
    TOWER,
    DEPARTURE,
    APPROACH
}

data class AtcMessage(
    val station: AtcStation,
    val controller: String,
    val message: String
)

class AtcEngine {

    fun initialCall(
        plan: FlightPlan,
        station: AtcStation
    ): AtcMessage {

        val message = when (station) {

            AtcStation.GROUND ->
                "${plan.departure} Ground, ${plan.callsign}, " +
                "stand, request taxi for departure to ${plan.destination}."

            AtcStation.TOWER ->
                "${plan.departure} Tower, ${plan.callsign}, " +
                "holding short of the runway, ready for departure."

            AtcStation.DEPARTURE ->
                "${plan.departure} Departure, ${plan.callsign}, " +
                "passing 3000 feet, climbing."

            AtcStation.APPROACH ->
                "${plan.destination} Approach, ${plan.callsign}, " +
                "requesting approach clearance, information unavailable."

        }

        return AtcMessage(
            station = station,
            controller = station.name,
            message = message
        )
    }

    fun controllerReply(
        plan: FlightPlan,
        station: AtcStation
    ): AtcMessage {

        val message = when (station) {

            AtcStation.GROUND ->
                "${plan.callsign}, taxi via assigned taxiway, " +
                "hold short of the runway. " +
                "Confirm you have received the taxi instructions."

            AtcStation.TOWER ->
                "${plan.callsign}, hold position. " +
                "Runway and traffic conditions are being checked."

            AtcStation.DEPARTURE ->
                "${plan.callsign}, radar contact. " +
                "Continue climb as assigned. Report passing your cleared altitude."

            AtcStation.APPROACH ->
                "${plan.callsign}, maintain present altitude. " +
                "Expect further approach instructions."

        }

        return AtcMessage(
            station = station,
            controller = station.name,
            message = message
        )
    }

    fun frequencyChange(
        plan: FlightPlan,
        nextStation: AtcStation
    ): AtcMessage {

        val message = when (nextStation) {

            AtcStation.TOWER ->
                "${plan.callsign}, contact Tower on the assigned frequency."

            AtcStation.DEPARTURE ->
                "${plan.callsign}, contact Departure on the assigned frequency."

            AtcStation.APPROACH ->
                "${plan.callsign}, contact Approach on the assigned frequency."

            AtcStation.GROUND ->
                "${plan.callsign}, contact Ground on the assigned frequency."
        }

        return AtcMessage(
            station = nextStation,
            controller = nextStation.name,
            message = message
        )
    }
}
