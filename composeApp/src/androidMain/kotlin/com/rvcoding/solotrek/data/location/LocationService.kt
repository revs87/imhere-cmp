package com.rvcoding.solotrek.data.location

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.rvcoding.solotrek.CommonContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A foreground service responsible for tracking location updates using KMPLocation.
 *
 * This service demonstrates how to:
 * 1. Initialize a location provider.
 * 2. Start itself as a foreground service with a notification.
 * 3. Collect location updates from a Kotlin Flow.
 * 4. Handle location data and potential errors.
 * 5. Manage its CoroutineScope for proper resource cleanup.
 */
class LocationService(private val context: CommonContext): Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private lateinit var locationProvider: KMPLocation

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationProvider = LocationProviderFactory().create(context)
    }

    /**
     * Called every time a client starts the service (e.g., via startService()).
     * This is where the main work of the service is initiated.
     *
     * @param intent The Intent supplied to startService(Intent), as given by the client.
     * @param flags Additional data about this start request.
     * @param startId A unique integer representing this specific request to start.
     * @return The return value indicates what semantics the system should use for the service
     * if its process is killed while it is started. START_STICKY means it will be
     * recreated and onStartCommand will be called with a null intent.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start the service in the foreground to prevent it from being killed by the system
        // The notification ensures the user is aware of the background activity.
        startForeground(NOTIFICATION_ID, createNotification())

        locationProvider.getLocation()
            .onEach { location ->
                // Process the received location data.
                // Examples:
                // - Save it to a local database (e.g., Room)
                // - Send it to a remote server via API call
                // - Broadcast it to UI components (e.g., Activities) using LocalBroadcastManager or LiveData
                println("New Location: $location")
            }
            .catch { e ->
                // Handle any errors that occur during location updates,
                // for example, if location permissions are revoked or GPS is off.
                e.printStackTrace()
                // Stop the service if a critical error occurs
                stopSelf()
            }
            // Launch the flow collection in the serviceScope. This ensures the collection
            // is automatically cancelled when serviceScope is cancelled (in onDestroy).
            .launchIn(serviceScope)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    /**
     * Creates a Notification for the foreground service.
     * For Android Oreo (API 26) and above, a Notification Channel is required.
     *
     * @return The Notification object to be displayed.
     */
    private fun createNotification(): Notification {
        val channelId = "location_service_channel"
        // For Android 8.0 (Oreo) and above, a notification channel must be created.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Location Service", // User-visible name of the channel
                NotificationManager.IMPORTANCE_DEFAULT // Importance level
            ).apply {
                description = "Notifies about background location tracking." // User-visible description
            }
            // Register the channel with the system
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Location Tracking") // Title of the notification
            .setContentText("Your location is being tracked in the background.") // Main text content
            .setSmallIcon(android.R.drawable.ic_menu_mylocation) // Small icon for the notification bar
            // Consider adding a pending intent here to open an activity when the user taps the notification
            .build()
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}
