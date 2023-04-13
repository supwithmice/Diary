package com.supwithmice.diary.models

data class AccountInfo(
    val accessToken: String,
    val active: Boolean,
    val activeToken: Any,
    val canLogin: Boolean,
    val currentOrganization: CurrentOrganization,
    val loginTime: String,
    val organizations: List<Organization>,
    val secureActiveToken: String,
    val storeTokens: Boolean,
    val user: User,
    val userRoles: List<Any>
)