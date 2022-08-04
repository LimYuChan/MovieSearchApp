package com.devsurfer.moviesearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devsurfer.domain.model.searchKeyword.SearchKeyword
import com.devsurfer.moviesearchapp.R
import com.devsurfer.moviesearchapp.databinding.ItemKeywordBinding

class KeywordAdapter(
    val onItemClick: (String) -> Unit
): ListAdapter<SearchKeyword, KeywordAdapter.KeywordItemViewHolder>(diffUtil){

    inner class KeywordItemViewHolder(val binding: ItemKeywordBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: SearchKeyword){
            binding.keyword = data.searchKeyword
            binding.itemKeyword.setOnClickListener {
                onItemClick(data.searchKeyword)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordItemViewHolder =
        KeywordItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_keyword, parent, false))

    override fun onBindViewHolder(holder: KeywordItemViewHolder, position: Int){
        currentList[position]?.let { holder.bind(it) }
    }

    companion object{
        val diffUtil =object: DiffUtil.ItemCallback<SearchKeyword>(){
            override fun areItemsTheSame(oldItem: SearchKeyword, newItem: SearchKeyword): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: SearchKeyword, newItem: SearchKeyword): Boolean = oldItem == newItem
        }
    }
}