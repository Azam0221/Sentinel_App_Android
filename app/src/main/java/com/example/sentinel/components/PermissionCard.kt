package com.example.sentinel.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sentinel.data.model.PermissionRequest
import com.example.sentinel.ui.theme.SentinelDarkGray
import com.example.sentinel.ui.theme.SentinelGreen
import com.example.sentinel.ui.theme.SentinelRed
import com.example.sentinel.ui.theme.TextGray
import com.example.sentinel.ui.theme.TextWhite



@Composable
fun PermissionCard(
    request: PermissionRequest,
    onApprove: (String) -> Unit,
    onDeny: (String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SentinelDarkGray.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(0.dp),

        border = BorderStroke(1.dp, Color.Yellow.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "ACTION_REQUEST",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Yellow,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    color = Color.Yellow.copy(alpha = 0.1f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "AWAITING_AUTH",
                        color = Color.Yellow,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = request.action,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text("Requested by: ", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                Text(request.agentId, color = SentinelGreen, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { onDeny(request.id) },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, SentinelRed),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = SentinelRed)
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("DENY")
                }

                Button(
                    onClick = { onApprove(request.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = SentinelGreen),
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Black)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("AUTHORIZE", color = Color.Black)
                }
            }
        }
    }
}