package com.supwithmice.diary.models

data class ReportConnection(
    val ConnectionId: String,
    val ConnectionTimeout: Double,
    val ConnectionToken: String,
    val DisconnectTimeout: Double,
    val KeepAliveTimeout: Double,
    val LongPollDelay: Double,
    val ProtocolVersion: String,
    val TransportConnectTimeout: Double,
    val TryWebSockets: Boolean,
    val Url: String
)