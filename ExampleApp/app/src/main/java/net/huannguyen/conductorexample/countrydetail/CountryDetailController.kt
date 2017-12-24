package net.huannguyen.conductorexample.countrydetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.huannguyen.conductorexample.R
import net.huannguyen.conductorexample.controller.BaseController
import net.huannguyen.conductorexample.misc.BundleBuilder
import net.huannguyen.conductorexample.model.Country

class CountryDetailController(args: Bundle?) : BaseController(args), DetailEventHandler {
    constructor(c: Country):this(BundleBuilder(Bundle())
            .putParcelable(KEY_COUNTRY, c)
            .build())
    private val country = getArgs().getParcelable<Country>(KEY_COUNTRY)

    override fun getTitle() = country.name

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.country_detail, container, false) as CountryDetailView
        view.setEventHandler(this)
        view.setData(country)
        return view
    }

    override fun onFlagClicked() {
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:0,0?q=%s", country.name)))
        mapIntent.`package` = "com.google.android.apps.maps"
        activity?.let {
            mapIntent.resolveActivity(it.packageManager)?.let {
                startActivity(mapIntent)
            }
        }
    }

    companion object {
        private val KEY_COUNTRY = "KEY_COUNTRY"
    }
}