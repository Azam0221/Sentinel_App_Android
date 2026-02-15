package com.example.sentinel.ui.viewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sentinel.data.model.Agent
import com.example.sentinel.data.model.PermissionRequest
import com.example.sentinel.data.repo.SentinelRepository
import com.example.sentinel.enum_.AgentStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = SentinelRepository()


    private val _agents = MutableStateFlow<List<Agent>>(emptyList())
    val agents: StateFlow<List<Agent>> = _agents


    private val _pendingRequests = MutableStateFlow<List<PermissionRequest>>(emptyList())
    val pendingRequests: StateFlow<List<PermissionRequest>> = _pendingRequests


    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    private val _logs = MutableStateFlow<List<String>>(emptyList())
    val logs: StateFlow<List<String>> = _logs


    private var activeLogAgentId: String? = null


    init {
        startPolling()
    }

    private fun startPolling() {
        viewModelScope.launch {
            while (isActive) {
                fetchAgents()
                fetchPendingRequests()
                activeLogAgentId?.let { fetchLogs(it) }
                delay(2000)
            }
        }
    }

    fun setActiveAgent(agentId: String?) {
        activeLogAgentId = agentId
        if (agentId == null) _logs.value = emptyList()
    }

    private suspend fun fetchLogs(agentId: String) {
        try {
            val response = repository.getLogs(agentId)
            if (response.isSuccessful) {
                _logs.value = response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            Log.e("MainViewModel", "Log Error: ${e.message}")
        }
    }

    private suspend fun fetchAgents() {
        try {
            val response = repository.getAgents()
            if (response.isSuccessful) {
                _agents.value = response.body() ?: emptyList()
            }
        } catch (e: Exception) {

        }
    }

    fun killAgent(agentId: String) {
        viewModelScope.launch {
            try {
                val response = repository.killAgent(agentId)
                if (response.isSuccessful) {
                    _toastMessage.value = "TARGET NEUTRALIZED"
                    fetchAgents()
                } else {
                    _toastMessage.value = "Kill Failed: ${response.code()}"
                }
            } catch (e: Exception) {
                _toastMessage.value = "Error: ${e.message}"
            }
        }
    }



    private suspend fun fetchPendingRequests() {
        try {
            val response = repository.getPendingRequests()
            if (response.isSuccessful) {
                _pendingRequests.value = response.body() ?: emptyList()
            }
        } catch (e: Exception) {

            Log.d("MainViewModel", "Error in fetching the request")
        }
    }

    fun approveRequest(requestId: String) {
        sendDecision(requestId, "APPROVED")
    }

    fun denyRequest(requestId: String) {
        sendDecision(requestId, "DENIED")
    }

    private fun sendDecision(requestId: String, status: String) {
        viewModelScope.launch {
            try {
                val response = repository.decideRequest(requestId, status)
                if (response.isSuccessful) {
                    _toastMessage.value = "Request $status"
                    fetchPendingRequests()
                } else {
                    _toastMessage.value = "Action Failed"
                }
            } catch (e: Exception) {
                _toastMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}