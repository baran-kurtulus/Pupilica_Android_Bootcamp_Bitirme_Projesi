package com.example.bitirmeprojesi.data.repo

import android.content.Context
import android.widget.ImageView
import com.example.bitirmeprojesi.data.datasource.YedirDataStore
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import dagger.hilt.android.qualifiers.ApplicationContext

class YedirRepository(var yedirDataStore: YedirDataStore) {

    suspend fun yemekleriGetir(): List<Yemekler> = yedirDataStore.yemekleriGetir()

    suspend fun sepeteEkle(yemek_adi: String, yemek_resim_adi: String, yemek_fiyat: Int, yemek_siparis_adet: Int, kullanici_adi: String)
        = yedirDataStore.sepeteEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)

    suspend fun sepettekiYemekleriGetir(kullanici_adi: String): List<SepetYemekler> = yedirDataStore.sepettekiYemekleriGetir(kullanici_adi)

    suspend fun sepettenYemekSil(sepet_yemek_id: Int, kullanici_adi: String) = yedirDataStore.sepettenYemekSil(sepet_yemek_id, kullanici_adi)

    suspend fun ara(aramaKelimesi: String): List<Yemekler> = yedirDataStore.ara(aramaKelimesi)

    fun totalFiyatGetir(liste: List<SepetYemekler>): Int = yedirDataStore.totalFiyatGetir(liste)

}