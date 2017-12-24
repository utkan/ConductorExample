package net.huannguyen.conductorexample.controller

import android.os.Bundle
import android.view.View
import com.bluelinelabs.conductor.Controller
import net.huannguyen.conductorexample.activity.MainActivity

abstract class BaseController(args: Bundle?) : Controller(args) {
    override fun onAttach(view: View) {
        super.onAttach(view)
        // Quick way to access the toolbar for demo purposes. Production app needs to have this done properly
        val mainActivity = activity as MainActivity
        // Activity should have already been set after the conductor is attached.
        mainActivity.setToolBarTitle(getTitle())
        mainActivity.enableUpArrow(router.backstackSize > 1)

    }

    abstract fun getTitle(): String
}