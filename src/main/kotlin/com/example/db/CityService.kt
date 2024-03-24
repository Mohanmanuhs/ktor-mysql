package com.example.db

import com.example.models.City
import com.example.models.UserInfo

interface CityService {
    suspend fun addCity(city: City): City?
    suspend fun getAllCities():List<City>
    suspend fun deleteCity(id:Int):Boolean
    suspend fun getAllUsersInfo():List<UserInfo>
}