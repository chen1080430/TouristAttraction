package com.mason.touristattractionshw.ui.attraction

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.model.network.AttractionPagingSource
import com.mason.touristattractionshw.model.network.AttractionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AttractionViewModel(application: Application) : AndroidViewModel(application) {

    private var currentFlow: Flow<PagingData<Attraction>>? = null
    var attractionRepository = AttractionRepository.getRepository()
    //    val langLiveData: MutableLiveData<String> = MutableLiveData("zh-tw")
    val langLiveData= attractionRepository.language

    //    val attractionPagingSource by lazy { AttractionPagingSource(attractionRepository.attractionApi, "zh-tw") }
//    val attractionPagingSource by lazy { attractionRepository.attractionPagingSource }
    var attractionPagingSource
        get() = attractionRepository.attractionPagingSource
        set(value) {
            attractionRepository.attractionPagingSource = value
        }
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

//    var currentLang
//        get() = attractionRepository.language
//        set(value) {
//            attractionRepository.language = value
//        }

    private var _attractionListLiveData: MutableLiveData<List<Attraction>> = MutableLiveData(
        mutableListOf()
    )

    fun getExistAttractionList() = attractionRepository.allAttractionList.values.toList()


    val attractionListLiveData
        get() = _attractionListLiveData

    /*
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


     */
    /*

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



    fun setQueryLanguage(lang: String) {
        attractionRepository.language = lang
    }

     */

    //    fun fetchAttractionPageFlow(lang: String): Flow<PagingData<Attraction>> {
//        Log.d(TAG, "XXXXX> fetchAttractionPageFlow: lang: $lang")
////        setQueryLanguage(lang)
//        attractionPagingSource =
//            AttractionPagingSource(attractionRepository, attractionRepository.attractionApi, lang)
//        return Pager(
//            config = PagingConfig(pageSize = 30, initialLoadSize = 30, enablePlaceholders = false),
//            pagingSourceFactory = { attractionPagingSource!! }
//        ).flow.cachedIn(viewModelScope)
//    }
    fun fetchAttractionPageFlow(lang: String): Flow<PagingData<Attraction>> {
        Log.d(TAG, "XXXXX> fetchAttractionPageFlow: lang: $lang")
        return attractionRepository.fetchAttractionPageFlow(lang).cachedIn(viewModelScope)
    }


    /*
        fun fetchAttractionPageFlow(lang: String): Flow<PagingData<Attraction>> {
            if (lang != currentLang) {
                // LANG 值發生變化，重新設置 Flow
    //            setQueryLanguage(lang)
                currentLang = lang
                currentFlow = Pager(
                    config = PagingConfig(pageSize = 30, initialLoadSize = 30, enablePlaceholders = false),
                    pagingSourceFactory = { attractionPagingSource }
                ).flow.cachedIn(viewModelScope)
            }

            return currentFlow ?: Pager(
                config = PagingConfig(pageSize = 30, initialLoadSize = 30, enablePlaceholders = false),
                pagingSourceFactory = { attractionPagingSource }
            ).flow.cachedIn(viewModelScope)
        }
    */


    // 在 LANG 更改時重新更新 Flow
    fun updateFlowWithNewLang(lang: String) {
        attractionRepository.language.value = lang
//        langLiveData.value = lang
//        currentLang = lang
//        currentFlow = null
    }

//    fun getAttractionById(id: Int): Attraction? {
//        return attractionPagingSource.allAttractionList.get(id)
//    }

    /*
    private suspend fun fetchFirstPage(lang: String) {
        attractionRepository.fetchFirstPage(lang)
        _attractionListLiveData.postValue(getExistAttractionList())
    }


     */

    companion object {
        private const val TAG = "AttractionViewModel"
    }
}