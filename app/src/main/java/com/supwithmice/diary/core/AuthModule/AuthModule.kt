package com.supwithmice.diary.core.AuthModule

import com.supwithmice.diary.core.SettingsModule.diaryPassword
import com.supwithmice.diary.core.SettingsModule.diaryUsername
import com.supwithmice.diary.core.SiteInformation.lt
import com.supwithmice.diary.core.SiteInformation.salt
import com.supwithmice.diary.core.SiteInformation.ver
import com.supwithmice.diary.core.client
import com.supwithmice.diary.core.recreateClient
import com.supwithmice.diary.models.GetLoginDataModel
import com.supwithmice.diary.models.LoginModel
import io.ktor.client.call.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import java.security.MessageDigest

const val url = "http://109.195.102.150/webapi/"
suspend fun authMe(password: String, username: String): AuthEvent {
    val getData: GetLoginDataModel = client.post(url + "auth/getdata").body()
    client.cookies(url)

    salt = getData.salt
    lt = getData.lt
    ver = getData.ver

    fun md5(str: String): ByteArray =
        MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))

    fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

    val encryptedPw = md5(password).toHex()
    val pw2 = md5(salt + encryptedPw).toHex()
    val pw = pw2.substring(0, password.length)

    val login: LoginModel = client.submitForm(
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

suspend fun reAuthMe() {
    val getData: GetLoginDataModel = client.post(url + "auth/getdata").body()
    client.cookies(url)

    salt = getData.salt
    lt = getData.lt
    ver = getData.ver

    fun md5(str: String): ByteArray =
        MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))

    fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

    val encryptedPw = md5(diaryPassword!!).toHex()
    val pw2 = md5(salt + encryptedPw).toHex()
    val pw = pw2.substring(0, diaryPassword!!.length)

    val login: LoginModel = client.submitForm(
        url = url + "login",
        formParameters = Parameters.build {
            append("LoginType", "1")
            append("cid", "2")
            append("sid", "66")
            append("scid", "1")
            append("UN", diaryUsername!!)
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

    val at = login.at
    val accessToken = login.accessToken
    val refreshToken = login.refreshToken

    recreateClient(accessToken, refreshToken)
}