package net.huannguyen.conductorexample.countrydetail

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.country_detail.view.*
import net.huannguyen.conductorexample.model.Country

class CountryDetailView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    // Note: Having the controller implementing an interface and pass its reference to this View to handle navigation
    // upon clicks for demo purposes.
    // A nicer way of doing this is using RxJava to turn view clicks into a stream which is then observed by a Presenter
    // declared in the Controller. The Presenter then determines what should be done to response to clicks.
    private var eventHandler: DetailEventHandler? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        flag.setOnClickListener {
            eventHandler?.onFlagClicked()
        }
    }

    fun setEventHandler(eventHandler: DetailEventHandler) {
        this.eventHandler = eventHandler
    }

    fun setData(country: Country) {
        Picasso.with(context)
                .load(country.flag)
                .into(flag)

        capital.text = country.capital
        population.text = country.population.toString()
        language.text = country.language
        currency.text = country.currency
        timezone.text = country.timezone
    }
}