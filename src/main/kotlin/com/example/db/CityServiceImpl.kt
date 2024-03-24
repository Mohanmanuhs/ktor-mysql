package com.example.db

import com.example.models.Cities
import com.example.models.City
import com.example.models.UserInfo
import com.example.models.Users
import com.example.plugins.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll


class CityServiceImpl : CityService {
    private fun resultRowToCity(resultRow: ResultRow): City {
        return City(
            cityName = resultRow[Cities.cityName],
            id = resultRow[Cities.id]
        )
    }

    override suspend fun addCity(city: City): City? = dbQuery {
        val insertStmt = Cities.insert {
            it[cityName] = city.cityName
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToCity(it) }
    }

    override suspend fun getAllCities(): List<City> = dbQuery {
        Cities.selectAll().map { resultRowToCity(it) }
    }

    override suspend fun deleteCity(id: Int): Boolean = dbQuery {
        Cities.deleteWhere { Cities.id eq id } > 0
    }

    /*With this function, we create an inner join between the Users and Cities
     table based on the foreign key we defined earlier. .
     select(Users.name,Cities.name) defines the columns that will be part of
     the result set this way we can map it to the UserInfo data class we defined earlier.*/
    override suspend fun getAllUsersInfo(): List<UserInfo> = dbQuery {
        (Users innerJoin Cities)
            .select(Users.name, Cities.cityName)
            .map {
                UserInfo(
                    name = it[Users.name],
                    city = it[Cities.cityName]
                )
            }
    }
}