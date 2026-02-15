package com.example.sentinel.data.model

import com.example.sentinel.enum_.AgentStatus


data class Agent(

    val id: String,
    val name: String,
    val status: AgentStatus,
    val model: String,
    val currentActivity: String

)