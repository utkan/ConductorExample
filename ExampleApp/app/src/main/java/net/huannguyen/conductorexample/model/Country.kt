package net.huannguyen.conductorexample.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class Country(
        val name: String,
        val capital: String,
        val population: Long,
        val flag: String,
        val language: String,
        val currency: String,
        val timezone: String
) : PaperParcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelCountry.CREATOR
    }
}
