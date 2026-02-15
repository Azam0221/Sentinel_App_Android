package com.example.sentinel.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sentinel.data.model.Agent
import com.example.sentinel.enum_.AgentStatus
import com.example.sentinel.ui.theme.SentinelDarkGray
import com.example.sentinel.ui.theme.SentinelGreen
import com.example.sentinel.ui.theme.SentinelRed

@Composable
fun AgentCard(
    agent: Agent,
    onKillClick: (String) -> Unit,
    onLogsClick: (String) -> Unit
) {

    val isKilled = agent.status == AgentStatus.KILLED
    val statusColor = if (isKilled) SentinelRed else SentinelGreen

    Card(
        colors = CardDefaults.cardColors(containerColor = SentinelDarkGray.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, if (isKilled) SentinelRed.copy(0.5f) else SentinelGreen.copy(0.2f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = agent.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = if (isKilled) "OFFLINE" else "ACTIVE",
                        color = statusColor,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Model", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                    Text(text = agent.model, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "ID", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                    Text(text = agent.id, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Current Activity", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
            Text(
                text = agent.currentActivity,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isKilled) SentinelRed else Color(0xFFCCCCCC),
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {


                OutlinedButton(
                    onClick = { onLogsClick(agent.name) },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text("VIEW LOGS")
                }

                if (!isKilled) {
                    Button(
                        onClick = { onKillClick(agent.id) },
                        colors = ButtonDefaults.buttonColors(containerColor = SentinelRed),
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("KILL")
                    }
                } else {
                    Button(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.DarkGray)
                    ) {
                        Text("DEAD", color = Color.Gray)
                    }
                }
            }
        }
    }
}
