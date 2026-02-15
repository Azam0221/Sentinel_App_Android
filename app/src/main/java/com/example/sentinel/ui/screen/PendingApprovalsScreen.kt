package com.example.sentinel.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sentinel.components.PermissionCard
import com.example.sentinel.data.model.PermissionRequest
import com.example.sentinel.ui.theme.SentinelBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingApprovalsScreen(
    requests: List<PermissionRequest>,
    onApprove: (String) -> Unit,
    onDeny: (String) -> Unit
) {
    Scaffold(
        containerColor = SentinelBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "PENDING APPROVALS",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = Color.Yellow
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SentinelBlack,
                    scrolledContainerColor = SentinelBlack
                )
            )
        }
    ) { paddingValues ->
        if (requests.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().background(SentinelBlack),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "NO PENDING REQUESTS",
                    color = Color.Gray,
                    fontFamily = FontFamily.Monospace
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SentinelBlack)
                    .navigationBarsPadding(),
                contentPadding = PaddingValues(
                    top = paddingValues.calculateTopPadding() + 16.dp,
                    bottom = 80.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(requests) { request ->
                    PermissionCard(request, onApprove, onDeny)
                }
            }
        }
    }
}