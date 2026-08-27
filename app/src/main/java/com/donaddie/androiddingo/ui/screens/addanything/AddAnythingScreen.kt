package com.donaddie.androiddingo.ui.screens.addanything

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.donaddie.androiddingo.data.database.DingoDatabase
import com.donaddie.androiddingo.data.model.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddAnythingScreen(
    database: DingoDatabase,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "Add Anything",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "What do you want to add?",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .focusRequester(focusRequester),
                    singleLine = false,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (text.isNotBlank()) {
                                val (type, amount, description) = parseInput(text)
                                val transaction = Transaction(
                                    description = description,
                                    amount = amount,
                                    type = type,
                                    category = "other",
                                    date = System.currentTimeMillis()
                                )
                                CoroutineScope(Dispatchers.IO).launch {
                                    database.transactionDao().insert(transaction)
                                }
                                onDismiss()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = text.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Add")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

fun parseInput(input: String): Triple<String, Double, String> {
    val lower = input.lowercase()
    var type = "expense"
    var amount = 0.0
    var description = input

    if (lower.contains("spent") || lower.contains("paid") || lower.contains("cost")) {
        type = "expense"
    } else if (lower.contains("earned") || lower.contains("income") || lower.contains("got paid")) {
        type = "income"
    }

    val amountMatch = """(\d+\.?\d*)""".toRegex().find(lower)
    if (amountMatch != null) {
        amount = amountMatch.value.toDouble()
        description = input.replace(amountMatch.value, "").trim()
    }

    return Triple(type, amount, description)
}