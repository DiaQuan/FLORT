package com.example.flort.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PartnerViewModel(private val dao: PartnerDao) : ViewModel() {

    val tumKisiler: Flow<List<Partner>> = dao.tumunuGetir()

    fun kaydet(
        isim: String,
        yas: Int,
        ozellikler: String,
        notlar: String,
        foto: String?
    ) {
        viewModelScope.launch {
            dao.ekle(
                Partner(
                    isim = isim,
                    yas = yas,
                    sevdigiOzellikler = ozellikler,
                    hatirlanacakSeyler = notlar,
                    fotoUri = foto
                )
            )
        }
    }

    fun guncelle(partner: Partner) {
        viewModelScope.launch { dao.guncelle(partner) }
    }

    fun sil(partner: Partner) {
        viewModelScope.launch { dao.sil(partner) }
    }
}

class PartnerViewModelFactory(private val dao: PartnerDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PartnerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PartnerViewModel(dao) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel sınıfı")
    }
}
