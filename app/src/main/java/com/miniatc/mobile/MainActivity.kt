package com.miniatc.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF09111F)
private val Panel = Color(0xFF142238)
private val Cyan = Color(0xFF38D9F5)
private val White = Color(0xFFEAF2FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiniATCApp()
        }
    }
}

@Composable
fun MiniATCApp() {
    var callsign by remember { mutableStateOf("AFR123") }
    var aircraft by remember { mutableStateOf("A320") }
    var departure by remember { mutableStateOf("FMEE") }
    var destination by remember { mutableStateOf("FMMI") }
    var altitude by remember { mutableStateOf("FL350") }
    var status by remember { mutableStateOf("EN ATTENTE DU PLAN DE VOL") }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .verticalScroll(rememberScrollState())
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "MINI ATC MADA",
                color = Cyan,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "FLIGHT ASSISTANT  •  VERSION 1.0",
                color = Color.LightGray,
                fontSize = 11.sp
            )

            HorizontalDivider(color = Cyan.copy(alpha = 0.4f))

            Card(
                colors = CardDefaults.cardColors(containerColor = Panel),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "FLIGHT PLAN",
                        color = Cyan,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    FlightField("CALLSIGN", callsign) { callsign = it }
                    FlightField("AIRCRAFT", aircraft) { aircraft = it }
                    FlightField("DEPARTURE (ICAO)", departure) { departure = it }
                    FlightField("DESTINATION (ICAO)", destination) {
                        destination = it
                    }
                    FlightField("CRUISE ALTITUDE", altitude) {
                        altitude = it
                    }

                    Spacer(Modifier.height(14.dp))

                    Button(
                        onClick = {
                            status = "PLAN PRÉPARÉ : $callsign"
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Cyan,
                            contentColor = Background
                        )
                    ) {
                        Text("PREPARE FLIGHT", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Panel),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "ATC CONTROL",
                        color = Cyan,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "STATUS",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )

                    Text(
                        status,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "GROUND  •  TOWER  •  DEPARTURE  •  APPROACH",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )
                }
            }

            Text(
                "SIMULATION ONLY — NOT FOR REAL FLIGHT",
                color = Color.Gray,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun FlightField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Text(
            label,
            color = Color.LightGray,
            fontSize = 11.sp
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = White,
                unfocusedTextColor = White,
                focusedBorderColor = Cyan,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Cyan
            )
        )
    }
}
