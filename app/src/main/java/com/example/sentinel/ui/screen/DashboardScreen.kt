package com.example.sentinel.ui.screen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sentinel.components.AgentCard
import com.example.sentinel.data.model.Agent
import com.example.sentinel.enum_.AgentStatus
import com.example.sentinel.ui.theme.SentinelBlack
import com.example.sentinel.ui.theme.SentinelDarkGray
import com.example.sentinel.ui.theme.SentinelGreen
import com.example.sentinel.ui.theme.SentinelRed



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    agents: List<Agent>,
    onKillClick: (String) -> Unit,
    onLogsClick: (String, String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "SENTINEL COMMAND",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = SentinelGreen
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SentinelBlack,
                    scrolledContainerColor = SentinelBlack
                )
            )
        },
        containerColor = SentinelBlack
    ) { paddingValues ->
        if (agents.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                   // .padding(paddingValues)
                    .background(SentinelBlack),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = SentinelGreen)
            }
        } else {
            // The Agent List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SentinelBlack)
                    .navigationBarsPadding(),
                contentPadding = PaddingValues(
                    top = paddingValues.calculateTopPadding() + 16.dp,
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(agents) { agent ->
                    AgentCard(agent = agent, onKillClick = onKillClick,
                        onLogsClick = { id -> onLogsClick(id, agent.name) }
                    )
                }
            }
        }
    }
}
