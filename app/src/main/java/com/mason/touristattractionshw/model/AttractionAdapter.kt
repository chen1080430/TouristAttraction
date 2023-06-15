package com.mason.touristattractionshw.model

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mason.touristattractionshw.R
import com.mason.touristattractionshw.databinding.AttractionItemBinding
import com.mason.touristattractionshw.ui.attraction.AttractionListFragmentDirections
import com.mason.touristattractionshw.ui.attraction.AttractionViewModel
import com.mason.touristattractionshw.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//import com.squareup.picasso.Picasso

class AttractionAdapter(val attractionViewModel: AttractionViewModel) :
    PagingDataAdapter<Attraction, AttractionAdapter.ViewHolder>(AttractionDiffCallback()) {

    class ViewHolder(val binding: AttractionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var startTime = 0L
        fun bind(holder: ViewHolder, attractionViewModel: AttractionViewModel, item: Attraction) {
            binding.apply {
                layoutAttraction = item
                layoutViewModel = attractionViewModel
                executePendingBindings()
            }
            binding.attractionLayout.setOnClickListener { view ->
                LogUtil.d(TAG, "XXXXX> bind: navigation to detail attraciton: ${item.id}")
                var navigationAttractionDetail =
                    AttractionListFragmentDirections.navigationAttractionDetail(item.id)

                view.findNavController().navigate(navigationAttractionDetail)
            }
            var height = binding.root.resources.getDimension(R.dimen.attraction_item_height).toInt()
            if (item.images.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    Glide.with(binding.root)
                        .load(item.images[0].src)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(0, height)
            /*
                        .listener(object : RequestListener<Drawable> {

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
                                val duration = endTime - holder.startTime
//                                LogUtil.d("ImageLoadTime", "XXXXX> ${item.name} image.duration: $duration ms")
                                return false
                            }
                        })

                 */
                        .into(binding.imageViewPhoto)
                }
//                Picasso.get().load(item.images[0].src).resize(0, height).onlyScaleDown().into(binding.imageViewPhoto)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding =
            AttractionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.startTime = System.currentTimeMillis()
        var item = getItem(position)!!
        holder.bind(holder, attractionViewModel, item)
    }

    companion object {
        private const val TAG = "AttractionAdapter"
    }

}

class AttractionDiffCallback : DiffUtil.ItemCallback<Attraction>() {
    override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
        return oldItem == newItem
    }

}
