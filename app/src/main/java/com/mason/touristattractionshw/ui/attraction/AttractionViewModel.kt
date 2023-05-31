package com.mason.touristattractionshw.ui.attraction

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.model.network.AttractionApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AttractionViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private var _attractionListLiveData :MutableLiveData<List<Attraction>> = MutableLiveData(listOf<Attraction>())
    val attractionListLiveData
        get() = _attractionListLiveData

    fun initAttractions(lang: String) = viewModelScope.launch {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                var attractionApi = AttractionApi.create()
                var fetchAttraction = attractionApi.fetchAttraction(  lang, 1)
                var totalAttractions = fetchAttraction.total
                var data = fetchAttraction.data
                _attractionListLiveData.postValue(data)
                Log.d(TAG, "XXXXX> initAttractions: totalAttractions: $totalAttractions")
                Log.d(TAG, "XXXXX> initAttractions: data.size = ${data.size}")
            } catch (e: Exception) {
                Log.e(Companion.TAG, "XXXXX> initAttractions: e: ", e)
            }


        }

    }

    companion object {
        private const val TAG = "AttractionViewModel"
    }
}