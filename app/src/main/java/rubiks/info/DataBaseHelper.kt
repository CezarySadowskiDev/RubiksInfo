package rubiks.info

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

// Opis tabeli
object TableInfo : BaseColumns {
    const val TABLE_NAME = "Times"
    const val TABLE_COLUMN_TIME = "time"
}

// Komendy SQL
object BasicCommands {
    const val SQL_CREATE_TABLE =
        "CREATE TABLE ${TableInfo.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${TableInfo.TABLE_COLUMN_TIME} INTEGER NOT NULL)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"
}

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, TableInfo.TABLE_NAME, null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(BasicCommands.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(BasicCommands.SQL_DELETE_TABLE)
        onCreate(p0)
    }

    fun getData(dataType: String): String {
        val db = this.readableDatabase

        when (dataType) {
            "solvesCount" -> {
                return executeQuery(db, "SELECT count(*) FROM ${TableInfo.TABLE_NAME}")
            }
            "meanTime" -> {
                return formatTime(
                    executeQuery(
                        db,
                        "SELECT avg(${TableInfo.TABLE_COLUMN_TIME}) " +
                                "FROM ${TableInfo.TABLE_NAME}"
                    )
                )
            }
            "meanTimeLastTenSolves" -> {
                return formatTime(
                    executeQuery(
                        db,
                        "SELECT avg(${TableInfo.TABLE_COLUMN_TIME}) " +
                                "FROM (SELECT ${TableInfo.TABLE_COLUMN_TIME} " +
                                "FROM ${TableInfo.TABLE_NAME} " +
                                "ORDER BY _id DESC LIMIT 10)"
                    )
                )
            }
            "bestTime" -> {
                return formatTime(
                    executeQuery(
                        db,
                        "SELECT ${TableInfo.TABLE_COLUMN_TIME} " +
                                "FROM ${TableInfo.TABLE_NAME} " +
                                "ORDER BY ${TableInfo.TABLE_COLUMN_TIME} ASC LIMIT 1"
                    )
                )
            }
            "worstTime" -> {
                return formatTime(
                    executeQuery(
                        db,
                        "SELECT ${TableInfo.TABLE_COLUMN_TIME} " +
                                "FROM ${TableInfo.TABLE_NAME} " +
                                "ORDER BY ${TableInfo.TABLE_COLUMN_TIME} DESC LIMIT 1"
                    )
                )
            }
            "timeSpendOnSolving" -> {
                return formatTime(
                    executeQuery(
                        db,
                        "SELECT sum(${TableInfo.TABLE_COLUMN_TIME}) FROM ${TableInfo.TABLE_NAME}"
                    )
                )
            }
        }

        return "0"
    }

    private fun executeQuery(dataBase: SQLiteDatabase, query: String): String {
        val result = dataBase.rawQuery(query, null)

        if (result.moveToFirst()) {
            return result.getString(0)
        }

        result.close()

        return "0"
    }

    private fun formatTime(value: String): String {

        var properValue = value.toFloat().toInt()

        val milliseconds = properValue % 1000
        properValue /= 1000

        val seconds = properValue % 60
        properValue /= 60

        return String.format("%02d", properValue) + ":" +
                String.format("%02d", seconds) + ":" +
                String.format("%03d", milliseconds)
    }
}