package com.ngmatt.weedmapsandroidcodechallenge

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.ngmatt.weedmapsandroidcodechallenge.location.LocationViewModel
import com.ngmatt.weedmapsandroidcodechallenge.search.SearchBusinessFragment
import com.ngmatt.weedmapsandroidcodechallenge.utils.toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Matt Ng on 9/14/20
 */
class HelloWorldActivity: AppCompatActivity() {
    private val searchFragment: SearchBusinessFragment by inject()
    private val locationViewModel: LocationViewModel by viewModel()

    companion object {
        const val REQUEST_CODE_LOCATION = 4003
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_CODE_LOCATION)
            return
        } else {
            GlobalScope.launch { locationViewModel.permissionGranted() }
        }

        locationViewModel.coordinateLiveData.observe(this,  Observer { toast("New Location was found: (${it.latitudeString},${it.longitudeString})") })
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction()
            .replace(R.id.root, searchFragment)
            .commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    GlobalScope.launch { locationViewModel.permissionGranted() }
                } else {
                    toast("Access to location was denied")
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}