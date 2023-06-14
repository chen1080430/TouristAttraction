package com.mason.touristattractionshw.model.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.model.network.AttractionRepository.Companion.NETWORK_PAGE_SIZE
import com.mason.touristattractionshw.util.LogUtil

class AttractionPagingSource(
    val attractionApi: AttractionApi,
    val lang: String,
) :
    PagingSource<Int, Attraction>() {
    private val CACHE_FLAG = false
    private var totalPages: Int = Int.MAX_VALUE
    val allAttractionList = LinkedHashMap<Int, Attraction>(0)
    private val STARTING_PAGE_INDEX = 1

    override fun getRefreshKey(state: PagingState<Int, Attraction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Attraction> {
        try {
            val page = params.key ?: STARTING_PAGE_INDEX
//            LogUtil.d(TAG, "XXXXX> load: page: ${page}, language: ${language}, getLanguage: ${getLanguage()}, totalPages: $totalPages  , this: $this")


            val response = attractionApi.fetchAttraction(lang, page)
            var allAttractionsPages = (response.total.toDouble() / NETWORK_PAGE_SIZE)
            totalPages = allAttractionsPages.toInt() + 2

            var cahceSizeCorrect =
                allAttractionList.size >= allAttractionsPages.toInt() * NETWORK_PAGE_SIZE
            if (CACHE_FLAG && allAttractionList.isNotEmpty() && cahceSizeCorrect && page == STARTING_PAGE_INDEX) {
                LogUtil.d(
                    TAG,
                    "XXXXX> load: use cache: allattractionList.size: ${allAttractionList.size} , page: $page , language: ${lang}"
                )
                return LoadResult.Page(
                    data = allAttractionList.values.toList(),
                    prevKey = null,
                    nextKey = null
                )
            }
            if (page == STARTING_PAGE_INDEX /*|| languageChanged*/) {
//                allAttractionList.clear()
            }

            val attractions = response.data

            attractions.forEach { attraction ->
                allAttractionList.put(attraction.id, attraction)
            }
            LogUtil.d(
                TAG,
                "XXXXX> load: lang: $lang, page: ${page} , total: ${response.total}, attractions.size: ${attractions.size} , allAttractionList.size: ${allAttractionList.size}  , this: $this"
            )
            val prevKey = if (page > STARTING_PAGE_INDEX) page - 1 else null
            val nextKey = if (attractions.isNotEmpty()) page + 1 else null

            return LoadResult.Page(
                data = attractions,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LogUtil.e(TAG, "XXXXX> load: e", e)
            return LoadResult.Error(e)
        }
    }


    companion object {
        private const val TAG = "AttractionPagingSource"
    }
}