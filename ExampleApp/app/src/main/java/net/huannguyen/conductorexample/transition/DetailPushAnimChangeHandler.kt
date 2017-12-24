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

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewGroup

import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler
import kotlinx.android.synthetic.main.country_detail.view.*

import net.huannguyen.conductorexample.countrydetail.CountryDetailView

class DetailPushAnimChangeHandler : AnimatorChangeHandler() {

    override fun getAnimator(container: ViewGroup,
                             from: View?,
                             to: View?,
                             isPush: Boolean,
                             toAddedToContainer: Boolean): Animator {

        // Make sure the to view is a CountryDetailView
        if (to == null || to !is CountryDetailView)
            throw IllegalArgumentException("The to view must be a CountryDetailView")

        // Set the button scale to 0 to make it invisible at the beginning.
        to.favourite_fab.scaleX = 0F
        to.favourite_fab.scaleY = 0F

        val animatorSet = AnimatorSet()

        val flagAndDetailAnim = AnimatorSet()

        // Hide the old view
        val hideFromViewAnim = ObjectAnimator.ofFloat<View>(from, View.ALPHA, 1F, 0F)

        // Slide down the flag
        val flagAnim = ObjectAnimator.ofFloat(to.flag, View.TRANSLATION_Y, -to.flag.height.toFloat(), 0F)

        // Slide up the details
        val detailAnim = ObjectAnimator.ofFloat(to.detail_group, View.TRANSLATION_Y, to.detail_group.height.toFloat(), 0F)

        flagAndDetailAnim.playTogether(hideFromViewAnim, flagAnim, detailAnim)
        flagAndDetailAnim.duration = 300
        flagAndDetailAnim.interpolator = FastOutSlowInInterpolator()

        // Scale up the favourite fab
        val fabScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0F, 1F)
        val fabScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0F, 1F)
        val favouriteAnim = ObjectAnimator.ofPropertyValuesHolder(to.favourite_fab, fabScaleX, fabScaleY)
                .setDuration(200)

        animatorSet.playSequentially(flagAndDetailAnim, favouriteAnim)

        animatorSet.start()

        return animatorSet
    }

    override fun resetFromView(from: View) {
        from.alpha = 1f
    }
}
