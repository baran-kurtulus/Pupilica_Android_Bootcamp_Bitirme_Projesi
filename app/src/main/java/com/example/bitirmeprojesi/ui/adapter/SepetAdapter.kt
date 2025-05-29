package com.example.bitirmeprojesi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.databinding.SepetCardTasarimBinding
import com.example.bitirmeprojesi.ui.viewmodel.SepetViewModel
import com.google.android.material.snackbar.Snackbar

class SepetAdapter(var context: Context, var viewModel: SepetViewModel)
    : RecyclerView.Adapter<SepetAdapter.CardTasarimTutucu>(){

    private val yemekListesi = mutableListOf<SepetYemekler>()

    fun listeGuncelle(yeniListe: List<SepetYemekler>){
        yemekListesi.clear()
        yemekListesi.addAll(yeniListe)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim = SepetCardTasarimBinding.inflate(LayoutInflater.from(context), parent, false)
        return CardTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val yemek = yemekListesi.get(position)
        val t = holder.tasarim
        val totalFiyat = yemek.yemek_fiyat * yemek.yemek_siparis_adet

        t.textViewSepetYemekAd.text = yemek.yemek_adi
        t.textViewSepetYemekAdet.text = yemek.yemek_siparis_adet.toString()
        t.textViewSepetYemekFiyat.text = "${yemek.yemek_fiyat}₺"
        t.textViewSepetYemekTotalFiyat.text = totalFiyat.toString()

        Glide.with(context).load("http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}")
            .override(300, 300).into(t.imageViewSepetYemek)

        t.imageViewSepetSil.setOnClickListener {
            Snackbar.make(it, "${yemek.yemek_adi} silinsin mi", Snackbar.LENGTH_SHORT)
                .setAction("Evet") {
                    viewModel.sepettenYemekSil(yemek.sepet_yemek_id)
                }.show()
        }
    }

    override fun getItemCount(): Int {
        return yemekListesi.size
    }

    inner class CardTasarimTutucu(var tasarim: SepetCardTasarimBinding): RecyclerView.ViewHolder(tasarim.root)
}