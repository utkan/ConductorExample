package net.huannguyen.conductorexample.countrygrid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat
import net.huannguyen.conductorexample.R
import net.huannguyen.conductorexample.controller.BaseController
import net.huannguyen.conductorexample.countrydetail.CountryDetailController
import net.huannguyen.conductorexample.model.Country
import net.huannguyen.conductorexample.transition.DetailPopAnimChangeHandler
import net.huannguyen.conductorexample.transition.DetailPopTransChangeHandler
import net.huannguyen.conductorexample.transition.DetailPushAnimChangeHandler
import net.huannguyen.conductorexample.transition.DetailPushTransChangeHandler

class CountryGridController(args: Bundle?) : BaseController(args), GridEventHandler {
    constructor() : this(null)

    override fun getTitle() = activity?.getString(R.string.countries) ?: ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.country_grid, container, false) as CountryGridView
        view.setEventHandler(this)
        return view
    }

    override fun onCountryClicked(country: Country) {
        // For demo purposes, use animator change handler for countries with name starting with
        // a character before 'i' in the alphabet. For other countries, use transition change handler if the app is running on
        // API level 21+. Use the mentioned animator change handler otherwise.
        val countryNameFirstCharBeforeI = country.name.toLowerCase()[0] < 'i'

        val pushHandler = if (countryNameFirstCharBeforeI)
            DetailPushAnimChangeHandler()
        else
            TransitionChangeHandlerCompat(DetailPushTransChangeHandler(country.name), DetailPushAnimChangeHandler())

        val popHandler = if (countryNameFirstCharBeforeI)
            DetailPopAnimChangeHandler()
        else
            TransitionChangeHandlerCompat(DetailPopTransChangeHandler(country.name), DetailPopAnimChangeHandler())

        router.pushController(RouterTransaction.with(CountryDetailController(country))
                .pushChangeHandler(pushHandler)
                .popChangeHandler(popHandler))
    }
}