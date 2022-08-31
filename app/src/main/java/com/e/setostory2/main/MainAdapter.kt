package com.e.setostory2.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.setostory2.data.StoryItemList
import com.e.setostory2.databinding.StoryListRvcardBinding

class MainAdapter(fragment: FirstFragment) : PagingDataAdapter<StoryItemList,StoryViewHolder>(Comparator) {

    private val frag = fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder{
        val binding= StoryListRvcardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)

        holder.text.text = data?.name
        Glide.with(holder.itemView.context).load(data?.photoUrl).into(holder.img)
        holder.img.transitionName = "trans_image$position"
        holder.text.transitionName = "trans_text$position"
        holder.itemView.setOnClickListener{
            val extra = FragmentNavigatorExtras(
                kotlin.Pair(holder.img,"transendF"),
                kotlin.Pair(holder.text,"transendN")

            )

            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                data?.name,
                data?.description,
                data?.photoUrl
            )
            findNavController(frag).navigate(action,extra)
        }
    }
    private object Comparator : DiffUtil.ItemCallback<StoryItemList>() {
        override fun areItemsTheSame(oldItem: StoryItemList, newItem: StoryItemList): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: StoryItemList, newItem: StoryItemList): Boolean =
            oldItem.description == newItem.description
    }

}

class StoryViewHolder(private val binding: StoryListRvcardBinding) : RecyclerView.ViewHolder(binding.root) {
        val text : TextView = binding.usercardName
        val img : ImageView = binding.usercardImage
    }



