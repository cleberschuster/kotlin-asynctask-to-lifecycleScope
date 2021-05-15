package com.schuster.threading

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

//TODO: 010 - Criar a classe responsável por carregar os dados
class AstrosRepository {

    //TODO: 011 - Criar função para carregar os astronautas com withContext(Dispatchers.IO)
    suspend fun loadAstros() : AstrosResult? {

        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request
                .Builder()
                .url("http://api.open-notify.org/astros.json")
                .build()

            val response = client.newCall(request).execute()
            return@withContext parseResultToJson(response.body?.string())
        }
    }

    //TODO: 012 - Criar função para converter o json
    private fun parseResultToJson(body: String?) =
        Gson().fromJson(body, AstrosResult::class.java)

}