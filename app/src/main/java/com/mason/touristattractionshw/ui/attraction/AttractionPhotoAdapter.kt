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
import com.squareup.picasso.Picasso

class AttractionDetailComparator : DiffUtil.ItemCallback<Attraction>() {
    override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
        return oldItem == newItem
    }

}

class AttractionPhotoAdapter(val viewModel: AttractionDetailViewModel) : ListAdapter<Attraction, AttractionPhotoAdapter.AttractionDetailViewHolder>(AttractionDetailComparator()) {
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
        if (item.images.isNotEmpty()) {
            Picasso.get().load(item.images[0].src).into(holder.binding.imageViewDetailPhoto)
        }
    }


}
