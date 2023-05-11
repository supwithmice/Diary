package com.supwithmice.diary.models

data class Report(
    val filterPanel: FilterPanel,
    val group: String,
    val id: String,
    val level: String,
    val name: String,
    val order: Int,
    val presentTypes: List<Any>
)