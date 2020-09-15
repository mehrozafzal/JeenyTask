package com.jeeny.task.model

import com.jeeny.task.repository.model.Coordinate
import com.jeeny.task.repository.model.PoiListItem
import com.jeeny.task.repository.model.VehicleResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class VehicleModelTest {

    private val id: Int = 930670
    private val latitude: Double = 53.46111
    private val longitude: Double = 9.921229
    private val fleetType: String = "TAXI"
    private val heading: Double = 110.61653
    private val poiList: ArrayList<PoiListItem> = ArrayList()

    @Mock
    lateinit var coordinate: Coordinate

    @Mock
    lateinit var poiListItem: PoiListItem

    @Mock
    lateinit var vehicleResponse: VehicleResponse

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        coordinate.latitude = latitude
        coordinate.longitude = longitude

        poiListItem.coordinate = coordinate
        poiListItem.fleetType = fleetType
        poiListItem.heading = heading
        poiListItem.id = id

        poiList.add(poiListItem)
        Mockito.`when`(vehicleResponse.poiList).thenReturn(poiList)
    }

    @Test
    fun testPoiList() {
        val coordinate = Coordinate()
        coordinate.latitude = 53.46111
        coordinate.longitude = 9.921229

        val poiListItem = PoiListItem()
        poiListItem.coordinate = coordinate
        poiListItem.fleetType = "TAXI"
        poiListItem.heading = 110.61653
        poiListItem.id = 930670

        poiList.add(poiListItem)
        Assert.assertEquals(poiList, vehicleResponse.poiList)
    }


}