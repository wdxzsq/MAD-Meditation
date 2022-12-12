package com.example.meditation.common

import android.content.Context

class GlobalHelper {
    companion object {
        const val baseUrl = "http://mskko2021.mad.hakta.pro/api"
        const val baseFeelingsUrl = "$baseUrl/feelings"
        const val baseQuotesUrl = "$baseUrl/quotes"
        const val baseLoginUrl = "$baseUrl/user/login"

            val regexEmail = Regex("""[a-z0-9]+@[a-z0-9]+\.[a-z]{2,3}$""")

        fun Toast(context: Context, msg: String) {
            android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_LONG).show()
        }
    }
}