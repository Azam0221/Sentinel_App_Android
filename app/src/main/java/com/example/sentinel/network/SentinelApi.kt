package com.example.sentinel.network

import com.example.sentinel.data.model.Agent
import com.example.sentinel.data.model.PermissionRequest
import retrofit2.Response
import retrofit2.http.*

interface SentinelApi {

    @GET("agent/mobile/list")
    suspend fun getAgents(): Response<List<Agent>>

    @GET("permission/mobile/pending")
    suspend fun getPendingRequests(): Response<List<PermissionRequest>>


    @POST("permission/mobile/decide/{requestId}")
    suspend fun decideRequest(
        @Path("requestId") requestId: String,
        @Query("status") status: String
    ): Response<PermissionRequest>


    @POST("agent/mobile/kill/{agentId}")
    suspend fun killAgent(
        @Path("agentId") agentId: String
    ): Response<String>

    @GET("agent/logs/{agentId}")
    suspend fun getLogs(
        @Path("agentId") agentId: String
    ): Response<List<String>>

}