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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ngmatt.weedmapsandroidcodechallenge.R
import com.ngmatt.weedmapsandroidcodechallenge.data.model.Coordinate
import com.ngmatt.weedmapsandroidcodechallenge.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchBusinessFragment: Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var binding: FragmentSearchBinding

    private var resultAdapter: BusinessAdapter? = null

    private lateinit var locationClient : FusedLocationProviderClient

    companion object {
        const val REQUEST_CODE_LOCATION = 4003
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultAdapter = BusinessAdapter()
        binding.recyclerResults.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = resultAdapter
        }

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_CODE_LOCATION)
            return
        }
        locationClient.lastLocation.addOnSuccessListener { searchViewModel.setUserLocation(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.viewModel = searchViewModel
        binding.containerSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if(it.isNotEmpty()) {
                        searchViewModel.searchBusiness(it)
                        return true
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        searchViewModel.searchPagedListLiveData.observe(viewLifecycleOwner, Observer { resultAdapter?.submitList(it) })
        searchViewModel.searchTerm.observe(viewLifecycleOwner, Observer {
            txtSearchLabel?.text =
                if(it.isEmpty())
                    context?.getText(R.string.showing_results_empty)
                else context?.getString(R.string.latest_results_for, it)
        })
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    locationClient.lastLocation.addOnSuccessListener {
                        searchViewModel.setUserLocation(it)
                    }
                } else {
                    Toast.makeText(context, "Access to location is necessary for search to function properly", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}