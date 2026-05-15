package com.example.scrollablelistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFFDF7FF)) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Foto Profil Bulat
                    Image(
                        painter = painterResource(id = R.drawable.movie4), // Pastikan gambar ada di drawable
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(150.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Muhammad Ilham", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6847A3))
                    Text("m.ilham@student.ulm.ac.id", fontSize = 16.sp, color = Color.Gray)

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEADDFF)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Universitas Lambung Mangkurat",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            color = Color(0xFF21005D),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}