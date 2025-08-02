package com.example.winecluster

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.jvm.java

class ResultsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val clusterId = intent.getIntExtra("cluster", -1)
        setContent {
            ResultsScreen(clusterId)
        }
    }
}

@Composable
fun ResultsScreen(clusterId: Int) {
    val scrollState = rememberScrollState()

    val clusterInfo = mapOf(
        1 to Pair(
            "Light-bodied & Crisp",
            "Lower alcohol and phenolic content, elevated acidity (malic acid), lighter color and aging profile. Suggests a refreshing, youthful wine with a clean finish."
        ),
        2 to Pair(
            "Balanced & Smooth",
            "Slightly above-average alcohol and phenolics, balanced acidity and color. Indicates a versatile, medium-bodied wine with smooth texture and moderate aging potential."
        ),
        3 to Pair(
            "Full-bodied & Intense",
            "Highest alcohol, color intensity, and phenolic richness. Lower acidity and strong aging potential. Represents a bold, structured wine with deep flavor and mature tone."
        )
    )

    val (tag, characteristics) = clusterInfo[clusterId] ?: Pair("Unknown", "No data available.")
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF841B3A)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 32.dp, vertical = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp)) // Push content lower

            Text(
                text = "🍷 Your wine belongs to:",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Cluster $clusterId",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Tag: $tag",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Key Characteristics",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = characteristics,
                fontSize = 16.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(bottom = 32.dp),
                lineHeight = 22.sp
            )

            Button(
                onClick = {
                    context.startActivity(Intent(context, InputActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(0.6f)
            ) {
                Text("🔙 Back to Input", color = Color(0xFF841B3A), fontWeight = FontWeight.Bold)
            }
        }
    }
}




