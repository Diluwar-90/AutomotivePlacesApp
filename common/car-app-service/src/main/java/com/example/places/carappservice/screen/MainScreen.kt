package com.example.places.carappservice.screen

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.*
import androidx.core.graphics.drawable.IconCompat
import com.example.places.carappservice.R
import com.example.places.data.PlacesRepository

class MainScreen(carContext: CarContext) : Screen(carContext) {

    override fun onGetTemplate(): Template {
        val placesRepository = PlacesRepository()
        val itemListBuilder = ItemList.Builder()
            .setNoItemsMessage("No places to show")

        placesRepository.getPlaces()
            .forEach {
                itemListBuilder.addItem(
                    Row.Builder()
                        .setTitle(it.name)
                        // Each item in the list *must* have a DistanceSpan applied to either the title
                        // or one of the its lines of text (to help drivers make decisions)
                        .addText(SpannableString(" ").apply {
                            setSpan(
                                DistanceSpan.create(
                                    Distance.create(Math.random() * 100, Distance.UNIT_KILOMETERS)
                                ), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                            )
                        })
                        .setOnClickListener {  screenManager.push(DetailScreen(carContext, it.id))}
                        // Setting Metadata is optional, but is required to automatically show the
                        // item's location on the provided map
                        .setMetadata(
                            Metadata.Builder()
                                .setPlace(Place.Builder(CarLocation.create(it.latitude, it.longitude))
                                    // Using the default PlaceMarker indicates that the host should
                                    // decide how to style the pins it shows on the map/in the list
                                    .setMarker(PlaceMarker.Builder().build())
                                    .build())
                                .build()
                        ).build()
                )
            }

        return PlaceListMapTemplate.Builder()
            .setTitle("Places")
            .setItemList(itemListBuilder.build())
            .build()
    }
}