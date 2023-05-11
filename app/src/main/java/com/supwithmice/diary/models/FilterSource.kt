package com.supwithmice.diary.models

data class FilterSource(
    val defaultRange: DefaultRange,
    val defaultValue: String,
    val filterId: String,
    val items: List<Item>,
    val maxValue: String,
    val message: Any,
    val minValue: String,
    val nullText: Any,
    val range: Range
)