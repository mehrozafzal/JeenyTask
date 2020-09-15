package com.jeeny.task.model

import com.jeeny.task.repository.model.Coordinate
import com.jeeny.task.repository.model.PoiListItem
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PoiModelTest {

    private val id: Int = 930670
    private val latitude: Double = 53.46111
    private val longitude: Double = 9.921229
    private val fleetType: String = "TAXI"
    private val heading: Double = 110.61653

    @Mock
    lateinit var coordinate: Coordinate

    @Mock
    lateinit var poiListItem: PoiListItem

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(poiListItem.id).thenReturn(id)
        Mockito.`when`(poiListItem.fleetType).thenReturn(fleetType)
        Mockito.`when`(poiListItem.heading).thenReturn(heading)
        coordinate.latitude = latitude
        coordinate.longitude = longitude
        Mockito.`when`(poiListItem.coordinate).thenReturn(coordinate)

    }

    @Test
    fun testId() {
        Assert.assertEquals(930670, poiListItem.id)
    }

    @Test
    fun testFleetType() {
        Assert.assertEquals("TAXI", poiListItem.fleetType)
    }

    @Test
    fun testHeading() {
        Assert.assertEquals(110.61653, poiListItem.heading)
    }

    @Test
    fun testCoordinate() {
        Assert.assertEquals(coordinate, poiListItem.coordinate)
    }

}