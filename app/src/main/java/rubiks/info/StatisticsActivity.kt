package rubiks.info

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView

class StatisticsActivity : AppCompatActivity() {

    private val statisticsData = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        statisticsData.add(resources.getString(R.string.solves_count))
        statisticsData.add(resources.getString(R.string.mean_time))
        statisticsData.add(resources.getString(R.string.mean_time_last_10_solves))
        statisticsData.add(resources.getString(R.string.best_time))
        statisticsData.add(resources.getString(R.string.worst_time))
        statisticsData.add(resources.getString(R.string.time_spend_on_solving))

        val db = DataBaseHelper(this)

        if (databaseExists(this, db)) {

            val solvesCountData = findViewById<TextView>(R.id.solvesCountData)
            solvesCountData.text = db.getData("solvesCount")[0]

            val meanTimeData = findViewById<TextView>(R.id.meanTimeData)
            meanTimeData.text = db.getData("meanTime")[0]

            val meanTimeLastTenSolvesData = findViewById<TextView>(R.id.meanTimeLastTenSolvesData)
            meanTimeLastTenSolvesData.text = db.getData("meanTimeLastTenSolves")[0]

            val bestTimeData = findViewById<TextView>(R.id.bestTimeData)
            bestTimeData.text = db.getData("bestTime")[0]

            val worstTimeData = findViewById<TextView>(R.id.worstTimeData)
            worstTimeData.text = db.getData("worstTime")[0]

            val timeSpendOnSolvingData = findViewById<TextView>(R.id.timeSpendOnSolvingData)
            timeSpendOnSolvingData.text = db.getData("timeSpendOnSolving")[0]

        } else {

            val table = findViewById<TableLayout>(R.id.statisticsTable)
            table.visibility = View.GONE

            val textViewInfo = TextView(this)
            textViewInfo.text = resources.getString(R.string.no_database_info)
            textViewInfo.textSize = 20f
            textViewInfo.setTextColor(Color.WHITE)
            textViewInfo.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textViewInfo.gravity = Gravity.CENTER

            val statisticsMainLayout = findViewById<LinearLayout>(R.id.statisticsMainLayout)
            statisticsMainLayout.addView(textViewInfo)
        }
    }

    private fun databaseExists(context: Context, dataBaseHelper: DataBaseHelper): Boolean {

        val dbFile = context.getDatabasePath(TableInfo.TABLE_NAME)

        if (dbFile.exists()) {

            val isDataBaseEmpty = dataBaseHelper.getData("solvesCount")[0]
            if (isDataBaseEmpty.toInt() > 0) {

                return true
            }
        }

        return false
    }
}