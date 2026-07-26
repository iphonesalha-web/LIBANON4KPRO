package com.libanon4kpro.app.ui.livetv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.libanon4kpro.app.data.local.entity.ChannelEntity
import com.libanon4kpro.app.databinding.ItemChannelTvBinding

class LiveTvChannelAdapter(
    private val onClick: (ChannelEntity) -> Unit,
    private val onFavoriteClick: (ChannelEntity) -> Unit
) : ListAdapter<ChannelEntity, LiveTvChannelAdapter.ChannelViewHolder>(ChannelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ItemChannelTvBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChannelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1)
    }

    inner class ChannelViewHolder(
        private val binding: ItemChannelTvBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChannelEntity, index: Int) {
            binding.tvChannelIndex.text = index.toString()
            binding.tvChannelName.text = item.name
            binding.tvChannelGroup.text = item.groupName.ifBlank { "General" }
            binding.btnFavorite.text = if (item.isFavorite) "★" else "☆"

            binding.root.setOnClickListener { onClick(item) }
            binding.btnFavorite.setOnClickListener { onFavoriteClick(item) }
        }
    }

    private class ChannelDiffCallback : DiffUtil.ItemCallback<ChannelEntity>() {
        override fun areItemsTheSame(oldItem: ChannelEntity, newItem: ChannelEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ChannelEntity, newItem: ChannelEntity) =
            oldItem == newItem
    }
}
