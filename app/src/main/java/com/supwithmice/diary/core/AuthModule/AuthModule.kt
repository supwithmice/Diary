package com.supwithmice.diary.core.AuthModule

import com.supwithmice.diary.core.client
import com.supwithmice.diary.models.GetDataModel
import com.supwithmice.diary.models.GetLoginModel
import io.ktor.client.call.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import java.security.MessageDigest

const val url = "http://109.195.102.150/webapi/"
suspend fun authMe(password: String, username: String): AuthEvent {
    val getData: GetDataModel = client.post(url + "auth/getdata").body()
    client.cookies(url)

    val salt = getData.salt
    val lt = getData.lt
    val ver = getData.ver

    fun md5(str: String): ByteArray =
        MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))

    fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

    val encryptedPw = md5(password).toHex()
    val pw2 = md5(salt + encryptedPw).toHex()
    val pw = pw2.substring(0, password.length)

    val login: GetLoginModel = client.submitForm(
        url = url + "login",
        formParameters = Parameters.build {
            append("LoginType", "1")
            append("cid", "2")
            append("sid", "66")
            append("scid", "1")
            append("UN", username)
            append("PW", pw)
            append("lt", lt)
            append("pw2", pw2)
            append("ver", ver)
        }
    ) {
        headers {
            append("Referer", "http://109.195.102.150/")
        }
    }.body()

    return if (login.message == "Неправильный пароль или логин" || login.message == "Ошибка входа в систему") {
        AuthEvent.FailedAuth(login.message)
    } else {
        AuthEvent.GotAuth(login)
    }
}