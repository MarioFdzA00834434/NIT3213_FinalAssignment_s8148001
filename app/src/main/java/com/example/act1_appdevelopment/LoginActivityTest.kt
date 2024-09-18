package com.example.act1_appdevelopment

import android.widget.TextView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response

class LoginActivityTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var mockCall: Call<LoginResponse>

    lateinit var loginActivity: LoginActivity

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loginActivity = LoginActivity()
        loginActivity.apiService = apiService
    }

    @Test
    fun testLoginSuccess() {
        val mockResponse = mock(Response::class.java) as Response<LoginResponse>
        val loginResponse = LoginResponse(keypass = "travel")
        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.body()).thenReturn(loginResponse)
        `when`(mockCall.enqueue(any())).thenAnswer {
            val callback: retrofit2.Callback<LoginResponse> = it.getArgument(0)
            callback.onResponse(mockCall, mockResponse)
        }
        `when`(apiService.login(any())).thenReturn(mockCall)

        loginActivity.performLogin("Rob", "s8034816", TextView(loginActivity))

        verify(apiService).login(any())
        verify(mockCall).enqueue(any())
    }

    @Test
    fun testLoginFailure() {
        val mockResponse = mock(Response::class.java) as Response<LoginResponse>
        `when`(mockResponse.isSuccessful).thenReturn(false)
        `when`(mockCall.enqueue(any())).thenAnswer {
            val callback: retrofit2.Callback<LoginResponse> = it.getArgument(0)
            callback.onResponse(mockCall, mockResponse)
        }
        `when`(apiService.login(any())).thenReturn(mockCall)

        loginActivity.performLogin("test", "test", TextView(loginActivity))

        verify(apiService).login(any())
        verify(mockCall).enqueue(any())
    }
}
