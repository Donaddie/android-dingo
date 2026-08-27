package com.donaddie.androiddingo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.donaddie.androiddingo.data.database.DingoDatabase
import com.donaddie.androiddingo.ui.theme.DingoTheme
import com.donaddie.androiddingo.ui.screens.today.TodayScreen
import com.donaddie.androiddingo.ui.screens.finance.FinanceScreen
import com.donaddie.androiddingo.ui.screens.life.LifeScreen
import com.donaddie.androiddingo.ui.screens.inbox.InboxScreen
import com.donaddie.androiddingo.ui.screens.vehicle.VehicleScreen
import com.donaddie.androiddingo.ui.screens.documents.DocumentsScreen
import com.donaddie.androiddingo.ui.screens.addanything.AddAnythingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DingoTheme {
                val database = (application as DingoApp).database
                DingoNavigation(database = database)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DingoNavigation(database: DingoDatabase) {
    val navController = rememberNavController()
    var showAddAnything by remember { mutableStateOf(false) }
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Today") },
                    label = { Text("Today") },
                    selected = navController.currentBackStackEntry?.destination?.route == "today",
                    onClick = {
                        navController.navigate("today") {
                            popUpTo("bottom_nav") { inclusive = true }
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.AccountBalance, contentDescription = "Finance") },
                    label = { Text("Finance") },
                    selected = navController.currentBackStackEntry?.destination?.route == "finance",
                    onClick = {
                        navController.navigate("finance") {
                            popUpTo("bottom_nav") { inclusive = true }
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Life") },
                    label = { Text("Life") },
                    selected = navController.currentBackStackEntry?.destination?.route == "life",
                    onClick = {
                        navController.navigate("life") {
                            popUpTo("bottom_nav") { inclusive = true }
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Inbox, contentDescription = "Inbox") },
                    label = { Text("Inbox") },
                    selected = navController.currentBackStackEntry?.destination?.route == "inbox",
                    onClick = {
                        navController.navigate("inbox") {
                            popUpTo("bottom_nav") { inclusive = true }
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, contentDescription = "More") },
                    label = { Text("More") },
                    selected = navController.currentBackStackEntry?.destination?.route == "more",
                    onClick = {
                        navController.navigate("more") {
                            popUpTo("bottom_nav") { inclusive = true }
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (showAddAnything) {
                ExtendedFloatingActionButton(
                    onClick = { showAddAnything = false },
                    icon = { Icon(Icons.Default.Close, contentDescription = null) },
                    text = { Text("Close") }
                )
            } else {
                FloatingActionButton(
                    onClick = { showAddAnything = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Anything")
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "bottom_nav",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("bottom_nav") {
                TodayScreen(
                    onAddClick = { showAddAnything = true },
                    database = database
                )
            }
            
            composable("today") {
                TodayScreen(
                    onAddClick = { showAddAnything = true },
                    database = database
                )
            }
            
            composable("finance") { FinanceScreen(database = database) }
            composable("life") { LifeScreen(database = database) }
            composable("inbox") { InboxScreen() }
            composable("vehicle") { VehicleScreen(database = database) }
            composable("documents") { DocumentsScreen() }
        }
    }
    
    if (showAddAnything) {
        AddAnythingScreen(
            onDismiss = { showAddAnything = false },
            onAdd = { text ->
                showAddAnything = false
                navController.navigate("today") {
                    popUpTo("bottom_nav") { inclusive = true }
                }
            }
        )
    }
}
