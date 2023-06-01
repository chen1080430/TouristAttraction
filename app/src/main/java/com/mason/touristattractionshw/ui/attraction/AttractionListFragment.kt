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
import com.mason.touristattractionshw.MainActivity
import com.mason.touristattractionshw.R
import com.mason.touristattractionshw.databinding.FragmentAttractionListBinding
import com.mason.touristattractionshw.model.AttractionAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AttractionListFragment : Fragment() {

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

        attractionViewModel.langLiveData.observe(viewLifecycleOwner) { newLang ->
            Log.d(TAG, "XXXXX> onCreateView: new Language: $newLang")
            fetchAttractionData(newLang)
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
        }
    }

    private fun init() {
        setHasOptionsMenu(true)
        attractionAdapter = AttractionAdapter(attractionViewModel)
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
                Log.d(
                    TAG,
                    "XXXXX> onOptionsItemSelected: rv.childCount: $childCount , " +
                            "adapter.size: $itemCount"
                )
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
                var langSelect = "zh-tw"
                when (menuItem.itemId) {
                    R.id.language_en -> {
                        langSelect = "en"
                    }

                    R.id.language_zh_tw -> {
                        langSelect = "zh-tw"
                    }

                    R.id.language_zh_cn -> {
                        langSelect = "zh-cn"
                    }

                    R.id.language_ja -> {
                        langSelect = "ja"
                    }

                    R.id.language_ko -> {
                        langSelect = "ko"
                    }

                    R.id.language_es -> {
                        langSelect = "es"
                    }

                    R.id.language_th -> {
                        langSelect = "th"
                    }

                    // TODO server unavaliable
//                    R.id.language_id -> {
//                        langSelect = "id"
//                    }

                    else -> false
                }
                attractionViewModel.updateFlowWithNewLang(langSelect)
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