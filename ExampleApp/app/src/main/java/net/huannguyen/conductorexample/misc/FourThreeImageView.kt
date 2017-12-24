package net.huannguyen.conductorexample.misc

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View.MeasureSpec.*

class FourThreeImageView(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val fourThreeHeight = makeMeasureSpec(getSize(widthMeasureSpec) * 3 / 4, EXACTLY)
        super.onMeasure(widthMeasureSpec, fourThreeHeight)
    }
}