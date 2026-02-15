package com.example.sentinel.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sentinel.ui.theme.SentinelBlack
import com.example.sentinel.ui.theme.SentinelGreen
import com.example.sentinel.ui.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogScreen(
    agentName: String,
    agentId: String,
    viewModel: MainViewModel,
    onBackClick: () -> Unit
) {

    val logs by viewModel.logs.collectAsState()


    LaunchedEffect(agentId) {
        viewModel.setActiveAgent(agentId)
    }

    val listState = rememberLazyListState()

    LaunchedEffect(logs.size) {
        if (logs.isNotEmpty()) {
            listState.animateScrollToItem(logs.lastIndex)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "TERMINAL: $agentName",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SentinelGreen
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = SentinelGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SentinelBlack
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(SentinelBlack)
        ) {
            items(logs) { log ->
                Text(
                    text = log,

                    color = when {
                        log.contains("WARN") || log.contains("🚨") || log.contains("🛑") -> Color.Yellow
                        log.contains("✅") -> SentinelGreen
                        else -> SentinelGreen.copy(alpha = 0.8f)
                    },
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                 )
                HorizontalDivider(color = SentinelGreen.copy(alpha = 0.1f), thickness = 0.5.dp)
            }
        }
    }
}