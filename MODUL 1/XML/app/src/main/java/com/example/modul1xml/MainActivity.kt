package com.example.modul1xml

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    private lateinit var imgDice1: ImageView
    private lateinit var imgDice2: ImageView
    private lateinit var btnRoll: Button
    private lateinit var tvResult: TextView
    private lateinit var cardResult: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgDice1 = findViewById(R.id.imgDice1)
        imgDice2 = findViewById(R.id.imgDice2)
        btnRoll = findViewById(R.id.btnRoll)
        tvResult = findViewById(R.id.tvResult)
        cardResult = findViewById(R.id.cardResult)

        btnRoll.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {
        val dice1Value = (1..6).random()
        val dice2Value = (1..6).random()

        imgDice1.setImageResource(getDiceImage(dice1Value))
        imgDice2.setImageResource(getDiceImage(dice2Value))

        if (dice1Value == dice2Value) {
            tvResult.text = "Selamat, anda dapat dadu double!"
        } else {
            tvResult.text = "Anda belum beruntung!"
        }

        cardResult.visibility = View.VISIBLE
    }

    private fun getDiceImage(diceValue: Int): Int {
        return when (diceValue) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_0
        }
    }
}