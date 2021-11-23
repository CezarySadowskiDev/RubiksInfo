package rubiks.info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stopwatchCardView = findViewById<CardView>(R.id.mainMenuStopwatch)
        stopwatchCardView.setOnClickListener {
            val intent = Intent(this, StopwatchActivity::class.java)
            startActivity(intent)
        }

        val statisticsCardView = findViewById<CardView>(R.id.mainMenuStatistics)
        statisticsCardView.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }

        val tutorialCardView = findViewById<CardView>(R.id.mainMenuTutorial)
        tutorialCardView.setOnClickListener {
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
        }

        val editDataCardView = findViewById<CardView>(R.id.mainMenuEditData)
        editDataCardView.setOnClickListener {
            val intent = Intent(this, EditDataActivity::class.java)
            startActivity(intent)
        }
    }
}