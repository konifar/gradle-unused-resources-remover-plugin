package com.konifar.aurr

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.konifar.aurr.databinding.UsedInActivityAsDatabindingBinding

class DataBindingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Test for generated object from layout
        val binding = UsedInActivityAsDatabindingBinding.inflate(layoutInflater)
    }
}
