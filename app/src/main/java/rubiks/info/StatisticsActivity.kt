package rubiks.info

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginTop

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

        if (databaseExists(this, TableInfo.TABLE_NAME)) {

            val solvesCountData = findViewById<TextView>(R.id.solvesCountData)
            solvesCountData.text = db.getData("solvesCount")

            val meanTimeData = findViewById<TextView>(R.id.meanTimeData)
            meanTimeData.text = db.getData("meanTime")

            val meanTimeLastTenSolvesData = findViewById<TextView>(R.id.meanTimeLastTenSolvesData)
            meanTimeLastTenSolvesData.text = db.getData("meanTimeLastTenSolves")

            val bestTimeData = findViewById<TextView>(R.id.bestTimeData)
            bestTimeData.text = db.getData("bestTime")

            val worstTimeData = findViewById<TextView>(R.id.worstTimeData)
            worstTimeData.text = db.getData("worstTime")

            val timeSpendOnSolvingData = findViewById<TextView>(R.id.timeSpendOnSolvingData)
            timeSpendOnSolvingData.text = db.getData("timeSpendOnSolving")

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

    private fun databaseExists(context: Context, databaseName: String): Boolean {

        val dbFile = context.getDatabasePath(databaseName)
        return dbFile.exists()

    }
}