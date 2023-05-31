package com.mason.touristattractionshw.ui.attraction

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mason.touristattractionshw.R
import com.mason.touristattractionshw.databinding.FragmentAttractionListBinding
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.model.AttractionAdapter

class AttractionListFragment : Fragment() {

    private var LANG: String = "zh-tw"
    private lateinit var attractionAdapter: AttractionAdapter
    private var _binding: FragmentAttractionListBinding ?= null

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

        attractionViewModel.attractionListLiveData.observe(viewLifecycleOwner) {
//            Log.d(Companion.TAG, "XXXXX> onCreateView: attractions: $it")
            Log.d(Companion.TAG, "XXXXX> onCreateView: attractions: ${it.size}")

            attractionAdapter.submitList(it)
        }


        return root
    }

    private fun init() {
        setHasOptionsMenu(true)
        attractionAdapter = AttractionAdapter(attractionViewModel)
//        addTestAttraction()
        attractionViewModel.initAttractions(LANG)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.language_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_language -> {
                attractionViewModel.refreshAttractions("en")
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addTestAttraction() {
        var testAttractionList = mutableListOf<Attraction>()
        testAttractionList.add(Attraction(id = 1, name = "test1"))
        testAttractionList.add(Attraction(id = 2, name = "test2"))
        testAttractionList.add(Attraction(id = 3, name = "test3"))
        testAttractionList.add(Attraction(id = 4, name = "test4"))
        testAttractionList.add(Attraction(id = 5, name = "test5"))
        attractionAdapter.submitList(testAttractionList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "AttractionListFragment"
    }
}