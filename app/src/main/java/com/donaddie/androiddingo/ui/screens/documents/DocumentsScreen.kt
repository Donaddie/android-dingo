package com.donaddie.androiddingo.ui.screens.documents

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentsScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Documents") }) },
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
            Text("Documents", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            
            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatChip(label = "Important", value = "5", color = Color(0xFFFF5252))
                StatChip(label = "Expiring Soon", value = "2", color = Color(0xFFFFA000))
                StatChip(label = "Receipts", value = "18", color = Color(0xFF4CAF50))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Important Documents
            SectionHeader(title = "Important Documents")
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(documentList) { doc ->
                    DocumentCard(document = doc)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Receipts
            SectionHeader(title = "Recent Receipts")
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(receiptList) { receipt ->
                    ReceiptCard(receipt = receipt)
                }
            }
        }
    }
}

@Composable
fun StatChip(label: String, value: String, color: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = color)
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
}

@Composable
fun DocumentCard(document: Document) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                if (document.expiryWarning) Icons.Default.Warning else Icons.Default.Description,
                contentDescription = null,
                tint = if (document.expiryWarning) Color(0xFFFF5252) else MaterialTheme.colorScheme.primary
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(document.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text(document.category, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (document.expiryWarning) {
                    Text("Expiring soon!", style = MaterialTheme.typography.labelSmall, color = Color(0xFFFF5252))
                }
            }
        }
    }
}

@Composable
fun ReceiptCard(receipt: Receipt) {
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
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = Color(0xFF4CAF50))
                Column {
                    Text(receipt.merchant, style = MaterialTheme.typography.titleSmall)
                    Text(receipt.date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Text(
                String.format("£%.2f", receipt.amount),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class Document(val name: String, val category: String, val expiryWarning: Boolean = false)
data class Receipt(val merchant: String, val date: String, val amount: Double)

private val documentList = listOf(
    Document("Driving Licence", "Personal", false),
    Document("Vehicle Registration", "Vehicle", false),
    Document("Insurance Certificate", "Vehicle", false),
    Document("MOT Certificate", "Vehicle", true),
    Document("Tax Document", "Finance", false),
    Document("Property Deed", "Home", false),
    Document("Electrical Certificate", "Home", true)
)

private val receiptList = listOf(
    Receipt("Tesco", "27 Aug 2026", 45.50),
    Receipt("Shell Petrol", "26 Aug 2026", 62.00),
    Receipt("Amazon", "25 Aug 2026", 29.99),
    Receipt("Costa Coffee", "25 Aug 2026", 3.50),
    Receipt("Sainsbury's", "24 Aug 2026", 78.25)
)
