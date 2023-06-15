package com.mason.touristattractionshw.model.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.util.LogUtil
import kotlinx.coroutines.flow.Flow

class AttractionRepository(val attractionApi: AttractionApi) {
    var language: MutableLiveData<String> = MutableLiveData("zh-tw")
    var attractionPagingSource: AttractionPagingSource? = null

    fun fetchAttractionPageFlow(lang: String): Flow<PagingData<Attraction>> {
        if (attractionPagingSource == null || lang != attractionPagingSource!!.lang) {
            attractionPagingSource?.let {

                LogUtil.d(TAG, "XXXXX> fetchAttractionPageFlow: old.language: ${it.lang} , <-> new: $lang")
            } ?.run {
                LogUtil.d(
                    TAG,
                    "XXXXX> fetchAttractionPageFlow: new a attractionPagingSource, old: $attractionPagingSource"
                )
            }
            attractionPagingSource =
                AttractionPagingSource(attractionApi, lang)
        }
        LogUtil.d(
            TAG,
            "XXXXX> fetchAttractionPageFlow: lang: $lang , attractionPagingSource: $attractionPagingSource"
        )
        return Pager(
            config = PagingConfig(pageSize = 30, initialLoadSize = 30, enablePlaceholders = true),
            pagingSourceFactory = { attractionPagingSource!! }
        ).flow
    }

    fun getAttraction(id: Int): Attraction {
        LogUtil.d(
            TAG,
            "XXXXX> getAttraction: id: $id , " +
                    "attractionRespotory.size: ${attractionPagingSource?.allAttractionList?.size}"
        )
        return attractionPagingSource?.allAttractionList?.get(id) ?: Attraction(
            id = -1,
            name = "empty0",
            url = "https://www.google.com/"
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