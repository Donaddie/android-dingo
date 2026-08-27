package com.donaddie.androiddingo.ui.screens.inbox

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
fun InboxScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Follow-ups", "Waiting-for", "Notes", "Captured")
    
    Scaffold(
        topBar = { TopAppBar(title = { Text("Inbox") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            
            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedTab) {
                    0 -> FollowUpsTab()
                    1 -> WaitingForTab()
                    2 -> NotesTab()
                    3 -> CapturedTab()
                }
            }
        }
    }
}

@Composable
fun FollowUpsTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleFollowUps) { item ->
            FollowUpCard(item = item)
        }
    }
}

@Composable
fun WaitingForTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleWaiting) { item ->
            WaitingCard(item = item)
        }
    }
}

@Composable
fun NotesTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleNotes) { note ->
            NoteCard(note = note)
        }
    }
}

@Composable
fun CapturedTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleCaptured) { item ->
            CapturedCard(item = item)
        }
    }
}

@Composable
fun FollowUpCard(item: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(item, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun WaitingCard(item: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Update, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
            Text(item, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun NoteCard(note: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Note, contentDescription = null, tint = Color(0xFFFFA000))
            Text(note, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun CapturedCard(item: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.NoteAdd, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
            Text(item, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private val sampleFollowUps = listOf(
    "Reply to Sarah about the meeting",
    "Follow up on invoice #1234",
    "Check status of order delivery",
    "Confirm appointment with dentist"
)

private val sampleWaiting = listOf(
    "Waiting for contractor quote",
    "Awaiting response from supplier",
    "Waiting for document delivery"
)

private val sampleNotes = listOf(
    "Remember to call the bank about account update",
    "Book holiday flights next week",
    "Research insurance options",
    "Schedule car service appointment"
)

private val sampleCaptured = listOf(
    "Recipe for pasta carbonara",
    "Gift ideas for birthday",
    "Home office setup ideas",
    "Book recommendations"
)
