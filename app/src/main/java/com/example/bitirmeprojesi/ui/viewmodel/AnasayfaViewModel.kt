package com.example.bitirmeprojesi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.data.repo.YedirRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnasayfaViewModel @Inject constructor(var yedirRepository: YedirRepository): ViewModel() {
    val yemekListesi = MutableLiveData<List<Yemekler>>()

    init {
        yemekleriGetir()

    }

    fun yemekleriGetir(){
        CoroutineScope(Dispatchers.Main).launch{
            try{
                yemekListesi.value = yedirRepository.yemekleriGetir()
            }catch (e: Exception){
                Log.e("Yemekler", "Valla gelmedi")
            }
        }
    }

    fun ara(aramaKelimesi: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                yemekListesi.value = yedirRepository.ara(aramaKelimesi)
            }catch (e: Exception){
                Log.e("Yemekler", "Valla gelmedi")
            }
        }
    }
}