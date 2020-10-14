package com.ngmatt.weedmapsandroidcodechallenge.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ngmatt.weedmapsandroidcodechallenge.R
import com.ngmatt.weedmapsandroidcodechallenge.databinding.FragmentSearchBinding
import com.ngmatt.weedmapsandroidcodechallenge.utils.hideKeyboard
import com.ngmatt.weedmapsandroidcodechallenge.utils.toast
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchBusinessFragment: Fragment(), SearchSuggestionAdapter.OnSuggestionClickedListener {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var binding: FragmentSearchBinding

    private lateinit var resultAdapter: BusinessAdapter
    private lateinit var suggestionsAdapter: SearchSuggestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        suggestionsAdapter = SearchSuggestionAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultAdapter = BusinessAdapter()
        binding.lifecycleOwner = this
        binding.recyclerResults.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = resultAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(dy > 100) {
                        context?.hideKeyboard(recyclerView)
                        listSuggestions?.visibility = View.GONE
                    }
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.viewModel = searchViewModel
        binding.listSuggestions.layoutManager = LinearLayoutManager(context)
        binding.listSuggestions.adapter = suggestionsAdapter
        binding.containerSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                submitSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if(it.isNotEmpty()) {
                        listSuggestions.visibility = View.VISIBLE
                        searchViewModel.searchTerm.postValue(newText)
                        return true
                    }
                }
                return false
            }
        })
        searchViewModel.searchPagedListLiveData.observe(viewLifecycleOwner, Observer { resultAdapter.submitList(it) })
        searchViewModel.searchTerm.observe(viewLifecycleOwner, Observer {
            txtSearchLabel?.text =
                if(it.isEmpty())
                    context?.getText(R.string.showing_results_empty)
                else context?.getString(R.string.latest_results_for, it)
        })
        searchViewModel.suggestionsLiveData.observe(viewLifecycleOwner, Observer { suggestionsAdapter.suggestions = it })
        searchViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { throwable -> context?.toast(throwable.localizedMessage) })
        return binding.root
    }

    override fun onSuggestionClicked(suggestion: String) { binding.containerSearch.setQuery(suggestion, true) }

    private fun submitSearch(query: String?) = query?.let {
        listSuggestions.visibility = View.GONE
        context?.hideKeyboard(binding.containerSearch)
        if(query.isNotEmpty())
            searchViewModel.searchBusiness(query)
    }
}