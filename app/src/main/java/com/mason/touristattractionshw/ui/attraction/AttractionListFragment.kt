package com.mason.touristattractionshw.ui.attraction

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.mason.touristattractionshw.MainActivity
import com.mason.touristattractionshw.R
import com.mason.touristattractionshw.databinding.FragmentAttractionListBinding
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.model.AttractionAdapter
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AttractionListFragment : Fragment() {

    private val attractionList = mutableListOf<Attraction>()
    private var LANG: String = "zh-tw"
    private lateinit var attractionAdapter: AttractionAdapter
    private var _binding: FragmentAttractionListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val attractionViewModel by activityViewModels<AttractionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAttractionListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        init()
        binding.recyclerAttractions.adapter = attractionAdapter

/*        lifecycleScope.launch {
            attractionViewModel.fetchAttractionPageFlow(LANG).collectLatest { attraction ->
                attractionAdapter.submitData(attraction)
            }
        }*/
//        attractionViewModel.attractionListLiveData.observe(viewLifecycleOwner) {
////            Log.d(Companion.TAG, "XXXXX> onCreateView: attractions: $it")
//            Log.d(Companion.TAG, "XXXXX> onCreateView: attractions: ${it.size}")
//
//            attractionAdapter.submitList(it)
//        }

        attractionViewModel.langLiveData.observe(viewLifecycleOwner) { newLang ->
            Log.d(TAG, "XXXXX> onCreateView: newLang: $newLang")
            LANG = newLang
            fetchAttractionData(LANG)
        }

        return root
    }

    private fun fetchAttractionData(lang: String) {
        lifecycleScope.launch {
            val attractionFlow = attractionViewModel.fetchAttractionPageFlow(lang)
            attractionAdapter.refresh()
            attractionFlow.collectLatest { attraction ->
                attractionAdapter.submitData(attraction)

            }
//            attractionAdapter.submitData(attractionFlow)
        }
    }

    private fun init() {
        setHasOptionsMenu(true)
        attractionAdapter = AttractionAdapter(attractionViewModel)
//        addTestAttraction()
//        attractionViewModel.initAttractions(LANG)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_language -> {
                showPopupMenu()
                var childCount = binding.recyclerAttractions.childCount
                var itemCount = attractionAdapter.itemCount
                Log.d(TAG, "XXXXX> onOptionsItemSelected: rv.childCount: $childCount , adapter.size: $itemCount , language: $LANG")
            }

            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showPopupMenu() {
        PopupMenu(
            requireActivity(),
            (requireActivity() as MainActivity).findViewById(R.id.nav_view),
            Gravity.RIGHT
        ).apply {
            menuInflater.inflate(R.menu.language_menu, menu)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.language_en -> {
                        LANG = "en"
                    }

                    R.id.language_zh_tw -> {
                        LANG = "zh-tw"
                    }

                    R.id.language_zh_cn -> {
                        LANG = "zh-cn"
                    }

                    R.id.language_ja -> {
                        LANG = "ja"
                    }

                    R.id.language_ko -> {
                        LANG = "ko"
                    }

                    R.id.language_es -> {
                        LANG = "es"
                    }

                    R.id.language_th -> {
                        LANG = "th"
                    }

//                    R.id.language_id -> {
//                        LANG = "id"
//                    }

                    else -> false
                }
//                attractionViewModel.refreshAttractions(LANG)
//                attractionViewModel.setQueryLanguage(LANG)
                attractionViewModel.updateFlowWithNewLang(LANG)
//                lifecycleScope.launch {
//                    attractionAdapter.refresh()
//
//                }

//                attractionAdapter.refresh()
                true

            }

            show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "AttractionListFragment"
    }
}