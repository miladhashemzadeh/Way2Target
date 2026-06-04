package com.vampyreworld.w2t.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val databasePath = File(System.getProperty("user.home"), "w2t.db")
        val driver: SqlDriver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")
        if (!databasePath.exists()) {
            W2TDatabase.Schema.create(driver)
        }
        return driver
    }
}
