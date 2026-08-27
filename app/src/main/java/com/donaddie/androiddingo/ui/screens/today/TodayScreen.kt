package com.donaddie.androiddingo.ui.screens.today

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.donaddie.androiddingo.data.database.DingoDatabase
import com.donaddie.androiddingo.data.model.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(
    onAddClick: () -> Unit,
    database: DingoDatabase
) {
    val transactions by database.transactionDao().getAll()
        .collectAsState(initial = emptyList())
    
    var totalIncome by remember { mutableStateOf(0.0) }
    var totalExpense by remember { mutableStateOf(0.0) }
    
    transactions.forEach { transaction ->
        if (transaction.type == "income") totalIncome += transaction.amount
        else totalExpense += transaction.amount
    }
    
    val available = totalIncome - totalExpense
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Today") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // Summary card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Available to Spend",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "£${"%.2f".format(available)}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Recent transactions
            Text(
                text = "Recent Transactions (${transactions.size})",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            if (transactions.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No transactions yet. Tap + to add one!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(transactions.take(10)) { transaction ->
                        TransactionItem(transaction = transaction)
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = transaction.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = if (transaction.type == "income") "+£${"%.2f".format(transaction.amount)}" else "-£${"%.2f".format(transaction.amount)}",
                style = MaterialTheme.typography.bodyLarge,
                color = if (transaction.type == "income") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
    }
}
