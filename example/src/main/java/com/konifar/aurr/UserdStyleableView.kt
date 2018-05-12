package com.konifar.aurr

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class UserdStyleableView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var test: String = ""

    init {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.UsedStyleable, 0, 0)
        try {
            test = ta.getString(R.styleable.UsedStyleable_usedString)
        } finally {
            ta.recycle()
        }
    }

}