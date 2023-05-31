package com.mason.touristattractionshw.ui.attraction

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.model.network.AttractionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AttractionViewModel(application: Application) : AndroidViewModel(application) {

    var attractionRepository = AttractionRepository.getRepository()
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private var _attractionListLiveData: MutableLiveData<List<Attraction>> = MutableLiveData(
        mutableListOf()
    )

    fun getExistAttractionList() = attractionRepository.allAttractionList.values.toList()


    val attractionListLiveData
        get() = _attractionListLiveData

    fun initAttractions(lang: String) = viewModelScope.launch {
        Log.d(TAG, "XXXXX> initAttractions: attractionRespotory: ${attractionRepository}")

        CoroutineScope(Dispatchers.IO).launch {

            try {
//                refreshAttractions(lang)
                var isAttractionsListEmpty = attractionRepository.allAttractionList.isEmpty()
                if (isAttractionsListEmpty) {
                    refreshAttractions(lang)
                } else {
                    _attractionListLiveData.postValue(getExistAttractionList())
                }

                Log.d(
                    TAG,
                    "XXXXX> initAttractions: isAttractionsListEmpty: $isAttractionsListEmpty"
                )
                Log.d(
                    TAG,
                    "XXXXX> initAttractions: allAttractionList.size = ${attractionRepository.allAttractionList.size}"
                )
            } catch (e: Exception) {
                Log.e(Companion.TAG, "XXXXX> initAttractions: e: ", e)
            }


        }

    }

    fun refreshAttractions(lang: String) {
        if (lang == attractionRepository.language && attractionRepository.allAttractionList.isNotEmpty()) {
            _attractionListLiveData.value = getExistAttractionList()

        } else {
            viewModelScope.launch {
                try {
                    attractionRepository.fetchFirstPage(lang)
                    _attractionListLiveData.postValue(getExistAttractionList())
                } catch (e: Exception) {
                    Log.e(TAG, "XXXXX> refreshAttractions: e", e)
                }
            }
        }


    }

    private suspend fun fetchFirstPage(lang: String) {
        attractionRepository.fetchFirstPage(lang)
        _attractionListLiveData.postValue(getExistAttractionList())
    }

    companion object {
        private const val TAG = "AttractionViewModel"
    }
}