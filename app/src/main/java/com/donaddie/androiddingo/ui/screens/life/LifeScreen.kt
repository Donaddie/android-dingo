package com.donaddie.androiddingo.ui.screens.life

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
fun LifeScreen(database: DingoDatabase) {
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }
    
    LaunchedEffect(database) {
        database.transactionDao().getAll().collectLatest { list ->
            transactions = list
        }
    }
    
    val lifeExpenses = transactions.filter { t ->
        t.category.contains("life", ignoreCase = true) || t.category.contains("personal", ignoreCase = true)
    }
    var totalLifeSpent by remember { mutableStateOf(0.0) }
    lifeExpenses.forEach { totalLifeSpent += it.amount }
    
    Scaffold(
        topBar = { TopAppBar(title = { Text("Life") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Life", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            
            // Summary Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Event, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("12", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("Events", style = MaterialTheme.typography.labelSmall)
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Flag, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiaryContainer, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("5", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("Goals", style = MaterialTheme.typography.labelSmall)
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Done, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("8", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("Tasks", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Life Spending
            SectionHeader(title = "Life Spending")
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Total Spent", style = MaterialTheme.typography.labelMedium)
                        Text(
                            String.format("£%.2f", totalLifeSpent),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9C27B0)
                        )
                    }
                    Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = Color(0xFF9C27B0))
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Categories
            SectionHeader(title = "Categories")
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(lifeCategories) { category ->
                    CategoryRow(category = category)
                }
            }
        }
    }
}

private val lifeCategories = listOf(
    CategoryItem("Groceries", "£342.50", "12 transactions"),
    CategoryItem("Dining Out", "£156.20", "8 transactions"),
    CategoryItem("Entertainment", "£89.00", "5 transactions"),
    CategoryItem("Subscriptions", "£45.00", "3 transactions"),
    CategoryItem("Transport", "£67.50", "6 transactions")
)

data class CategoryItem(val name: String, val amount: String, val count: String)

@Composable
fun CategoryRow(category: CategoryItem) {
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
                Text(category.name, style = MaterialTheme.typography.titleSmall)
                Text(category.count, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(category.amount, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
}
