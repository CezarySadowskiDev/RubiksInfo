package rubiks.info

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class StopwatchActivity : AppCompatActivity() {

    private var stopWatchStatus: Int = 1

    companion object {
        private var startTime: Long = 0
        private var timeInMilliseconds: Long = 0
        private var updateTime: Long = 0
        private var timeSwapBuff: Long = 0
    }

    private lateinit var timesList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        val stopWatch = findViewById<TextView>(R.id.stopwatchTextView)
        val linearLayout = findViewById<ConstraintLayout>(R.id.startStopChronometer)
        val handler = Handler()

        timesList = ArrayList()

        val runnable = object : Runnable {

            override fun run() {

                timeInMilliseconds = SystemClock.uptimeMillis() - startTime
                updateTime = timeSwapBuff + timeInMilliseconds
                var sec = (updateTime / 1000).toInt()
                val min = sec / 60
                sec %= 60

                val milliseconds = (updateTime % 1000).toInt()

                val stopWatchString =
                    String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" +
                            String.format("%03d", milliseconds)
                stopWatch.text = stopWatchString
                handler.postDelayed(this, 0)

            }
        }

        linearLayout.setOnClickListener {

            when (stopWatchStatus) {
                0 -> {
                    stopWatch.text = getString(R.string.time_zero)
                    stopWatchStatus = 1

                    val stopwatchInfo = findViewById<TextView>(R.id.stopwatchInfo)

                    stopwatchInfo.text = getString(R.string.start_stopwatch)

                }
                1 -> {
                    stopWatchStatus = 2
                    startTime = SystemClock.uptimeMillis()
                    handler.postDelayed(runnable, 0)

                    val times = findViewById<RecyclerView>(R.id.lastTimesRV)
                    val stopwatchInfo = findViewById<TextView>(R.id.stopwatchInfo)

                    times.visibility = View.INVISIBLE
                    stopwatchInfo.text = getString(R.string.stop_stopwatch)
                }
                2 -> {
                    stopWatchStatus = 0
                    handler.removeCallbacks(runnable)

                    val stopwatchInfo = findViewById<TextView>(R.id.stopwatchInfo)
                    stopwatchInfo.text = getString(R.string.start_stopwatch)

                    val time = stopWatch.text.toString()
                    timesList.add(time)

                    timesList.reverse()

                    val times = findViewById<RecyclerView>(R.id.lastTimesRV)
                    times.visibility = View.VISIBLE
                    times.layoutManager = LinearLayoutManager(this)
                    times.adapter = LastTimesAdapter(timesList)
                }
            }
        }
    }

    override fun onBackPressed() {

        val dbHelper = DataBaseHelper(this)
        val db = dbHelper.writableDatabase

        for (time in timesList) {
            val tempTimeMinutes = time.substring(0, time.indexOf(':')).toInt()
            val tempTimeSeconds = time.substring(time.indexOf(':') + 1, time.lastIndexOf(':')).toInt()
            val tempTimeMilliseconds = time.substring(time.lastIndexOf(':') + 1).toInt()

            val dataBaseTime = (tempTimeMinutes * 60 * 1000) + (tempTimeSeconds * 1000) + (tempTimeMilliseconds)

            val value = ContentValues()
            value.put("time", dataBaseTime)

            db.insertOrThrow(TableInfo.TABLE_NAME, null, value)

            Toast.makeText(this, dataBaseTime.toString(), Toast.LENGTH_SHORT).show()

        }

        super.onBackPressed()
    }
}