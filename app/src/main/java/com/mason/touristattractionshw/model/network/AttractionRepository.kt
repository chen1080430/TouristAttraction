package com.mason.touristattractionshw.model.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mason.touristattractionshw.model.Attraction
import kotlinx.coroutines.flow.Flow

class AttractionRepository(val attractionApi: AttractionApi) {
    var language: MutableLiveData<String> = MutableLiveData("zh-tw")
    var attractionPagingSource: AttractionPagingSource? = null

    fun fetchAttractionPageFlow(lang: String): Flow<PagingData<Attraction>> {
        attractionPagingSource =
            AttractionPagingSource(attractionApi, lang)
        return Pager(
            config = PagingConfig(pageSize = 30, initialLoadSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { attractionPagingSource!! }
        ).flow
    }

    fun getAttraction(id: Int): Attraction {
        Log.d(
            TAG,
            "XXXXX> getAttraction: id: $id , " +
                    "attractionRespotory.size: ${attractionPagingSource?.allAttractionList?.size}"
        )
        return attractionPagingSource?.allAttractionList?.get(id) ?: Attraction(
            id = -1,
            name = "empty0"
        )
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