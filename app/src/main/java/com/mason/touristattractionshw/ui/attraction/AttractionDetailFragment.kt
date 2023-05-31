package com.mason.touristattractionshw.ui.attraction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.mason.touristattractionshw.R
import com.mason.touristattractionshw.databinding.FragmentAttractionDetailBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ATTRACTION_ID = "attraction_id"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AttractionDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AttractionDetailFragment : Fragment() {
    private lateinit var attractionPhotoAdapter: AttractionPhotoAdapter
    private var param_id: Int = -1
    private var param2: String? = null

    private val attractionDetailViewmModel by activityViewModels<AttractionDetailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param_id = it.getInt(ARG_ATTRACTION_ID)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentAttractionDetailBinding>(
            layoutInflater,
            R.layout.fragment_attraction_detail,
            container,
            false
        )
        binding.apply {
            layoutAttraction = attractionDetailViewmModel.getAttraction(param_id)
            lifecycleOwner = viewLifecycleOwner
        }

        attractionPhotoAdapter = AttractionPhotoAdapter(attractionDetailViewmModel)
        binding.recyclerViewPhotos.adapter = attractionPhotoAdapter

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AttractionDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AttractionDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ATTRACTION_ID, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}