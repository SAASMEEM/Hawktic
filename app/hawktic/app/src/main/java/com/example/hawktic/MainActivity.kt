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
        myLocationOverlay.enableMyLocation()

        // Create a button to center the map on the user's location
        val focusButton = findViewById<Button>(R.id.focus_button)
        focusButton.setOnClickListener {
            // Get the user's last known location
            val location = myLocationOverlay.lastFix

            // If the location is not null, center the map on it
            if (location != null) {
                mapView.controller.animateTo(GeoPoint(location))
            }
        }

    }


}
