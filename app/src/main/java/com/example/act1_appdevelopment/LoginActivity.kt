package com.example.act1_appdevelopment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText: EditText = findViewById(R.id.username)
        val passwordEditText: EditText = findViewById(R.id.password)
        val loginButton: Button = findViewById(R.id.loginButton)
        val errorMessage: TextView = findViewById(R.id.errorMessage)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            performLogin(username, password, errorMessage)
        }
    }

    fun performLogin(username: String, password: String, errorMessage: TextView) {
        val credentials = UserCredentials(username, password)
        val call = apiService.login(credentials)

        val request = call.request()
        Log.d(TAG, "Request URL: ${request.url}")
        Log.d(TAG, "Request Method: ${request.method}")

        val requestBody = request.body
        if (requestBody != null) {
            val buffer = okio.Buffer()
            requestBody.writeTo(buffer)
            Log.d(TAG, "Request Body: ${buffer.readUtf8()}")
        }

        call.enqueue(object : Callback<LoginResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d(TAG, "Response Code: ${response.code()}")
                Log.d(TAG, "Response Body: ${response.body()}")
                Log.d(TAG, "Response Message: ${response.message()}")

                if (response.isSuccessful) {
                    val keypass = response.body()?.keypass ?: return
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    intent.putExtra("KEYPASS", keypass)
                    startActivity(intent)
                    finish()
                } else {
                    errorMessage.text = "Login failed: ${response.message()}"
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "Login Error", t)
                errorMessage.text = "Error: ${t.message}"
            }
        })
    }
}
