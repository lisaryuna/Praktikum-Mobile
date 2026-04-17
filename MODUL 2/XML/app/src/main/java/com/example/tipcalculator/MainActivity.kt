package com.example.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var etBillAmount: TextInputEditText
    private lateinit var spinnerTipPercentage: com.google.android.material.textfield.MaterialAutoCompleteTextView
    private lateinit var switchRoundUp: MaterialSwitch
    private lateinit var tvTipResult: TextView

    private var currentTipPercentage = 15.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBillAmount = findViewById(R.id.etBillAmount)
        spinnerTipPercentage = findViewById(R.id.spinnerTipPercentage)
        switchRoundUp = findViewById(R.id.switchRoundUp)
        tvTipResult = findViewById(R.id.tvTipResult)

        val tipOptions = arrayOf("15%", "18%", "20%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tipOptions)
        spinnerTipPercentage.setAdapter(adapter)

        spinnerTipPercentage.setText(tipOptions[0], false)

        spinnerTipPercentage.setOnItemClickListener {_, _, position, _ ->
            currentTipPercentage = when (position) {
                0 -> 15.0
                1 -> 18.0
                else -> 20.0
            }
            calculateTip()
        }

        etBillAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateTip()
            }
        })

        switchRoundUp.setOnCheckedChangeListener { _, _ ->
            calculateTip()
        }
    }

    private fun calculateTip() {
        val amount = etBillAmount.text.toString()
        val cost = amount.toDoubleOrNull()

        if (cost == null || cost == 0.0) {
            tvTipResult.text = "Tip Amount: $0.00"
            return
        }

        var tip = (currentTipPercentage / 100) * cost
        if (switchRoundUp.isChecked) {
            tip = ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        tvTipResult.text = "Tip Amount: $formattedTip"
    }
}