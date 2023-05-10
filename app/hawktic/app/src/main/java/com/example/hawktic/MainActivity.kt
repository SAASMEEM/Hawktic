package com.example.hawktic

import android.os.Bundle
import androidx.activity.ComponentActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MainActivity : ComponentActivity() {
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
    }


}
