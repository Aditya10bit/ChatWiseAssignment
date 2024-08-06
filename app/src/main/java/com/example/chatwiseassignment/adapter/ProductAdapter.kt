package com.example.chatwiseassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatwiseassignment.databinding.ItemProductBinding
import com.example.chatwiseassignment.databinding.ItemProductTitleBinding
import com.example.chatwiseassignment.model.Product

class ProductAdapter(
    private val items: List<Any>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_TITLE = 0
        const val VIEW_TYPE_PRODUCT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is String -> VIEW_TYPE_TITLE
            is Product -> VIEW_TYPE_PRODUCT
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TITLE -> {
                val binding = ItemProductTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TitleViewHolder(binding)
            }
            VIEW_TYPE_PRODUCT -> {
                val binding = ItemProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ProductViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                holder.bind(items[position] as String)
            }
            is ProductViewHolder -> {
                holder.bind(items[position] as Product)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class TitleViewHolder(private val binding: ItemProductTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.tvTitle.text = title
        }
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductName.text = product.title
            binding.tvProductPrice.text = "$${product.price}"
            Glide.with(binding.ivProductImage.context)
                .load(product.thumbnail)
                .into(binding.ivProductImage)

            itemView.setOnClickListener {
                onItemClick(product)
            }
        }
    }
}
