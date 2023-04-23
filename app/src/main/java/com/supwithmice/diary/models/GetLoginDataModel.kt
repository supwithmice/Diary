package com.supwithmice.diary.models

data class GetLoginDataModel(
    val lt: String,
    val salt: String,
    val ver: String
)