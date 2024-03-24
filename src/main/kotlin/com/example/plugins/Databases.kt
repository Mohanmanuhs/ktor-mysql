package com.example.plugins

import com.example.models.Cities
import com.example.models.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

/*To enable connection pooling, define a function returning the DataSource
 interface, utilizing HikariCP’s HikariDataSource. This function,
 provideDataSource(), will be employed when Exposed connects to the database*/
private fun provideDataSource(url:String,driverClass:String): HikariDataSource {
    val hikariConfig= HikariConfig().apply {
        driverClassName=driverClass
        jdbcUrl=url
        maximumPoolSize=3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }
    return HikariDataSource(hikariConfig)
}

//Here we will define the connection to the database and the database schema.
fun Application.configureDatabases() {
    val driverClass=environment.config.property("storage.driverClassName").getString()
    val jdbcUrl=environment.config.property("storage.jdbcURL").getString()
    val db= Database.connect(provideDataSource(jdbcUrl,driverClass))
    transaction(db){
        SchemaUtils.create(Users, Cities)
    }
}


/*create a helper function called dbquery() which we will use to perform
 future operations on the database while also leveraging Kotlin coroutines.
 This way each database transaction will take place in its own coroutine
 hence performing transactions asynchronously and in a non-blocking way.*/
suspend fun <T> dbQuery(block:suspend ()->T):T{
    return newSuspendedTransaction(Dispatchers.IO) { block() }
}