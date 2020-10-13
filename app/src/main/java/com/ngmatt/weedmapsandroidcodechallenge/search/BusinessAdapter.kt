package com.ngmatt.weedmapsandroidcodechallenge.search

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ngmatt.weedmapsandroidcodechallenge.R
import com.ngmatt.weedmapsandroidcodechallenge.data.model.Business
import com.ngmatt.weedmapsandroidcodechallenge.utils.inflate
import kotlinx.android.synthetic.main.item_business_result.view.*

class BusinessAdapter : PagedListAdapter<Business, BusinessAdapter.BusinessItemHolder>(DIFF_CALLBACK) {

    inner class BusinessItemHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_business_result)) {

        fun bind(business: Business) = with(itemView){
            Glide.with(this.context)
                .load(business.image_url)
                .centerCrop()
                .into(imageBusiness)

            txtName?.text = business.name
            txtRating?.text = "${business.rating}"
            txtTopReview?.text = business.review
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessItemHolder = BusinessItemHolder(parent)
    override fun onBindViewHolder(holder: BusinessItemHolder, position: Int) { getItem(position)?.let { holder.bind(it) } }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Business>() {
            override fun areItemsTheSame(oldItem: Business, newItem: Business): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Business, newItem: Business): Boolean {
                return oldItem == newItem
            }
        }
    }
}