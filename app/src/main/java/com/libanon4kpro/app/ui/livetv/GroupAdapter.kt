package com.libanon4kpro.app.ui.livetv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.libanon4kpro.app.databinding.ItemGroupBinding

data class GroupItem(val name: String, val count: Int, val isSelected: Boolean = false)

class GroupAdapter(
    private val onGroupClick: (String) -> Unit
) : ListAdapter<GroupItem, GroupAdapter.GroupViewHolder>(GroupDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GroupViewHolder(
        private val binding: ItemGroupBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GroupItem) {
            binding.tvGroupName.text = item.name
            binding.tvGroupCount.text = item.count.toString()
            binding.root.isActivated = item.isSelected
            binding.tvGroupName.textSize = if (item.isSelected) 15f else 14f

            binding.root.setOnClickListener {
                onGroupClick(item.name)
            }
        }
    }

    private class GroupDiffCallback : DiffUtil.ItemCallback<GroupItem>() {
        override fun areItemsTheSame(oldItem: GroupItem, newItem: GroupItem) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: GroupItem, newItem: GroupItem) =
            oldItem == newItem
    }
}
