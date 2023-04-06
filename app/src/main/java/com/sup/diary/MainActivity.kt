package com.sup.diary

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.sup.diary.models.*
import com.sup.diary.utils.orig_pw
import com.sup.diary.utils.url
import com.sup.diary.utils.username
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val getData: GetDataModel = client.post(url + "auth/getdata").body()
            client.cookies(url)

            val salt = getData.salt
            val lt = getData.lt
            val ver = getData.ver

            fun md5(str: String): ByteArray =
                MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))

            fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

            val enc_pw = md5(orig_pw).toHex()
            val pw2 = md5(salt + enc_pw).toHex()
            val pw = pw2.substring(0, orig_pw.length)

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

            val at = login.at
            val accessToken = login.accessToken
            val refreshToken = login.refreshToken

            client = HttpClient(OkHttp) {
                followRedirects = false
                install(HttpCookies)
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) { gson() }
                install(Auth) {
                    bearer {
                        loadTokens {
                            BearerTokens(accessToken, refreshToken)
                        }
                    }
                }
            }


            val init: DiaryInit = client.get(url + "student/diary/init").body()
            val currentYear: CurrentYear = client.get(url + "years/current").body()

            val yearId = currentYear.id
            val student: Student = init.students[0]
        }

        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = client.post(url + "auth/logout").log("closed")
            client.close()
        }
        super.onDestroy()
    }
}