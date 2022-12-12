package com.example.meditation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.meditation.common.GlobalHelper
import com.example.meditation.databinding.ActivityLoginBinding
import com.example.meditation.model.LoginData
import com.example.meditation.model.QuotesDataItem
import com.example.meditation.model.eLoginData
import com.example.meditation.model.gLoginData
import com.example.meditation.ui.home.HomeFragment
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.siEnter.setOnClickListener {
            val fields: List<String> = listOf(
                b.siEmail.text.toString(),
                b.siPassword.text.toString(),
            )
            if (fields.contains("")) {
                Toast.makeText(this@LoginActivity, "Fields must not be empty", Toast.LENGTH_SHORT)
                    .show()
//            } else if (GlobalHelper.regexEmail.find(b.siEmail.editableText.toString()) == null) GlobalHelper.Toast(this@LoginActivity, "Email entered incorrectly")
            } else login()
        }

        b.siReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }


    private fun login() {
        val data = LoginData(
            b.siEmail.text.toString(),
            b.siPassword.text.toString()
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: HttpResponse = HttpClient() {
                    install(ContentNegotiation) {
                        gson()
                    }
                }. request {
                    url(GlobalHelper.baseLoginUrl)
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    setBody(data)
                }
                Log.i("HTTP_CLIENT", response.bodyAsText())
                if (response.status == HttpStatusCode.OK) {
                    val gData = Gson().fromJson(response.bodyAsText(), gLoginData::class.java)
                    Log.e("HTTP_CLIENT", gData.toString())
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java).putExtra("nickName", gData.nickName).putExtra("avatar", gData.avatar))
                } else {
                    withContext(Dispatchers.Main)  {
                        //ошибки, приходящие с сервера
                        val error = Gson().fromJson(response.bodyAsText(), eLoginData::class.java)
                        GlobalHelper.Toast(this@LoginActivity, error.error)
                    }
                }
            } catch (e: Exception) {
                Log.e("HTTP_F", e.stackTraceToString())
                withContext(Dispatchers.Main)  {
                    GlobalHelper.Toast(this@LoginActivity, "Ошибка сервера")
                }
            }
        }
    }


}