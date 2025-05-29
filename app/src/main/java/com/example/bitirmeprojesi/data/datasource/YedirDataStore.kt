package com.example.bitirmeprojesi.data.datasource

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.retrofit.YedirDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Field

class YedirDataStore(var yedirDao: YedirDao) {
    //Çalışıyor
    suspend fun yemekleriGetir(): List<Yemekler> = withContext(Dispatchers.IO){
        return@withContext yedirDao.yemekleriGetir().yemekler
    }

    //Çalışıyor
    suspend fun sepeteEkle(yemek_adi: String,
                           yemek_resim_adi: String,
                           yemek_fiyat: Int,
                           yemek_siparis_adet: Int,
                           kullanici_adi: String){

        try {
            val yemekListesi = sepettekiYemekleriGetir(kullanici_adi)

            var ayniYemek: SepetYemekler? = null
            for(yemek in yemekListesi){
                if(yemek.yemek_adi == yemek_adi){
                    ayniYemek = yemek
                    break
                }
            }
            if(ayniYemek != null){
                sepettenYemekSil(ayniYemek.sepet_yemek_id, kullanici_adi)
                ayniYemek.yemek_siparis_adet += yemek_siparis_adet

                val crudCevap = yedirDao.sepeteEkle(ayniYemek.yemek_adi, ayniYemek.yemek_resim_adi, ayniYemek.yemek_fiyat, ayniYemek.yemek_siparis_adet, kullanici_adi)
                Log.e("Sepette Güncelleme", "${crudCevap.success} - ${crudCevap.message}")

            }
            else{
                val crudCevap = yedirDao.sepeteEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet,kullanici_adi)
                Log.e("Sepete Ekleme", "${crudCevap.success} - ${crudCevap.message}")
            }
        }catch (e: Exception){
            val crudCevap = yedirDao.sepeteEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet,kullanici_adi)
            Log.e("Sepete Ekleme", "${crudCevap.success} - ${crudCevap.message}")
        }


    }

    //Çalışıyor
    suspend fun sepettekiYemekleriGetir(kullanici_adi: String): List<SepetYemekler> = withContext(Dispatchers.IO){
        return@withContext yedirDao.sepettekiYemekleriGetir(kullanici_adi).sepet_yemekler
    }

    suspend fun sepettenYemekSil(sepet_yemek_id: Int, kullanici_adi: String){
        val crudCevap = yedirDao.sepettenYemekSil(sepet_yemek_id, kullanici_adi)
        Log.e("Sepetten Yemek Sil", "${crudCevap.success} - ${crudCevap.message}")
    }

    //Çalışıyor
    suspend fun ara(aramaKelimesi: String): List<Yemekler> = withContext(Dispatchers.IO){
        val liste = yedirDao.yemekleriGetir().yemekler
        val arananListe = mutableListOf<Yemekler>()

        for (yemek in liste){
            if(aramaKelimesi.lowercase() in yemek.yemek_adi.lowercase()){
                arananListe.add(yemek)
            }
        }

        val sonucListesi: List<Yemekler> = arananListe.toList()
        return@withContext sonucListesi
    }


    fun totalFiyatGetir(liste: List<SepetYemekler>): Int{
        var totalFiyat = 0
        for (i in liste){
            totalFiyat = totalFiyat + (i.yemek_fiyat * i.yemek_siparis_adet)
        }
        return totalFiyat
    }
}