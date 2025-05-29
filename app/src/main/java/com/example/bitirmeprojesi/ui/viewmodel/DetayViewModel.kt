package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.data.repo.YedirRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetayViewModel @Inject constructor(var yedirRepository: YedirRepository) : ViewModel() {

    fun sepeteEkle(yemek_adi: String,
                   yemek_resim_adi: String,
                   yemek_fiyat: Int,
                   yemek_siparis_adet: Int){
        CoroutineScope(Dispatchers.Main).launch {
            yedirRepository.sepeteEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, "hyrex32")
        }
    }


}