package com.example.places.carappservice

import androidx.car.app.CarAppService
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator

class PlacesCarAppService : CarAppService() {

    override fun createHostValidator(): HostValidator {
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    }

    override fun onCreateSession(): Session {
        // PlacesSession will be an unresolved reference until the next step
        return PlacesSession()
    }
}