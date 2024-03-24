package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
@Serializable
data class User(
    val name:String,
    val cityId:Int,
    val id:Int=0
)

/*The cityId column references another column in the cities table, id column,
 establishing a foreign key relationship.We will define the cities table later.
 Using ReferenceOption.CASCADE ensures that when a city is deleted,
 all users with a matching cityId will be deleted.*/
object Users: Table(){
    val id=integer("id").autoIncrement()
    val name=varchar("name",255)
    val cityId=integer("city_id").references(Cities.id,ReferenceOption.CASCADE)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}