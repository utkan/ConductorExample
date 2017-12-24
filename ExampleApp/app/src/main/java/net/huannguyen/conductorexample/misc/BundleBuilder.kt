package net.huannguyen.conductorexample.misc

import android.os.Bundle
import android.os.Parcelable

class BundleBuilder(val bundle: Bundle) {
    fun putParcelable(key: String, value: Parcelable): BundleBuilder {
        bundle.putParcelable(key, value)
        return this
    }

    fun build(): Bundle {
        return bundle
    }
}