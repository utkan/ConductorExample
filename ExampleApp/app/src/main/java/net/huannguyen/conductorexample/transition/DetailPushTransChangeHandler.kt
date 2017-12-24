/*
 *
 *  * Copyright (C) 2017 Huan Nguyen.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package net.huannguyen.conductorexample.transition

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.transition.*
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandler
import kotlinx.android.synthetic.main.country_detail.view.*
import net.huannguyen.conductorexample.countrydetail.CountryDetailView

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class DetailPushTransChangeHandler(flagViewTransitionName: String) : TransitionChangeHandler() {

    private var flagViewTransitionName: String? = flagViewTransitionName

    override fun saveToBundle(bundle: Bundle) {
        bundle.putString(KEY_FLAG_TRANSITION_NAME, flagViewTransitionName)
    }

    override fun restoreFromBundle(bundle: Bundle) {
        flagViewTransitionName = bundle.getString(KEY_FLAG_TRANSITION_NAME)
    }

    override fun getTransition(container: ViewGroup,
                               from: View?,
                               to: View?,
                               isPush: Boolean): Transition {

        if (to == null || to !is CountryDetailView) {
            throw IllegalArgumentException("The to view must be a CountryDetailView")
        }

        to.flag.transitionName = flagViewTransitionName

        val changeTransform = ChangeTransform()

        // Shared elements (the flag view in this case) are drawn in the window's view overlay during the transition by default.
        // That causes the favourite fab being drawn behind the flag when it is scaled up.
        // Setting the change transform not using overlay addresses this issue.
        changeTransform.reparentWithOverlay = false

        return TransitionSet()
                .addTransition(TransitionSet()
                        .addTransition(ChangeBounds())
                        .addTransition(ChangeClipBounds())
                        .addTransition(changeTransform)
                        .addTransition(ChangeImageTransform())
                        .setDuration(300))
                .addTransition(Slide().addTarget(to.detail_group).setStartDelay(150))
                .addTransition(Scale().addTarget(to.favourite_fab).setStartDelay(300))
                .setInterpolator(FastOutSlowInInterpolator())
    }

    companion object {
        private val KEY_FLAG_TRANSITION_NAME = "key_flag_transition_name"
    }
}
