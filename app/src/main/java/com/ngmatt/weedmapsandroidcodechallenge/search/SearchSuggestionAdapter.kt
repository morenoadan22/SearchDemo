package com.ngmatt.weedmapsandroidcodechallenge.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngmatt.weedmapsandroidcodechallenge.R
import com.ngmatt.weedmapsandroidcodechallenge.utils.inflate
import kotlinx.android.synthetic.main.item_search_suggestion.view.*

class SearchSuggestionAdapter(private val listener: OnSuggestionClickedListener): RecyclerView.Adapter<SearchSuggestionAdapter.ViewHolder>() {
    var suggestions: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_search_suggestion)) {
        private var suggestion : String = ""

        init {
            itemView.setOnClickListener {
                listener.onSuggestionClicked(suggestion)
            }
        }

        fun bind(s: String) = with(itemView) {
            suggestion = s
            txtSuggestion.text = s
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder = ViewHolder(parent)

    override fun getItemCount(): Int = suggestions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(suggestions[position])
    }

    interface OnSuggestionClickedListener {
        fun onSuggestionClicked(suggestion: String)
    }
}