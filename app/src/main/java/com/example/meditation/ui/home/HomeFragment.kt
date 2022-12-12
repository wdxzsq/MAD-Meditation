package com.example.meditation.ui.home

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.meditation.common.GlobalHelper
import com.example.meditation.controller.FeelingListAdapter
import com.example.meditation.controller.QuoteListAdapter
import com.example.meditation.databinding.FragmentHomeBinding
import com.example.meditation.model.FeelingsDataItem
import com.example.meditation.model.QuotesDataItem
import com.example.meditation.model.dataItem
import com.example.meditation.model.gLoginData
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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val adapter = FeelingListAdapter()
        val secondAdapter = QuoteListAdapter()

        val avatar = activity?.intent?.getStringExtra("avatar")
        val nickName = activity?.intent?.getStringExtra("nickName")
        binding.mainPhoto.load("$avatar")
        binding.welcomeBack.text = "С возвращением, $nickName!"


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: HttpResponse = HttpClient() {
                    install(ContentNegotiation) {
                        gson()
                    }
                }. request {
                    url(GlobalHelper.baseFeelingsUrl)
                    method = HttpMethod.Get
                    contentType(ContentType.Application.Json)
                }
                Log.i("HTTP_FSTATUS", response.status.toString())
                if (response.status == HttpStatusCode.OK) {
                    val dataF = Gson().fromJson(response.bodyAsText(), FeelingsDataItem::class.java)
                    Log.i("HTTP_FIF", dataF.toString())

                    withContext(Dispatchers.Main) {
                        binding.feelingsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        binding.feelingsList.adapter = adapter

                        adapter.update(arrayListOf(dataF))
                    }
                }

                val qResponse: HttpResponse = HttpClient() {
                    install(ContentNegotiation) {
                        gson()
                    }
                }. request {
                    url(GlobalHelper.baseQuotesUrl)
                    method = HttpMethod.Get
                    contentType(ContentType.Application.Json)
                }
                Log.i("HTTP_QSTATUS", qResponse.status.toString())
                if (qResponse.status == HttpStatusCode.OK) {
                    val dataQ = Gson().fromJson(qResponse.bodyAsText(), QuotesDataItem::class.java)
                    Log.i("HTTP_QIF", dataQ.toString())

                    withContext(Dispatchers.Main) {
                        binding.quotesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        binding.quotesList.adapter = secondAdapter

                        secondAdapter.update(arrayListOf(dataQ))
                    }
                }
            } catch (e: Exception) {
                Log.e("HTTP_F", e.stackTraceToString())
                withContext(Dispatchers.Main) {
                    context?.let { GlobalHelper.Toast(it, "Ошибка сервера") }
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}