package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceRollerTheme {
                DiceRollerApp()
            }
        }
    }
}

@Composable
fun DiceRollerApp() {
    var result1 by remember { mutableStateOf(0) }
    var result2 by remember { mutableStateOf(0) }

    val message = when {
        result1 == 0 && result2 == 0 -> ""
        result1 == result2 -> "Selamat, anda dapat dadu double!"
        else -> "Anda belum beruntung!"
    }

    val imageResource1 = when (result1) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_0
    }

    val imageResource2 = when (result2) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_0
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Image(
                painter = painterResource(imageResource1),
                contentDescription = result1.toString()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(imageResource2),
                contentDescription = result2.toString()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            result1 = Dice(6).roll()
            result2 = Dice(6).roll()
        }) {
            Text(text = "Roll", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = message, fontSize = 16.sp)
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}