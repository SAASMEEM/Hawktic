package com.example.hawktic

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MainActivity : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().userAgentValue = "Hawktic/1.0"

        setContentView(R.layout.activity_main)

        val mapView = findViewById<MapView>(R.id.map)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
        mapView.setMultiTouchControls(true)
        mapView.controller.zoomTo(15.0)

        val marker = Marker(mapView)
        marker.position = GeoPoint(37.7749, -122.4194)
        mapView.overlays.add(marker)

        val context = this
// Add a MyLocationNewOverlay to draw the user's location on the map
        val myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
        mapView.overlays.add(myLocationOverlay)
        myLocationOverlay.enableMyLocation()

// Create a button to center the map on the user's location
        val focusButton = findViewById<Button>(R.id.focus_button)
        focusButton.setOnClickListener {
            // Get the user's last known location
            val location = myLocationOverlay.lastFix
            if (location != null) {
                // Animate the map to the user's location
                mapView.controller.animateTo(GeoPoint(location))
            } else {
                // Wait for the user's location to become available
                myLocationOverlay.runOnFirstFix(Runnable {
                    // Animate the map to the user's location
                    val location = myLocationOverlay.lastFix
                    if (location != null && mapView.controller != null) {
                        mapView.controller.animateTo(GeoPoint(location))
                    }
                })
            }
        }


    }


}
