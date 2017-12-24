package net.huannguyen.conductorexample.countrygrid

import net.huannguyen.conductorexample.model.Country

interface GridEventHandler {
    fun onCountryClicked(country: Country)
}