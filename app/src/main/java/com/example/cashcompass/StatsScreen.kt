package com.example.cashcompass

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StatsScreen() {
    // Implement the UI for the stats screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Stats Screen", style = MaterialTheme.typography.h4)
        // Add more UI components here as needed
    }
}

@Preview(showBackground = true)
@Composable
fun StatsScreenPreview() {
    StatsScreen()
}