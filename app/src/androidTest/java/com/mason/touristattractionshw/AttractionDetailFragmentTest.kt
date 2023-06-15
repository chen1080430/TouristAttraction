package com.mason.touristattractionshw

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.model.AttractionImage
import com.mason.touristattractionshw.ui.attraction.AttractionDetailFragment
import com.mason.touristattractionshw.ui.attraction.AttractionDetailViewModel
import org.junit.After
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import io.mockk.mockkObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


@RunWith(AndroidJUnit4::class)
class AttractionDetailFragmentTest {

    private lateinit var viewModel: AttractionDetailViewModel
    private lateinit var scenario: FragmentScenario<AttractionDetailFragment>

    private val attraction = Attraction(
        id = 26,
        name = "陽明山中山樓",
        introduction = "陽明山國家公園內的中山樓，是蔣介石總統在位時，為了紀念國父孫中山先生百年誕辰，於1965年所興建，由名建築師修澤蘭女士規劃，位於群山環抱的綠意之中，外表為中國傳統古典式建築，內部陳設典雅細緻，過去是國民大會的會場，並為國家元首接待外賓或舉辦國宴的重要場地，如今已被指定為市立古蹟，並開放場地租借及參觀導覽，是一座具有歷史紀念價值的建築物。",
        url = "http://chungshanhall.ntl.edu.tw/cht/index.php?",
        address = "112 臺北市北投區陽明路二段15號",
        modified = "2023-06-12 17:00:52 +08:00",
        official_site = "http://chungshanhall.ntl.edu.tw/cht/index.php?",
        images = listOf(
            AttractionImage(
                src = "https://www.travel.taipei/image/214231",
                subject = "",
                ext = ".jpg"
            )
        )
    )

    @Before
    fun setUp() {
        val fragmentArgs = bundleOf("attraction_id" to 26)
        scenario = launchFragmentInContainer<AttractionDetailFragment>(
            initialState = Lifecycle.State.RESUMED, fragmentArgs = fragmentArgs
        )
//        scenario.recreate()

        viewModel = mockk<AttractionDetailViewModel>(relaxed = true)
        every { viewModel.getAttraction(26) } returns attraction
//        scenario.onFragment { fragment ->
//        }

    }

    @After
    fun tearDown() {
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun `test AttractionDetailFragment UI with mock attraciton`() {
        println("attraction: ${attraction}")
        runBlocking {
            delay(3000)
            onView(withId(R.id.textView_detail_name)).check(matches(isDisplayed()))
            onView(withId(R.id.textView_detail_name)).check((matches(withText("empty0"))))
            onView(withId(R.id.textView_detail_modify)).check(matches(isDisplayed()))
            onView(withId(R.id.textView_detail_address)).check(matches(isDisplayed()))
            onView(withId(R.id.textView_detail_description)).check(matches(isDisplayed()))
            onView(withId(R.id.textView_detail_official_site)).check(matches(isDisplayed()))
        }

    }

}