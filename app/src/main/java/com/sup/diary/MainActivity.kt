package com.sup.diary

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sup.diary.databinding.ActivityMainBinding
import com.sup.diary.models.*
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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import kotlin.text.Charsets.UTF_8

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    fun Any?.log(tag: String) = Log.d(tag, this.toString())

    var client = HttpClient(OkHttp) {
        followRedirects = false
        install(HttpCookies)
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(ContentNegotiation) { gson() }
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
            val assignmentTypes = client.get(url + "grade/assignment/types").body<String>().log("assignmentTypes")

            val yearId = currentYear.id
            val student: Student = init.students[0]

            val gold = getDiary(student, yearId)




//



            client.close()




        }


    }

    suspend fun getAttachment(attachmentId: Int, ){
        val response = client.get(url + "attachments/$attachmentId")
        TODO("save attachment")
    }

    suspend fun getDiary(student: Student, yearId: Int, start: String? = null, end: String? = null){

        if (start == null) {
//            val calendar = Calendar.getInstance()
//            calendar.firstDayOfWeek.log("asdasd")

//            val formatter = SimpleDateFormat("yyyy-MM-dd")
//            val current = formatter.format(calendar).log("asdas")

            val date = Date().log("asd")
            val current = SimpleDateFormat("yyyy-MM-dd").format(date).log("asd")

        }


//        val diary: Diary = client.submitForm(
//            url + "student/diary",
//            formParameters = Parameters.build {
//                append("studentId", student.studentId.toString())
//                append("yearId", yearId.toString())
//                append("weekStart", start.toString())
//                append("weekEnd", end.toString())
//            }
//        ).body()

    }


    override fun onDestroy() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = client.post(url + "auth/logout").log("closed")
            client.close()
        }
        super.onDestroy()
    }
}