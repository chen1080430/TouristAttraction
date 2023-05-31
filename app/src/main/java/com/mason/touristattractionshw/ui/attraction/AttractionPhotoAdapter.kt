package com.mason.touristattractionshw.ui.attraction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mason.touristattractionshw.R
import com.mason.touristattractionshw.databinding.AttractionPhotosItemBinding
import com.mason.touristattractionshw.model.Attraction
import com.mason.touristattractionshw.model.AttractionAdapter
import com.mason.touristattractionshw.model.AttractionImage
import com.squareup.picasso.Picasso

class AttractionDetailComparator : DiffUtil.ItemCallback<AttractionImage>() {
    override fun areItemsTheSame(oldItem: AttractionImage, newItem: AttractionImage): Boolean {
        return oldItem.src == newItem.src
    }

    override fun areContentsTheSame(oldItem: AttractionImage, newItem: AttractionImage): Boolean {
        return oldItem == newItem
    }

}

class AttractionPhotoAdapter(val viewModel: AttractionDetailViewModel) : ListAdapter<AttractionImage, AttractionPhotoAdapter.AttractionDetailViewHolder>(AttractionDetailComparator()) {
    class AttractionDetailViewHolder(val binding: AttractionPhotosItemBinding) : RecyclerView.ViewHolder(binding.root) {

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

        return  AttractionDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttractionDetailViewHolder, position: Int) {
        var item = getItem(position)
        var screenWidth = holder.itemView.context.resources.displayMetrics.widthPixels
        item.let {
            Picasso.get().load(item.src)
//                .fit()
//                .resize(1000, 1000)
                .resize(screenWidth, 0)
                .onlyScaleDown()
                .into(holder.binding.imageViewDetailPhoto)
        }
    }


}
