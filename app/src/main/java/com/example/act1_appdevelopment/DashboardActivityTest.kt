package com.example.act1_appdevelopment

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response

class DashboardActivityTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var mockCall: Call<DashboardResponse>

    lateinit var dashboardActivity: DashboardActivity

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dashboardActivity = DashboardActivity()
        dashboardActivity.apiService = apiService
    }

    @Test
    fun testFetchDashboardDataSuccess() {
        // Arrange
        val mockResponse = mock(Response::class.java) as Response<DashboardResponse>
        val entities = listOf(Entity("Paris", "France", "Spring", "Eiffel Tower", "The capital of France, known for its art, fashion, gastronomy, and iconic landmarks."))
        val dashboardResponse = DashboardResponse(entities, 7)
        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.body()).thenReturn(dashboardResponse)
        `when`(mockCall.enqueue(any())).thenAnswer {
            val callback: retrofit2.Callback<DashboardResponse> = it.getArgument(0)
            callback.onResponse(mockCall, mockResponse)
        }
        `when`(apiService.getDashboard(anyString())).thenReturn(mockCall)

        dashboardActivity.fetchDashboardData("travel")

        verify(apiService).getDashboard("travel")
        verify(mockCall).enqueue(any())
    }

    @Test
    fun testFetchDashboardDataFailure() {
        val mockResponse = mock(Response::class.java) as Response<DashboardResponse>
        `when`(mockResponse.isSuccessful).thenReturn(false)
        `when`(mockCall.enqueue(any())).thenAnswer {
            val callback: retrofit2.Callback<DashboardResponse> = it.getArgument(0)
            callback.onResponse(mockCall, mockResponse)
        }
        `when`(apiService.getDashboard(anyString())).thenReturn(mockCall)

        dashboardActivity.fetchDashboardData("testKeypass")

        verify(apiService).getDashboard("testKeypass")
        verify(mockCall).enqueue(any())
    }
}
