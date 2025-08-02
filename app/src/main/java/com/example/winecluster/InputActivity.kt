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
import kotlinx.coroutines.launch
import predictCluster

class InputActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InputScreen()
        }
    }
}

@Composable
fun InputScreen() {
    val scrollState = rememberScrollState()
    val inputValues = remember { List(13) { mutableStateOf("") } }

    val minValues = listOf(
        11.03, 0.74, 1.36, 10.60, 70.00, 0.98, 0.34,
        0.13, 0.41, 1.28, 0.48, 1.27, 278.00
    )
    val maxValues = listOf(
        14.83, 5.80, 3.23, 30.00, 162.00, 3.88, 5.08,
        0.66, 3.58, 13.00, 1.71, 4.00, 1680.00
    )

    val attributeLabels = listOf(
        "Alcohol", "Malic Acid", "Ash", "Alcalinity of Ash", "Magnesium",
        "Total Phenols", "Flavanoids", "Nonflavanoid Phenols", "Proanthocyanins",
        "Color Intensity", "Hue", "OD280/OD315", "Proline"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter Wine Chemical Attributes",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        attributeLabels.forEachIndexed { index, label ->
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = inputValues[index].value,
                    onValueChange = {
                        inputValues[index].value = it
                    },
                    label = { Text(label, color = Color.White) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.LightGray,
                        cursorColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )

                Text(
                    text = "Allowed range: ${minValues[index]} – ${maxValues[index]}",
                    fontSize = 12.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        Button(
            onClick = {
                val values = inputValues.map { it.value.toDoubleOrNull() }
                if (values.all { it != null }) {
                    val validValues = values.map { it!! }

                    coroutineScope.launch {
                        try {
                            val clusterId = predictCluster(validValues)
                            val intent = Intent(context, ResultsActivity::class.java)
                            intent.putExtra("cluster", clusterId)
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            println("API call failed: ${e.message}")
                        }
                    }
                } else {
                    println("Invalid input: Please fill all fields correctly.")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFff7b7b))
        ) {
            Text("Submit", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}
