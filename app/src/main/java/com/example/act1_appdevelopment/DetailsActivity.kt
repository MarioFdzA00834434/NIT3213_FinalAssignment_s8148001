package com.example.act1_appdevelopment

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DetailsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val destination = intent.getStringExtra("destination") ?: ""
        val country = intent.getStringExtra("country") ?: ""
        val bestSeason = intent.getStringExtra("bestSeason") ?: ""
        val popularAttraction = intent.getStringExtra("popularAttraction") ?: ""
        val description = intent.getStringExtra("description") ?: ""

        findViewById<TextView>(R.id.destination).text = destination
        findViewById<TextView>(R.id.country).text = "Country: $country"
        findViewById<TextView>(R.id.season).text = "Best season: $bestSeason"
        findViewById<TextView>(R.id.attraction).text = "Popular attraction: $popularAttraction"
        findViewById<TextView>(R.id.description).text = description

        findViewById<Button>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }
    }
}
