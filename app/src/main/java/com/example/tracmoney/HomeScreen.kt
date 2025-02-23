package com.example.tracmoney

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val monthlyTransactions by viewModel.monthlyTransactions.collectAsState()
    val selectedSectionId by viewModel.selectedSectionId.collectAsState()
    val randomTransactions = listOf(
        Transaction(
            dates = mutableListOf("2023-01-01"),
            title = "Transaction 1",
            amounts = mutableListOf(10.0)
        ),
        Transaction(
            dates = mutableListOf("2023-01-02"),
            title = "Transaction 2",
            amounts = mutableListOf(20.0)
        ),
        Transaction(
            dates = mutableListOf("2023-01-03"),
            title = "Transaction 3",
            amounts = mutableListOf(30.0)
        ),
        Transaction(
            dates = mutableListOf("2023-01-04"),
            title = "Transaction 4",
            amounts = mutableListOf(40.0)
        ),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = LocalDate.now().toString(),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // RecyclerView equivalent using LazyColumn
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(monthlyTransactions?.sections ?: emptyList()) { section ->
                SectionItem(
                    section = section,
                    onSectionClick = {
                        viewModel.selectSection(section.id)
                    },
                    onDeleteSection = {
                        viewModel.deleteSection(section.id)
                    },
                    isSelected = section.id == selectedSectionId
                )
            }
        }

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    val newSectionId = viewModel.getNewId()
                    val newSection = TransactionSection(
                        id = newSectionId,
                        title = "New Section $newSectionId",
                        currency = "USD"
                    )
                    viewModel.addSection(newSection)
                },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text("Add Section")
            }

            Button(
                onClick = {
                    val randomTransaction = randomTransactions.random()
                    viewModel.addTransaction(randomTransaction)
                    // Notify the adapter about the updated section
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Add Transaction")
            }
        }
    }
}

@Composable
fun SectionItem(
    section: TransactionSection,
    onSectionClick: () -> Unit,
    onDeleteSection: () -> Unit,
    isSelected: Boolean
) {
    // Implement your section UI here
    Text(
        text = section.title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onSectionClick)
    )
}