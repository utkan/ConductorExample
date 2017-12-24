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
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.transition.TransitionValues
import android.transition.Visibility
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class Scale : Visibility {

    constructor() {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onAppear(sceneRoot: ViewGroup, view: View, startValues: TransitionValues, endValues: TransitionValues): Animator {
        return getAnimator(view, 0f, 1f)
    }

    override fun onDisappear(sceneRoot: ViewGroup, view: View, startValues: TransitionValues, endValues: TransitionValues): Animator {
        return getAnimator(view, 1f, 0f)
    }

    private fun getAnimator(view: View, startValue: Float, endValue: Float): Animator {
        view.scaleX = startValue
        view.scaleY = startValue
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, startValue, endValue)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, startValue, endValue)
        return ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY)
    }
}