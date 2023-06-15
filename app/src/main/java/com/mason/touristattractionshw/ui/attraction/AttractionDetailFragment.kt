package com.mason.touristattractionshw.ui.attraction

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.mason.touristattractionshw.R
import com.mason.touristattractionshw.databinding.FragmentAttractionDetailBinding
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.util.LogUtil
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [AttractionDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AttractionDetailFragment : Fragment() {
    private lateinit var attraction: Attraction
    private lateinit var attractionPhotoAdapter: AttractionPhotoAdapter
    private var param_id: Int = -1
    private var param2: String? = null

    private val attractionDetailViewModel by activityViewModels<AttractionDetailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param_id = it.getInt(ARG_ATTRACTION_ID)
            LogUtil.d(Companion.TAG, "XXXX> onCreate: param_id = $param_id")
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
        attraction = attractionDetailViewModel.getAttraction(param_id)
        binding.apply {
            layoutAttraction = attraction
            lifecycleOwner = viewLifecycleOwner
        }

        var imageCount = attraction.images.size.also {
            if (it==0) {
                binding.recyclerViewPhotos.visibility = View.GONE
            }
        }
        LogUtil.d(Companion.TAG, "XXXX> onCreateView: imageCount = $imageCount")

        attractionPhotoAdapter = AttractionPhotoAdapter(attractionDetailViewModel)
        binding.recyclerViewPhotos.adapter = attractionPhotoAdapter


        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerViewPhotos)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        layoutManager.setItemPrefetchEnabled(true)
        layoutManager.initialPrefetchItemCount = 1
        binding.recyclerViewPhotos.layoutManager = layoutManager

        attractionDetailViewModel.attractionImageLiveData.observe(viewLifecycleOwner) {
            LogUtil.d(Companion.TAG, "XXXX> onCreateView: attractionImageLiveData = ${it.size}")
            attractionPhotoAdapter.submitList(it)

        }
        /*
        lifecycleScope.launch {
            for (i in attraction.images.indices) {
                val startTime = System.currentTimeMillis()
//                LogUtil.d("ImageLoadTime", "XXXXX> ${attraction.name} image.start: $startTime ms")
                Glide.with(binding.root)
                    .load(attraction.images[i])
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .apply ( RequestOptions()
//                        .placeholder(R.drawable.ic_suitcase)
//                     .error(android.R.drawable.stat_notify_error))

//                    .into()
                    .listener(object : RequestListener<Drawable> {
//                            override fun onLoadStarted(placeholder: Drawable?) {
//                                // 當圖片加載開始時，紀錄開始時間
//                                holder.startTime = System.currentTimeMillis()
//                                return false
//                            }


                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            val endTime = System.currentTimeMillis()
                            val duration = endTime - startTime
                            LogUtil.d("ImageLoadTime", "XXXXX> ${attraction.name} image.duration: $duration ms")
                            return false
                        }
                    })

                    .into(binding.invisibleImageView)
            }
        }

         */


        return binding.root
    }

    companion object {
        private const val ARG_ATTRACTION_ID = "attraction_id"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AttractionDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ATTRACTION_ID, param1)
                }
            }

        private const val TAG = "AttractionDetailFragmen"
    }
}