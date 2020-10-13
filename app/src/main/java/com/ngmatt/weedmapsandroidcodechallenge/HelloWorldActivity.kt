package com.ngmatt.weedmapsandroidcodechallenge

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ngmatt.weedmapsandroidcodechallenge.R
import com.ngmatt.weedmapsandroidcodechallenge.search.SearchBusinessFragment
import org.koin.android.ext.android.inject

/**
 * Created by Matt Ng on 9/14/20
 */
class HelloWorldActivity: AppCompatActivity() {
    private val searchFragment: SearchBusinessFragment by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction()
            .replace(R.id.root, searchFragment)
            .commit()
    }
}