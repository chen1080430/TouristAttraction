package com.mason.touristattractionshw

import android.content.Context
import android.net.ConnectivityManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mason.touristattractionshw.model.network.AttractionApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AttractionApiTest {

    private lateinit var attractionApiService: AttractionApi

    val LANG = listOf(
        "en", "zh-tw", "zh-cn", "ja", "ko", "es", "th", "vi"
//        server unavailable
//        , "id"
    )

    @Before
    fun setup() {
        attractionApiService = AttractionApi.create()
    }

    @Test
    fun `test network connectivity`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        val isConnected = activeNetwork?.isConnectedOrConnecting == true

        println("Network connected: $isConnected")
        Assert.assertTrue(isConnected)
    }

    @Test
    fun `fetchAttraction all language`() {
        runBlocking {
            for (i in LANG.indices) {
                println("current test language: ${LANG[i]}")
                val response = attractionApiService.fetchAttraction(LANG[i], 1)
                response.apply {
                    Assert.assertNotNull(total)
                    Assert.assertNotNull(data)
                    // check first item in page
                    Assert.assertNotNull(data.get(1).name)
                    Assert.assertNotNull(data.get(1).introduction)

                    // check last item in page
                    Assert.assertNotNull(data.get(data.lastIndex).name)
                    Assert.assertNotNull(data.get(data.lastIndex).introduction)

                }
            }
        }
    }

    @Test
    fun `fetchAttraction response successful`() {
        runBlocking {
            for (i in LANG.indices) {
                val response = attractionApiService.fetchAttractionResponse(LANG[i], 1)

                // check response code
                Assert.assertEquals(200, response.code())
                Assert.assertEquals(true, response.isSuccessful)

                response.body()?.let {
                    Assert.assertNotNull(it.data)
                    Assert.assertNotNull(it.total)
                    Assert.assertNotNull(it.data.get(it.data.lastIndex).name)
                    Assert.assertNotNull(it.data.get(it.data.lastIndex).introduction)
                }
            }
        }
    }
}
