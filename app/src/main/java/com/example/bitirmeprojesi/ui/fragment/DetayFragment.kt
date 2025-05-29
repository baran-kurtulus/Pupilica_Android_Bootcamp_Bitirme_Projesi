package com.example.bitirmeprojesi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentDetayBinding
import com.example.bitirmeprojesi.ui.viewmodel.DetayViewModel
import com.example.bitirmeprojesi.utils.gecisYap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetayFragment : Fragment() {
    private lateinit var binding: FragmentDetayBinding
    private lateinit var viewModel: DetayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetayBinding.inflate(inflater, container, false)
        val bundle: DetayFragmentArgs by navArgs()
        val gelenYemek = bundle.yemek

        binding.textViewDetayAdet.text = "0"
        binding.textViewDetayFiyat.text = "${gelenYemek.yemek_fiyat}₺"
        binding.textViewDetayYemek.text = "${gelenYemek.yemek_adi}"
        binding.textViewTotalFiyat.text = "Toplam: 0₺"

        Glide.with(binding.imageViewDetayYemek).load("http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}")
            .override(400, 400)
            .into(binding.imageViewDetayYemek)

        binding.buttonArttir.setOnClickListener {
            var adet = binding.textViewDetayAdet.text.toString().toInt()
            var fiyat = gelenYemek.yemek_fiyat
            adet += 1
            val totalFiyat = adet * fiyat

            binding.textViewDetayAdet.text = adet.toString()
            binding.textViewTotalFiyat.text = "Toplam: ${totalFiyat}₺"
        }

        binding.buttonAzalt.setOnClickListener {
            if(binding.textViewDetayAdet.text == "0"){
                return@setOnClickListener
            }

            var adet = binding.textViewDetayAdet.text.toString().toInt()
            var fiyat = gelenYemek.yemek_fiyat
            adet -= 1
            val totalFiyat = adet * fiyat

            binding.textViewDetayAdet.text = adet.toString()
            binding.textViewTotalFiyat.text = "Toplam: ${totalFiyat}₺"

        }

        binding.buttonSepeteEkle.setOnClickListener {
            val adet = binding.textViewDetayAdet.text.toString().toInt()
            if(adet == 0){
                Toast.makeText(context,"Lütfen Kaç Adet Sipariş Edeceğinizi Seçiniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.sepeteEkle(gelenYemek.yemek_adi, gelenYemek.yemek_resim_adi, gelenYemek.yemek_fiyat, adet)
            Toast.makeText(context,"Ürün Sepete Eklendi!", Toast.LENGTH_SHORT).show()
            val gecis = DetayFragmentDirections.anaSayfaGecis()
            Navigation.gecisYap(it, gecis)
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetayViewModel by viewModels()
        viewModel = tempViewModel
    }

}