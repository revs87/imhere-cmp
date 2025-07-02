package com.rvcoding.solotrek.data.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.rvcoding.solotrek.data.location.PowerLevel.LEVEL_1
import com.rvcoding.solotrek.data.location.PowerLevel.LEVEL_2
import com.rvcoding.solotrek.data.location.PowerLevel.LEVEL_3
import com.rvcoding.solotrek.data.location.PowerLevel.LEVEL_4
import com.rvcoding.solotrek.data.location.PowerLevel.LEVEL_5
import com.rvcoding.solotrek.domain.data.location.LocationDomain
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidLocationProvider(
    private val context: Context
) : KMPLocation {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    private fun priority(powerLevel: PowerLevel): Int = when (powerLevel) {
        LEVEL_1 -> Priority.PRIORITY_PASSIVE
        LEVEL_2 -> Priority.PRIORITY_BALANCED_POWER_ACCURACY
        LEVEL_3, LEVEL_4, LEVEL_5 -> Priority.PRIORITY_HIGH_ACCURACY
    }

    private fun intervalMillis(powerLevel: PowerLevel): Long = when (powerLevel) {
        LEVEL_1 -> 30 * 60 * 1000L // 30 minutes
        LEVEL_2 -> 10 * 60 * 1000L // 10 minutes
        LEVEL_3 -> 60 * 1000L      //  1 minute
        LEVEL_4 -> 10 * 1000L      // 10 seconds
        LEVEL_5 ->  5 * 1000L      //  5 seconds
    }

    private fun LocationRequest.Builder.setMinUpdateIntervalMillis(powerLevel: PowerLevel): LocationRequest.Builder {
        setMinUpdateIntervalMillis(
            when (powerLevel) {
                LEVEL_1 ->  5 * 60 * 1000L //  5 minutes
                LEVEL_2 ->  3 * 60 * 1000L //  3 minutes
                LEVEL_3 -> 30 * 1000L      // 30 seconds
                LEVEL_4 ->  5 * 1000L      //  5 seconds
                LEVEL_5 ->  1 * 1000L      //  1 seconds
            }
        )
        return this
    }

    private fun LocationRequest.Builder.setMinUpdateDistanceMeters(powerLevel: PowerLevel): LocationRequest.Builder {
        setMinUpdateDistanceMeters(
            when (powerLevel) {
                LEVEL_1 -> 500.0F // 500 meters
                LEVEL_2 -> 100.0F // 100 meters
                LEVEL_3 ->  20.0F //  20 meters
                LEVEL_4 ->   5.0F //   5 meters
                LEVEL_5 ->   0.0F //   0 meters
            }
        )
        return this
    }
    private fun LocationRequest.Builder.setMaxUpdateDelayMillis(powerLevel: PowerLevel): LocationRequest.Builder {
        setMaxUpdateDelayMillis(
            when (powerLevel) {
                LEVEL_1 -> 60 * 60 * 1000L //  1 hour
                LEVEL_2 -> 30 * 60 * 1000L // 30 minutes
                LEVEL_3 ->  5 * 60 * 1000L //  5 minutes
                LEVEL_4 ->  1 * 60 * 1000L //  1 minute
                LEVEL_5 ->  0L             //  0 seconds
            }
        )
        return this
    }

    @SuppressLint("MissingPermission")
    override fun getLocation(powerLevel: PowerLevel): Flow<LocationDomain> = callbackFlow {
        val locationRequest = LocationRequest.Builder(
            priority(powerLevel),
            intervalMillis(powerLevel)
        )
            .setMinUpdateIntervalMillis(powerLevel)
            .setMinUpdateDistanceMeters(powerLevel)
            .setMaxUpdateDelayMillis(powerLevel)
            .setDurationMillis(Long.MAX_VALUE)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let {
                    trySend(
                        LocationDomain(
                            latitude = it.latitude,
                            longitude = it.longitude,
                            altitude = it.altitude,
                            timestamp = it.time
                        )
                    )
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            context.mainLooper
        )

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
}