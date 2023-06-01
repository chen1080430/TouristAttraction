package com.mason.touristattractionshw.model.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.ui.attraction.AttractionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AttractionRepository(val attractionApi: AttractionApi) {
    private var totalPages: Int = 1

    val allAttractionList = LinkedHashMap<Int, Attraction>(1)
    var language: MutableLiveData<String> = MutableLiveData("zh-tw")
//    var language: String = "zh-tw"
    var attractionPagingSource : AttractionPagingSource ?= null

    fun fetchAttractionPageFlow(lang: String): Flow<PagingData<Attraction>> {
        Log.d(TAG, "XXXXX> fetchAttractionPageFlow: lang: $lang")
//        setQueryLanguage(lang)
        attractionPagingSource =
            AttractionPagingSource(this, attractionApi, lang)
        return Pager(
            config = PagingConfig(pageSize = 30, initialLoadSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { attractionPagingSource!! }
        ).flow
    }
    /*
    suspend fun fetchAllAttractions(lang: String = "zh-tw"):  LinkedHashMap<Int, Attraction> {
        withContext(Dispatchers.IO) {

            try {
//                fetchFirstPage(lang)
                for (i in 2..fetchFirstPage(lang)) {
                    var attractions = attractionApi.fetchAttraction(lang, i)
                    attractions.data.forEach { attraction ->
                        allAttractionList.put(attraction.id, attraction)
                    }
                }
                var size = allAttractionList.size
                Log.d(TAG, "XXXXX> fetchAllAttractions: total size: ${size}")
            } catch (e: Exception) {
                Log.e(TAG, "XXXXX> fetchAllAttractions: e", e)
                return@withContext allAttractionList
            }
            Log.d(TAG, "XXXXX> fetchAllAttractions: finally return true")
            return@withContext allAttractionList
        }
        Log.d(TAG, "XXXXX> fetchAllAttractions:default return false")
        return allAttractionList
    }

     */

    /*
     suspend fun fetchFirstPage(lang: String) : Int {
         allAttractionList.clear()
        try {
            var firstPageAttraction = attractionApi.fetchAttraction(lang, 1)
            totalPages = firstPageAttraction.total / NETWORK_PAGE_SIZE
            firstPageAttraction
        .data.forEach { attraction ->
            allAttractionList.put(attraction.id, attraction)
        }
            language = lang
        } catch (e: Exception) {
            Log.e(TAG, "XXXXX> fetchFirstPage: e", e)
        }
        return totalPages
    }

     */

//    fun getAttraction(id: Int) : Attraction {
//Log.d(TAG, "XXXXX> getAttraction: attractionRespotory.size: ${allAttractionList.size}")
//            return allAttractionList.get(id)?: Attraction(id = -1, name = "empty0")
//    }

    fun getAttraction(id: Int): Attraction {
        Log.d(TAG, "XXXXX> getAttraction: attractionRespotory.size: ${attractionPagingSource?.allAttractionList?.size}")
        return attractionPagingSource?.allAttractionList?.get(id) ?: Attraction(id = -1, name = "empty0")
    }

    companion object {
        val NETWORK_PAGE_SIZE = 30
        private const val TAG = "AttractionRepository"
        private var instance: AttractionRepository? = null
        fun getRepository(): AttractionRepository {
            if (instance == null) {
                instance = AttractionRepository(AttractionApi.create())
            }
            return instance!!
        }
    }
}