package com.example.sentinel.data.model


data class PermissionRequest(
    val id: String,
    val agentId: String,
    val action: String,
    val status: String,
    val timestamp: String?
)