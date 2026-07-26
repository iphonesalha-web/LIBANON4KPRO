package com.libanon4kpro.app.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.libanon4kpro.app.data.local.entity.ChannelEntity
import com.libanon4kpro.app.databinding.ItemChannelBinding

class ChannelAdapter(
    private val onClick: (ChannelEntity) -> Unit,
    private val onFavoriteClick: (ChannelEntity) -> Unit
) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    private val items = mutableListOf<ChannelEntity>()

    fun submitList(data: List<ChannelEntity>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ItemChannelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChannelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ChannelViewHolder(
        private val binding: ItemChannelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChannelEntity) {
            binding.tvChannelName.text = item.name
            binding.tvChannelGroup.text = item.groupName.ifBlank { "General" }
            binding.btnFavorite.text = if (item.isFavorite) "★" else "☆"

            binding.root.setOnClickListener { onClick(item) }
            binding.btnFavorite.setOnClickListener { onFavoriteClick(item) }
        }
    }
}
