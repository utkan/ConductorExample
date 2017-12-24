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
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.view.View
import android.view.ViewGroup

import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler
import kotlinx.android.synthetic.main.country_detail.view.*

import net.huannguyen.conductorexample.countrydetail.CountryDetailView

class DetailPopAnimChangeHandler : AnimatorChangeHandler() {

    override fun getAnimator(container: ViewGroup,
                             from: View?,
                             to: View?,
                             isPush: Boolean,
                             toAddedToContainer: Boolean): Animator {
        // Make sure the from view is a CountryDetailView
        if (from == null || from !is CountryDetailView)
            throw IllegalArgumentException("The from view must be a CountryDetailView")

        if (to == null)
            throw IllegalArgumentException("The to view must not be null")

        val detailView = from as CountryDetailView

        val animatorSet = AnimatorSet()

        // Set the to View's alpha to 0 to hide it at the beginning.
        to?.alpha = 0f

        // Scale down to hide the fab button
        val fabScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0F)
        val fabScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0F)
        val hideFabButtonAnimator = ObjectAnimator.ofPropertyValuesHolder(detailView.favourite_fab, fabScaleX, fabScaleY)

        // Slide up the flag
        val flagAnimator = ObjectAnimator.ofFloat(detailView.flag, View.TRANSLATION_Y, 0F, -detailView.flag.height.toFloat())

        // Slide down the details
        val detailAnimator = ObjectAnimator.ofFloat(detailView.detail_group, View.TRANSLATION_Y, 0F, detailView.detail_group.height.toFloat())

        // Show the new view
        val showToViewAnimator = ObjectAnimator.ofFloat<View>(to, View.ALPHA, 0F, 1F)

        animatorSet.playTogether(hideFabButtonAnimator, flagAnimator, detailAnimator, showToViewAnimator)
        animatorSet.duration = 300
        animatorSet.interpolator = FastOutLinearInInterpolator()

        animatorSet.start()

        return animatorSet
    }

    override fun resetFromView(from: View) {
        val detailView = from as CountryDetailView
        detailView.favourite_fab.scaleX = 1F
        detailView.favourite_fab.scaleY = 1F
        detailView.flag.translationY = 0F
        detailView.detail_group.translationY = 0F
    }
}
