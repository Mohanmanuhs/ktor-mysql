package com.example.models

import kotlinx.serialization.Serializable

/*a data class that will show information about a user with their city name.
 We will later use an inner join on users and cities tables and map the
 result to the data class we create.*/
@Serializable
data class UserInfo(
    val name:String,
    val city:String
)