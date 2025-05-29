package com.example.bitirmeprojesi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.repo.YedirRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SepetViewModel @Inject constructor(var yedirRepository: YedirRepository): ViewModel() {

    val yemekListesi = MutableLiveData<List<SepetYemekler>>()

    init {
        sepettekiYemekleriGetir()
    }

    fun totalFiyatGetir(liste: List<SepetYemekler>): Int = yedirRepository.totalFiyatGetir(liste)

    fun sepettenYemekSil(sepet_yemek_id: Int){
        CoroutineScope(Dispatchers.Main).launch {
            yedirRepository.sepettenYemekSil(sepet_yemek_id, "hyrex32")
            sepettekiYemekleriGetir()
        }

    }

    fun sepettekiYemekleriGetir(){
        CoroutineScope(Dispatchers.Main).launch {
            try{
                yemekListesi.value = yedirRepository.sepettekiYemekleriGetir("hyrex32")
            }catch (e: Exception){
                yemekListesi.value = listOf<SepetYemekler>()//Sepette yemek olmadığında boş liste gönderimi
                Log.e("Sepet Yemek", "Liste Alınamadı")
            }
        }
    }
}