package com.example.dicerollerxml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi komponen dari XML
        val rollButton: Button = findViewById(R.id.rollButton)
        val diceImageLeft: ImageView = findViewById(R.id.diceImageLeft)
        val diceImageRight: ImageView = findViewById(R.id.diceImageRight)
        val messageText: TextView = findViewById(R.id.messageText)

        rollButton.setOnClickListener {
            // Kocok angka 1-6 untuk dua dadu
            val roll1 = (1..6).random()
            val roll2 = (1..6).random()

            // Update gambar dadu kiri
            diceImageLeft.setImageResource(getDiceResource(roll1))
            // Update gambar dadu kanan
            diceImageRight.setImageResource(getDiceResource(roll2))

            // Logika pesan Modul 1
            if (roll1 == roll2) {
                messageText.text = "Selamat, anda dapat dadu double!"
            } else {
                messageText.text = "Anda belum beruntung!"
            }
        }
    }

    // Fungsi bantuan untuk memilih gambar berdasarkan angka
    private fun getDiceResource(roll: Int): Int {
        return when (roll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }
}