package com.example.winecluster.model

import kotlinx.serialization.Serializable

@Serializable
data class WineAttributes(
    val attributes: List<Double>
)
