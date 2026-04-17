package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MaterialTheme {
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ) {
                   TipCalculatorScreen()
               }
           }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorScreen() {
    var amountInput by remember { mutableStateOf("") }
    var tipPercentage by remember { mutableDoubleStateOf(15.0) }
    var roundUp by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipPercentage, roundUp)

    Column(
        modifier = Modifier
            .padding(40.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Calculate Tip",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = amountInput,
            onValueChange = {amountInput = it},
            label = {Text("Bill Amount")},
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.money),
                    contentDescription = "Bill Icon"
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        var expanded by remember {mutableStateOf(false)}
        var options = listOf(15.0, 18.0, 20.0)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {expanded = !expanded},
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            TextField(
                value = "${tipPercentage.toInt()}%",
                onValueChange = {},
                readOnly = true,
                label = {Text("Tip Percentage")},
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.percent),
                        contentDescription = "Percent Icon"
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false}
            ) {
                options.forEach { option -> DropdownMenuItem(
                    text = { Text("${option.toInt()}%")},
                    onClick = {
                        tipPercentage = option
                        expanded = false
                    }
                )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Round up tip?", fontSize = 16.sp)
            Switch(
                checked = roundUp,
                onCheckedChange = {roundUp = it}
            )
        }

        Text(
            text = "Tip Amount: $tip",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun calculateTip(amount: Double, tipPercentage: Double, roundUp: Boolean): String {
    var tip = (tipPercentage/100) * amount
    if (roundUp) {
        tip = ceil(tip)
    }
    return NumberFormat.getCurrencyInstance(Locale.US).format(tip)
}