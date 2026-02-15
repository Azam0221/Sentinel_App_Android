package com.example.sentinel.data.repo

import com.example.sentinel.network.RetrofitClient


class SentinelRepository {
    private val api = RetrofitClient.api

    suspend fun getAgents() = api.getAgents()

    suspend fun getPendingRequests() = api.getPendingRequests()

    suspend fun decideRequest(id: String, status: String) = api.decideRequest(id, status)

    suspend fun killAgent(agentId: String) = api.killAgent(agentId)

    suspend fun getLogs(agentId: String) = api.getLogs(agentId)
}  