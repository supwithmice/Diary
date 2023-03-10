package com.sup.diary

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sup.diary.databinding.ActivityMainBinding
import com.sup.diary.models.GetDataModel
import com.sup.diary.models.GetLoginModel
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

    private lateinit var binding: ActivityMainBinding
    fun Any?.log(tag: String) = Log.d(tag, this.toString())

    val client = HttpClient(OkHttp) {
        followRedirects = false
        install(HttpCookies)
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(ContentNegotiation) { gson() }
//        install(Auth) {
//            bearer {
//
//            }
//        }
    }

    val url = "http://109.195.102.150/webapi/"
    val orig_pw = "178285"
    val username = "СмирновГ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)





        CoroutineScope(Dispatchers.IO).launch {
            val getData: GetDataModel =
                client.post(url + "auth/getdata").body()
            client.cookies(url).log("cookie")

//          -----getData-----
            val salt = getData.salt
            val lt = getData.lt
            val ver = getData.ver

            salt.log("")
            lt.log("")
            ver.log("")

//          -----encryptor-----
            fun md5(str: String): ByteArray =
                MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))

            fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

            val enc_pw = md5(orig_pw).toHex()
            val pw2 = md5(salt + enc_pw).toHex()
            val pw = pw2.substring(0, orig_pw.length)

//          -----login-----
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

            val response = client.get(url + "grade/assignment/types").body<String>().log("result")

//            client.close()

//            val client = HttpClient(OkHttp) {
//                followRedirects = false
//                install(HttpCookies)
//                install(Logging) {
//                    logger = Logger.SIMPLE
//                    level = LogLevel.ALL
//                }
//                install(ContentNegotiation) { gson() }
//                install(Auth) {
//                    bearer {
//                        loadTokens {
//                            BearerTokens(accessToken)
//                        }
//                        refreshTokens {
//                            BearerTokens(refreshToken)
//                        }
//                    }
//                }
            }

        }




    override fun onDestroy() {
        super.onDestroy()
        CoroutineScope(Dispatchers.IO).launch {
            client.post(url + "auth/logout").log("closed")
        }
            client.close()
    }
}