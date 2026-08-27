package com.donaddie.androiddingo.ui.screens.vehicle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.donaddie.androiddingo.data.database.DingoDatabase
import com.donaddie.androiddingo.data.model.Transaction
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleScreen(database: DingoDatabase) {
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }
    
    LaunchedEffect(database) {
        database.transactionDao().getAll().collectLatest { list ->
            transactions = list
        }
    }
    
    val vehicleExpenses = transactions.filter { t ->
        t.category.contains("vehicle", ignoreCase = true) || t.category.contains("car", ignoreCase = true)
    }
    var totalVehicleSpent by remember { mutableStateOf(0.0) }
    vehicleExpenses.forEach { totalVehicleSpent += it.amount }
    
    Scaffold(
        topBar = { TopAppBar(title = { Text("Vehicle") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Vehicle", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            
            // Vehicle Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Toyota Corolla", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("AB12 CDE", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Text("2020", style = MaterialTheme.typography.labelMedium)
                    }
                    Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(48.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Alerts
            SectionHeader(title = "Upcoming Reminders")
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFFF5252))
                    Column {
                        Text("MOT Expiry", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text("Due in 30 days", style = MaterialTheme.typography.bodySmall, color = Color(0xFFFF5252))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFFFFA000))
                    Column {
                        Text("Insurance Renewal", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text("Due in 60 days", style = MaterialTheme.typography.bodySmall, color = Color(0xFFFFA000))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Expenses
            SectionHeader(title = "Vehicle Expenses")
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Spent", style = MaterialTheme.typography.titleMedium)
                    Text(
                        String.format("£%.2f", totalVehicleSpent),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // History
            SectionHeader(title = "History")
            Spacer(modifier = Modifier.height(8.dp))
            
            if (vehicleExpenses.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No vehicle expenses yet", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(vehicleExpenses.take(10)) { transaction ->
                        VehicleExpenseRow(transaction = transaction)
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleExpenseRow(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(transaction.description, style = MaterialTheme.typography.titleSmall)
                Text(transaction.category, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(
                String.format("£%.2f", transaction.amount),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
}
