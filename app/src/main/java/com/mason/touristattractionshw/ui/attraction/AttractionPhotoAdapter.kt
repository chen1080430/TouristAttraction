package com.mason.touristattractionshw.ui.attraction

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.mason.touristattractionshw.R
import com.mason.touristattractionshw.databinding.AttractionPhotosItemBinding
import com.mason.touristattractionshw.model.AttractionImage
import com.mason.touristattractionshw.util.LogUtil

class AttractionDetailComparator : DiffUtil.ItemCallback<AttractionImage>() {
    override fun areItemsTheSame(oldItem: AttractionImage, newItem: AttractionImage): Boolean {
        return oldItem.src == newItem.src
    }

    override fun areContentsTheSame(oldItem: AttractionImage, newItem: AttractionImage): Boolean {
        return oldItem == newItem
    }

}

class AttractionPhotoAdapter(val viewModel: AttractionDetailViewModel) :
    ListAdapter<AttractionImage, AttractionPhotoAdapter.AttractionDetailViewHolder>(
        AttractionDetailComparator()
    ) {
    class AttractionDetailViewHolder(val binding: AttractionPhotosItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var startTime = 0L

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttractionDetailViewHolder {
        var binding = DataBindingUtil.inflate<AttractionPhotosItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.attraction_photos_item,
            parent,
            false
        )

        return AttractionDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttractionDetailViewHolder, position: Int) {
        holder.startTime = System.currentTimeMillis()
        holder.binding.progressBar.visibility = View.VISIBLE
        var item = getItem(position)
        var screenWidth = holder.itemView.context.resources.displayMetrics.widthPixels
        item.let {
//            Picasso.get().load(item.src)
////                .fit()
////                .resize(1000, 1000)
//                .resize(screenWidth, 0)
//                .onlyScaleDown()
//                .into(holder.binding.imageViewDetailPhoto)
            //glide
            Glide.with(holder.itemView.context)
                .load(item.src)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(
                    RequestOptions().override(screenWidth, 0)
                )
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.binding.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.binding.progressBar.visibility = View.GONE
                        val endTime = System.currentTimeMillis()
                        val duration = endTime - holder.startTime
                        LogUtil.d(
                            "ImageLoadTime",
                            "XXXXX> ${position+1} image.duration: $duration ms"
                        )
                        return false
                    }
                })
                .into(holder.binding.imageViewDetailPhoto)
        }
    }

}
