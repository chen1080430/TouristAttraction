package com.mason.touristattractionshw.model.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.model.network.AttractionRepository.Companion.NETWORK_PAGE_SIZE

class AttractionPagingSource(
    val attractionRepository: AttractionRepository,
    val attractionApi: AttractionApi,
    val lang: String,
) :
    PagingSource<Int, Attraction>() {
    private var totalPages: Int = Int.MAX_VALUE

    //    val allAttractionList = mutableListOf<Attraction>()
    val allAttractionList = LinkedHashMap<Int, Attraction>(0)

    private var language : String = "zh-tw"

    private val STARTING_PAGE_INDEX = 1

//    fun getLanguage(): String {
//        return attractionRepository.language
//    }

    override fun getRefreshKey(state: PagingState<Int, Attraction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Attraction> {
        /*
        val position = params.key ?: STARTING_PAGE_INDEX
        val pageSize = params.loadSize
        Log.d(
            Companion.TAG, "XXXXX> load: position: ${position}, pageSize: ${pageSize}")
        return try {
            val response = attractionApi.fetchAttraction(queryLang, position)
            val repos = response.data
            var attraction0 = repos.get(0)
            Log.d(TAG, "XXXXX> load: repos.size: ${repos.size}\n attraction0: ${attraction0.id}, name: ${attraction0.name}")
//            val nextKey = if (repos.isEmpty()) {
//                null
//            } else {
//                // initial load size = 3 * NETWORK_PAGE_SIZE
//                // ensure we're not requesting duplicating items, at the 2nd request
////                    position + (params.loadSize / NETWORK_PAGE_SIZE)
//                    position + 1
//            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = position +1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
        }

*/
        try {
            val page = params.key ?: STARTING_PAGE_INDEX
//            var languageChanged = getLanguage() != language
//            Log.d(TAG, "XXXXX> load: page: ${page}, language: ${language}, getLanguage: ${getLanguage()}, totalPages: $totalPages  , this: $this")

//            if (!languageChanged && allAttractionList.isNotEmpty() && page == totalPages) {
//                Log.d(TAG, "XXXXX> load: use cache: allattractionList.size: ${allAttractionList.size} , language: ${language}")
//                return LoadResult.Page(
//                    data = allAttractionList.values.toList(),
//                    prevKey = null,
//                    nextKey = null
//                )
//            }

            val response = attractionApi.fetchAttraction(lang, page)
            var floor = (response.total.toDouble() / NETWORK_PAGE_SIZE)
            totalPages = floor.toInt() + 2

            Log.d(TAG, "XXXXX> load: response.total :  lang : $lang , ${response.total}, totalPages: $totalPages , floor: $floor  , this: $this")
            if (page == STARTING_PAGE_INDEX /*|| languageChanged*/) {
                allAttractionList.clear()
            }

            val attractions = response.data

            attractions.forEach { attraction ->
                allAttractionList.put(attraction.id, attraction)
            }
//            language = getLanguage()
            Log.d(TAG, "XXXXX> load: lang: $lang, page: ${page}, attractions.size: ${attractions.size} , allAttractionList.size: ${allAttractionList.size}  , this: $this")
            val prevKey = if (page > STARTING_PAGE_INDEX) page - 1 else null
            val nextKey = if (attractions.isNotEmpty()) page + 1 else null

            return LoadResult.Page(
                data = attractions,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Log.e(TAG, "XXXXX> load: e", e)
            return LoadResult.Error(e)
        }
    }


companion object {
    private const val TAG = "AttractionPagingSource"
}
}