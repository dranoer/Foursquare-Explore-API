package com.nightmareinc.foursquare.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nightmareinc.foursquare.databinding.ListItemVenueBinding
import com.nightmareinc.foursquare.model.models.Item
import com.nightmareinc.foursquare.model.models.Venue


class VenueAdapter(val clickListener: VenueListener) : ListAdapter<Item, VenueAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemVenueBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, clickListener: VenueListener) {
            binding.venue = item.venue
            binding.userName.text = item.venue.name
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemVenueBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.venue.id == newItem.venue.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}

class VenueListener(val clickListener: (venueId: String) -> Unit) {
    fun onCLick(venue: Venue) = clickListener(venue.id!!)
}