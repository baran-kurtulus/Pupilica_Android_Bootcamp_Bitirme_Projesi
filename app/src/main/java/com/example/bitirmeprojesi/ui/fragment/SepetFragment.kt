package com.example.bitirmeprojesi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentSepetBinding
import com.example.bitirmeprojesi.ui.adapter.SepetAdapter
import com.example.bitirmeprojesi.ui.viewmodel.SepetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SepetFragment : Fragment() {
    private lateinit var binding: FragmentSepetBinding
    private lateinit var viewModel: SepetViewModel
    private lateinit var adapter: SepetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSepetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SepetAdapter(requireContext(), viewModel)
        binding.sepetRv.layoutManager = LinearLayoutManager(requireContext())
        binding.sepetRv.adapter = adapter

        viewModel.yemekListesi.observe(viewLifecycleOwner) {liste ->
            adapter.listeGuncelle(liste)
            binding.textViewToplamFiyat.text = "${viewModel.totalFiyatGetir(liste)}₺"
        }

        binding.buttonOnayla.setOnClickListener {

            val silinenler = viewModel.yemekListesi.value
            if(silinenler.isNotEmpty()){
                for (yemek in silinenler){
                    viewModel.sepettenYemekSil(yemek.sepet_yemek_id)
                }
                Toast.makeText(requireContext(),"Onaylandı! Gönderiniz 30 dakika içerisinde adresinize teslim edilecektir",
                    Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(requireContext(),"Sepetiniz boş olduğundan dolayı sipariş verilemiyor",
                    Toast.LENGTH_SHORT).show()
            }

        }



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SepetViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.sepettekiYemekleriGetir()
    }

    //override fun onHiddenChanged(hidden: Boolean) {
    //    super.onHiddenChanged(hidden)
    //    viewModel.sepettekiYemekleriGetir()
    //}


}