package com.example.act1_appdevelopment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class DashboardActivity : AppCompatActivity() {

    lateinit var apiService: ApiService
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EntityAdapter
    private lateinit var keypass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = EntityAdapter(listOf()) { selectedEntity ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("destination", selectedEntity.destination)
                putExtra("country", selectedEntity.country)
                putExtra("bestSeason", selectedEntity.bestSeason)
                putExtra("popularAttraction", selectedEntity.popularAttraction)
                putExtra("description", selectedEntity.description)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        keypass = intent.getStringExtra("KEYPASS") ?: ""

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://vu-nit3213-api.onrender.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        apiService = retrofit.create(ApiService::class.java)

        fetchDashboardData(keypass)
    }

    fun fetchDashboardData(keypass: String) {
        val call = apiService.getDashboard(keypass)

        call.enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(call: Call<DashboardResponse>, response: Response<DashboardResponse>) {
                if (response.isSuccessful) {
                    val dashboardData = response.body()
                    if (dashboardData != null) {
                        adapter.updateData(dashboardData.entities)
                    }
                } else {
                    Log.d("DashboardActivity", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                Log.e("DashboardActivity", "Error fetching dashboard data", t)
            }
        })

        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
