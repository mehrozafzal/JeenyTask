package com.jeeny.task.model

import com.jeeny.task.repository.model.Coordinate
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CoordinateModelTest {

    private val latitude: Double = 53.46111
    private val longitude: Double = 9.921229

    @Mock
    lateinit var coordinate: Coordinate

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(coordinate.latitude).thenReturn(latitude)
        Mockito.`when`(coordinate.longitude).thenReturn(longitude)
    }

    @Test
    fun testLatitude() {
        Assert.assertEquals(53.46111, coordinate.latitude)
    }

    @Test
    fun testLongitude() {
        Assert.assertEquals(9.921229, coordinate.longitude)
    }

}