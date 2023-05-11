package com.supwithmice.diary.models

data class Filter(
    val emptyText: String,
    val existStateProvider: Boolean,
    val filterType: String,
    val hasSureCheckedFlag: Boolean,
    val hideSingleOption: Boolean,
    val hideTitleFlag: Boolean,
    val id: String,
    val optionalFlag: Boolean,
    val order: Int,
    val showAllValueIfSingleFlag: Boolean,
    val title: String
)