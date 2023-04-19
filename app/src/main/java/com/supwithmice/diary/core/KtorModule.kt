package com.supwithmice.diary.core

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.gson.*


var client = HttpClient(OkHttp) {
    engine {
//        addInterceptor(addInterceptor())
//        this.config {
//            cookieJar(cookieJar = )
//        }
    }
    followRedirects = false
    install(HttpCookies)
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
    install(ContentNegotiation) { gson() }
}

fun recreateClient(accessToken: String, refreshToken: String) {
    client = client.config {
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(accessToken, refreshToken)
                }
            }
        }
    }
}