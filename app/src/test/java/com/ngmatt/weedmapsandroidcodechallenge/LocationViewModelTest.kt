package com.ngmatt.weedmapsandroidcodechallenge

import android.content.Context
import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import com.ngmatt.weedmapsandroidcodechallenge.location.Coordinate
import com.ngmatt.weedmapsandroidcodechallenge.location.LocationViewModel
import com.ngmatt.weedmapsandroidcodechallenge.location.infrastructure.LocationRepository
import com.ngmatt.weedmapsandroidcodechallenge.location.infrastructure.LocationSource
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LocationViewModelTest {

    @get:Rule
    public val rule: TestRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("main thread")

    private lateinit var locationViewModel: LocationViewModel
    private lateinit var mockLocationSource: LocationSource
    private lateinit var mockLocationProviderClient: FusedLocationProviderClient
    private lateinit var mockLocation : Location
    private lateinit var mockObserver: Observer<Coordinate>

    private val testCoordinate = Coordinate(Date(15000), 33.5, -122.5)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        mockLocationSource = mock<LocationRepository> {
            onBlocking { getLastLocation() }
                .thenAnswer { Coordinate(Date(), 33.9, 122.3) }
        }

        mockObserver = mock {}

        mockLocation = mock {
            on { latitude }.doAnswer { testCoordinate.latitude }
            on { longitude }.doAnswer { testCoordinate.longitude }
        }

        mockLocationProviderClient = mock {
            on { lastLocation }.doAnswer {
                val callback = it.arguments[0] as OnSuccessListener<Location>
                callback.onSuccess(mockLocation)
                null
            }
        }

        val mockContext = mock<Context>{}

        locationViewModel = LocationViewModel(mockContext, mockLocationSource)
    }


    @Test
    fun `test if I was able to receive location`() = runBlockingTest {
        doReturn(testCoordinate)
            .`when`(mockLocationSource)
            .getLastLocation()
        locationViewModel.permissionGranted()
        locationViewModel.coordinateLiveData.observeForever(mockObserver)
        verify(mockObserver).onChanged(testCoordinate)
        locationViewModel.coordinateLiveData.removeObserver(mockObserver)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


}