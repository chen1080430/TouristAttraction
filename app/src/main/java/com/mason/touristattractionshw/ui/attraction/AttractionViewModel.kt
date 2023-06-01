package com.mason.touristattractionshw.ui.attraction

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.model.network.AttractionRepository
import kotlinx.coroutines.flow.Flow

class AttractionViewModel(application: Application) : AndroidViewModel(application) {

    var attractionRepository = AttractionRepository.getRepository()
    val langLiveData = attractionRepository.language

    fun fetchAttractionPageFlow(lang: String): Flow<PagingData<Attraction>> {
        Log.d(TAG, "XXXXX> fetchAttractionPageFlow: lang: $lang")
        return attractionRepository.fetchAttractionPageFlow(lang).cachedIn(viewModelScope)
    }

    fun updateFlowWithNewLang(lang: String) {
        attractionRepository.language.value = lang
    }

    companion object {
        private const val TAG = "AttractionViewModel"
    }
}