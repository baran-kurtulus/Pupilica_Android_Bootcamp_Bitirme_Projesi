package com.example.bitirmeprojesi.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.databinding.YemekCardTasarimBinding
import com.example.bitirmeprojesi.ui.fragment.AnasayfaFragmentDirections
import com.example.bitirmeprojesi.ui.viewmodel.AnasayfaViewModel
import com.example.bitirmeprojesi.utils.gecisYap

class YemekAdapter(var context: Context, var viewModel: AnasayfaViewModel)
    : RecyclerView.Adapter<YemekAdapter.CardTasarimTutucu>(){

    private val yemekListesi = mutableListOf<Yemekler>()
    fun listeGuncelle(yeniListe: List<Yemekler>){
        yemekListesi.clear()
        yemekListesi.addAll(yeniListe)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim = YemekCardTasarimBinding.inflate(LayoutInflater.from(context), parent, false)
        Log.e("Yemekler", "onCreateViewHoldera Girdi")
        return CardTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val yemek = yemekListesi.get(position)
        val t = holder.tasarim
        t.textViewCardYemek.text = yemek.yemek_adi
        t.textViewCardFiyat.text = "${yemek.yemek_fiyat}₺"

        Glide.with(context).load("http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}")
            .override(300, 300).into(t.imageViewCardYemek)

        t.customButton.setOnClickListener {
            val gecis = AnasayfaFragmentDirections.detayGecis(yemek)
            Navigation.gecisYap(it, gecis)
        }

    }

    override fun getItemCount(): Int {
        return yemekListesi.size

    }

    inner class CardTasarimTutucu(var tasarim: YemekCardTasarimBinding): RecyclerView.ViewHolder(tasarim.root)
}