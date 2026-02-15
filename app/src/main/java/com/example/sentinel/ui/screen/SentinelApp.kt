package com.example.sentinel.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState // ✅ IMPORTANT IMPORT
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sentinel.ui.theme.SentinelBlack
import com.example.sentinel.ui.theme.SentinelDarkGray
import com.example.sentinel.ui.theme.SentinelRed
import com.example.sentinel.ui.viewModel.MainViewModel

@Composable
fun SentinelApp(
    viewModel: MainViewModel,
    onKillClick: (String) -> Unit
) {
    val navController = rememberNavController()
    val agents by viewModel.agents.collectAsState()
    val pendingRequests by viewModel.pendingRequests.collectAsState()


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {

            if (currentRoute?.startsWith("logs/") != true) {
                NavigationBar(
                    containerColor = SentinelDarkGray,
                    contentColor = Color.White
                ) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Fleet") },
                        label = { Text("Fleet") },
                        selected = currentRoute == "dashboard",
                        onClick = {
                            navController.navigate("dashboard") {
                                popUpTo("dashboard") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = SentinelRed.copy(alpha = 0.2f),
                            selectedIconColor = SentinelRed,
                            unselectedIconColor = Color.Gray
                        )
                    )
                    NavigationBarItem(
                        icon = {
                            BadgedBox(badge = {
                                if (pendingRequests.isNotEmpty()) {
                                    Badge { Text(pendingRequests.size.toString()) }
                                }
                            }) {
                                Icon(Icons.Default.Notifications, contentDescription = "Alerts")
                            }
                        },
                        label = { Text("Alerts") },
                        selected = currentRoute == "approvals",
                        onClick = {
                            navController.navigate("approvals") {
                                popUpTo("dashboard") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = SentinelRed.copy(alpha = 0.2f),
                            selectedIconColor = SentinelRed,
                            unselectedIconColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SentinelBlack)
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {


            NavHost(
                navController = navController,
                startDestination = "dashboard",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("dashboard") {
                    DashboardScreen(
                        agents = agents,
                        onKillClick = onKillClick,
                        onLogsClick = { id, name ->
                            navController.navigate("logs/$id/$name")
                        }
                    )
                }

                composable("approvals") {
                    PendingApprovalsScreen(
                        requests = pendingRequests,
                        onApprove = { id -> viewModel.approveRequest(id) },
                        onDeny = { id -> viewModel.denyRequest(id) }
                    )
                }

                composable(
                    route = "logs/{agentId}/{agentName}",
                    arguments = listOf(
                        navArgument("agentId") { type = NavType.StringType },
                        navArgument("agentName") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val agentId = backStackEntry.arguments?.getString("agentId") ?: ""
                    val agentName = backStackEntry.arguments?.getString("agentName") ?: "Agent"
                    LogScreen(
                        agentName = agentName,
                        agentId = agentId,
                        viewModel = viewModel,
                        onBackClick = {
                            viewModel.setActiveAgent(null)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}