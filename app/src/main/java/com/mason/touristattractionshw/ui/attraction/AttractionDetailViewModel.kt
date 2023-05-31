package com.mason.touristattractionshw.ui.attraction

import android.app.Application
import android.provider.LiveFolders
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.model.AttractionImage
import com.mason.touristattractionshw.model.network.AttractionRepository

class AttractionDetailViewModel(application: Application) : AndroidViewModel(application) {

    val attractionRepository = AttractionRepository.getRepository()

    private var _attraction : MutableLiveData<Attraction> = MutableLiveData()
    val attraction : LiveData<Attraction>
        get() = _attraction

    var attractionImageLiveData :LiveData<List<AttractionImage>> = _attraction.map { attraction ->
        attraction.images
    }

    fun getAttraction(id: Int) : Attraction {
        Log.d(Companion.TAG, "XXXXX> getAttraction: attractionRespotory: ${attractionRepository}")
        return attractionRepository.getAttraction(id).apply {
            _attraction.value = this
        }
    }

    companion object {
        private const val TAG = "AttractionDetailViewMod"
    }

}
