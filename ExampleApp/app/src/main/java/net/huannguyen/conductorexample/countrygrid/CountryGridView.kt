package net.huannguyen.conductorexample.countrygrid

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import net.huannguyen.conductorexample.R
import net.huannguyen.conductorexample.misc.FourThreeImageView
import net.huannguyen.conductorexample.model.Country
import java.util.*

class CountryGridView(context: Context?, attrs: AttributeSet?) : RecyclerView(context, attrs) {
    init {
        layoutManager = GridLayoutManager(getContext(),
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3)
        adapter = CountryAdapter(COUNTRIES)
    }

    // Note: Having the controller implementing an interface and pass its reference to this View to handle navigation
    // upon clicks for demo purposes.
    // A nicer way of doing this is using RxJava to turn view clicks into a stream which is then observed by a Presenter
    // declared in the Controller. The Presenter then determines what should be done to response to clicks.
    private var eventHandler: GridEventHandler? = null

    fun setEventHandler(eventHandler: GridEventHandler) {
        this.eventHandler = eventHandler
    }

    private fun onCountryClicked(country: Country) {
        eventHandler?.onCountryClicked(country)
    }

    private inner class CountryAdapter internal constructor(private val countries: List<Country>?) : RecyclerView.Adapter<CountryViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
            return CountryViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
            holder.bindData(countries!![position])
        }

        override fun getItemCount(): Int {
            return countries?.size ?: 0
        }
    }

    internal inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var country: Country? = null

        private val name: TextView by lazy {
            itemView.findViewById(R.id.name) as TextView
        }

        private val flag: FourThreeImageView by lazy {
            itemView.findViewById(R.id.flag) as FourThreeImageView
        }

        fun bindData(country: Country) {
            this.country = country
            Picasso.with(itemView.context)
                    .load(country.flag)
                    .into(flag)
            name.text = country.name

            // Set transition name for flag view to enable transition animation.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                flag.transitionName = country.name
            }
            flag.setOnClickListener({
                onCountryClicked(country)
            })
        }
    }

    companion object {
        // Assume there is a list of countries that has already been obtained.
        // Data referenced from restcountries.eu. Flag images from flagpedia.net.
        private val COUNTRIES = Arrays.asList(
                Country("Australia", "Canberra", 24117360, "https://flagpedia.net/data/flags/normal/au.png", "English", "Australian dollar", "UTC+10:00"),
                Country("Finland", "Helsinki", 5491817, "https://flagpedia.net/data/flags/normal/fi.png", "Finish", "Euro", "UTC+02:00"),
                Country("France", "Paris", 66710000, "https://flagpedia.net/data/flags/normal/fr.png", "French", "Euro", "UTC+01:00"),
                Country("Germany", "Berlin", 81770900, "https://flagpedia.net/data/flags/normal/de.png", "German", "Euro", "UTC+01:00"),
                Country("Greece", "Athens", 10858018, "https://flagpedia.net/data/flags/normal/gr.png", "Greek", "Euro", "UTC+02:00"),
                Country("Hungary", "Budapest", 9823000, "https://flagpedia.net/data/flags/normal/hu.png", "Hungarian", "Hungarian forint", "UTC+01:00"),
                Country("Iceland", "Reykjavík", 334300, "https://flagpedia.net/data/flags/normal/is.png", "Icelandic", "Icelandic króna", "UTC+00:00"),
                Country("Ireland", "Dublin", 6378000, "https://flagpedia.net/data/flags/normal/ie.png", "Irish", "Euro", "UTC+00:00"),
                Country("Italy", "Rome", 60665551, "https://flagpedia.net/data/flags/normal/it.png", "Italian", "Euro", "UTC+01:00"),
                Country("Luxembourg", "Luxembourg", 576200, "https://flagpedia.net/data/flags/normal/lu.png", "Luxembourgish", "Euro", "UTC+01:00"),
                Country("Netherlands", "Amsterdam", 17019800, "https://flagpedia.net/data/flags/normal/nl.png", "Dutch", "Euro", "UTC+01:00"),
                Country("Norway", "Oslo", 5223256, "https://flagpedia.net/data/flags/normal/no.png", "Norwegian", "Norwegian krone", "UTC+01:00"),
                Country("Portugal", "Lisbon", 10374822, "https://flagpedia.net/data/flags/normal/pt.png", "Portuguese", "Euro", "UTC+00:00"),
                Country("United Kingdom", "London", 65110000, "https://flagpedia.net/data/flags/normal/gb.png", "English", "Pound", "UTC+00:00"))

    }
}