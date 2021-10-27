package rubiks.info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

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

        Toast.makeText(this, statisticsData[0], Toast.LENGTH_SHORT).show()

        val solvesCount = findViewById<TextView>(R.id.solvesCountData)

    }
}