package rubiks.info

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

// Opis tabeli
object TableInfo: BaseColumns {
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

class DataBaseHelper(context: Context): SQLiteOpenHelper(context, TableInfo.TABLE_NAME, null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(BasicCommands.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(BasicCommands.SQL_DELETE_TABLE)
        onCreate(p0)
    }

}