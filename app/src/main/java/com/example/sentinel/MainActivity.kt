package com.example.sentinel

import android.hardware.biometrics.BiometricPrompt
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.example.sentinel.ui.screen.SentinelApp
import com.example.sentinel.ui.theme.SentinelBlack
import com.example.sentinel.ui.theme.SentinelTheme
import com.example.sentinel.ui.viewModel.MainViewModel
import com.example.sentinel.utils.BiometricHelper
import java.util.concurrent.Executor

class MainActivity : FragmentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var biometricHelper: BiometricHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        biometricHelper = BiometricHelper(this)

        setContent {
            SentinelTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SentinelBlack
                ) {
                    SentinelApp(viewModel) { agentId ->
                        triggerSecureKill(agentId)
                    }
                }
                }
            }
        }

    private fun triggerSecureKill(agentId: String) {
        biometricHelper.authenticate(
            title = "EMERGENCY TERMINATION",
            subtitle = "Confirm kill for Agent: $agentId",
            onSuccess = {
                viewModel.killAgent(agentId)
                Toast.makeText(this, "KILL COMMAND SENT", Toast.LENGTH_SHORT).show()
            }
        )
    }

}






