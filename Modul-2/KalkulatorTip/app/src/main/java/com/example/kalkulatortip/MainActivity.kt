package com.example.kalkulatortip

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.kalkulatortip.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf("15%", "18%", "20%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        (binding.tipOptions as? AutoCompleteTextView)?.setAdapter(adapter)
        displayTip(0.0)
        binding.costOfServiceEditText.doOnTextChanged { _, _, _, _ ->
            calculateTip()
        }

        binding.roundUpSwitch.setOnCheckedChangeListener { _, _ ->
            calculateTip()
        }

        binding.tipOptions.setOnItemClickListener { _, _, _, _ ->
            calculateTip()
        }
    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        val selectedText = binding.tipOptions.text.toString()
        val tipPercentage = when (selectedText) {
            "20%" -> 0.20
            "18%" -> 0.18
            else -> 0.15
        }

        var tip = tipPercentage * cost
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}