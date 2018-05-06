package com.konifar.aurr

import android.animation.AnimatorInflater
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.used_in_fragment_layout, container, false)

        val wrappedContext = ContextThemeWrapper(context, R.style.UsedInKotlin)
        val textView = TextView(wrappedContext, null, 0)
        textView.setText(R.string.used_in_kotlin)
        textView.setTextColor(ContextCompat.getColor(context!!, R.color.used_in_kotlin_color))

        textView.isEnabled = resources.getBoolean(R.bool.used_in_kotlin_bool)

        val padding = resources.getDimension(R.dimen.used_in_kotlin_dimen).toInt()
        textView.setPadding(padding, padding, padding, padding)

        AnimatorInflater.loadAnimator(context, R.animator.used_in_kotlin_animator)

        return view
    }

}
