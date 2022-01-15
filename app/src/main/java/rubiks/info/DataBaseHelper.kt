package rubiks.info

import android.content.ContentValues
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

    fun updateRow(timeToBeUpdated: String, newTime: String) {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(TableInfo.TABLE_COLUMN_TIME, newTime)

        db.update(
            TableInfo.TABLE_NAME,
            contentValues,
            "_id IN (SELECT max(_id) FROM ${TableInfo.TABLE_NAME} WHERE ${TableInfo.TABLE_COLUMN_TIME} = $timeToBeUpdated)",
            null
        )
    }

    fun getData(dataType: String): ArrayList<String> {
        val db = this.readableDatabase

        when (dataType) {
            "allData" -> {
                val addDataList = arrayListOf<String>()
                val cursor = db.rawQuery(
                    "SELECT ${TableInfo.TABLE_COLUMN_TIME} FROM ${TableInfo.TABLE_NAME}",
                    null
                )

                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast) {
                        val time =
                            formatTime(cursor.getString(cursor.getColumnIndex(TableInfo.TABLE_COLUMN_TIME)))
                        addDataList.add(time)

                        cursor.moveToNext()
                    }
                }

                cursor.close()

                return addDataList
            }
            "solvesCount" -> {
                return arrayListOf(executeQuery(db, "SELECT count(*) FROM ${TableInfo.TABLE_NAME}"))
            }
            "meanTime" -> {
                return arrayListOf(
                    formatTime(
                        executeQuery(
                            db,
                            "SELECT avg(${TableInfo.TABLE_COLUMN_TIME}) " +
                                    "FROM ${TableInfo.TABLE_NAME}"
                        )
                    )
                )
            }
            "meanTimeLastTenSolves" -> {
                return arrayListOf(
                    formatTime(
                        executeQuery(
                            db,
                            "SELECT avg(${TableInfo.TABLE_COLUMN_TIME}) " +
                                    "FROM (SELECT ${TableInfo.TABLE_COLUMN_TIME} " +
                                    "FROM ${TableInfo.TABLE_NAME} " +
                                    "ORDER BY _id DESC LIMIT 10)"
                        )
                    )
                )
            }
            "bestTime" -> {
                return arrayListOf(
                    formatTime(
                        executeQuery(
                            db,
                            "SELECT ${TableInfo.TABLE_COLUMN_TIME} " +
                                    "FROM ${TableInfo.TABLE_NAME} " +
                                    "ORDER BY ${TableInfo.TABLE_COLUMN_TIME} ASC LIMIT 1"
                        )
                    )
                )
            }
            "worstTime" -> {
                return arrayListOf(
                    formatTime(
                        executeQuery(
                            db,
                            "SELECT ${TableInfo.TABLE_COLUMN_TIME} " +
                                    "FROM ${TableInfo.TABLE_NAME} " +
                                    "ORDER BY ${TableInfo.TABLE_COLUMN_TIME} DESC LIMIT 1"
                        )
                    )
                )
            }
            "timeSpendOnSolving" -> {
                return arrayListOf(
                    formatTime(
                        executeQuery(
                            db,
                            "SELECT sum(${TableInfo.TABLE_COLUMN_TIME}) FROM ${TableInfo.TABLE_NAME}"
                        )
                    )
                )
            }
        }

        return arrayListOf("0")
    }

    fun removeDataFromDatabase(time: String) {
        val db = this.writableDatabase

        db.delete(
            TableInfo.TABLE_NAME,
            "_id IN (SELECT max(_id) FROM ${TableInfo.TABLE_NAME} WHERE ${TableInfo.TABLE_COLUMN_TIME} = $time)",
            null
        )
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