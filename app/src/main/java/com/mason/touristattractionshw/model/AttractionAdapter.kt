package com.mason.touristattractionshw.model

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mason.touristattractionshw.R
import com.mason.touristattractionshw.databinding.AttractionItemBinding
import com.mason.touristattractionshw.ui.attraction.AttractionListFragmentDirections
import com.mason.touristattractionshw.ui.attraction.AttractionViewModel
import com.squareup.picasso.Picasso

class AttractionAdapter(val attractionViewModel: AttractionViewModel) : PagingDataAdapter<Attraction, AttractionAdapter.ViewHolder>(AttractionDiffCallback()){

    class ViewHolder (val binding:AttractionItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(attractionViewModel: AttractionViewModel, item: Attraction) {
            binding.apply {
                layoutAttraction = item
                layoutViewModel = attractionViewModel
                executePendingBindings()
            }
            binding.attractionLayout.setOnClickListener { view ->
                Log.d(TAG, "XXXXX> bind: navigation to detail attraciton: ${item.id}")
                var navigationAttractionDetail =
                    AttractionListFragmentDirections.navigationAttractionDetail(item.id)

                view.findNavController().navigate(navigationAttractionDetail)
            }
            var height = binding.root.resources.getDimension(R.dimen.attraction_item_height).toInt()
            if (item.images.isNotEmpty()) {
                Picasso.get().load(item.images[0].src).resize(0, height).onlyScaleDown().into(binding.imageViewPhoto)
            }

        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = AttractionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = getItem(position)!!
        holder.bind(attractionViewModel,  item)
    }

    companion object {
        private const val TAG = "AttractionAdapter"
    }

}

class AttractionDiffCallback : DiffUtil.ItemCallback<Attraction>(){
    override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
        return oldItem == newItem
    }

}
