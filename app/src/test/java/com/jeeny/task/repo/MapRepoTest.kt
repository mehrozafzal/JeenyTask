package com.jeeny.task.repo


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.jeeny.task.CurrentThreadExecutor
import com.jeeny.task.repository.api.ApiServices
import com.jeeny.task.repository.api.network.LiveDataCallAdapterFactoryForRetrofit
import com.jeeny.task.repository.model.Coordinate
import com.jeeny.task.repository.model.PoiListItem
import com.jeeny.task.repository.model.VehicleResponse
import com.jeeny.task.repository.repo.MapRepository
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.IsEqual.equalTo
import org.junit.*
import org.junit.Assert.assertThat
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(JUnit4::class)
class MapRepoTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockWebServer = MockWebServer()

    private lateinit var apiServices: ApiServices

    private lateinit var mapRepository: MapRepository


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val currentThreadExecutor = CurrentThreadExecutor()
        val dispatcher = Dispatcher(currentThreadExecutor)
        val okHttpClient = OkHttpClient.Builder().dispatcher(dispatcher).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactoryForRetrofit())
            .callbackExecutor(currentThreadExecutor)
            .build()
        apiServices = retrofit.create(ApiServices::class.java)
        mapRepository = MapRepository(apiServices)
    }


    @Test
    fun testRestaurantData() {
        val responseBody =
            """
            {
            "poiList": [
                            {
                                 "id": 930670,
                                 "coordinate": {
                                    "latitude": 53.49547,
                                    "longitude": 9.928472
                                 },
                             "fleetType": "TAXI",
                             "heading": 110.61653
            }]}
             """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setHeader("Content-Type", "application/json; charset=UTF-8")
                .setResponseCode(200)
                .setBody(responseBody)
        )

        var vehicleResponse: VehicleResponse? = null
        var error: String? = null
        mapRepository.getVehicleResponseFromServer().observeForever {
            when {
                it.status.isSuccessful() -> {
                    vehicleResponse = it.data
                }
                it.status.isError() -> {
                    error = it.errorMessage
                }
            }
        }


        val request = mockWebServer.takeRequest()
        assertThat(request.requestLine, equalTo("GET /raw/SwFd1znM HTTP/1.1"));
        Assert.assertNull(error)
        Assert.assertNotNull(vehicleResponse)

        val poiList:ArrayList<PoiListItem> = ArrayList()
        val coordinate = Coordinate()
        coordinate.latitude = 53.49547
        coordinate.longitude = 9.928472

        val poiListItem = PoiListItem()
        poiListItem.coordinate = coordinate
        poiListItem.fleetType = "TAXI"
        poiListItem.heading = 110.61653
        poiListItem.id = 930670
        poiList.add(poiListItem)
        val vehicle = VehicleResponse()
        vehicle.poiList = poiList
        Assert.assertEquals(vehicleResponse, vehicle)
    }


    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

}