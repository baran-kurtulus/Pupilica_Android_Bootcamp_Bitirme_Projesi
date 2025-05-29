package com.example.bitirmeprojesi.retrofit

class ApiUtils {
    companion object{
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getYedirDao(): YedirDao{
            return RetrofitClient.getClient(BASE_URL).create(YedirDao::class.java)
        }
    }
}