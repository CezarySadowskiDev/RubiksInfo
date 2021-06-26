package rubiks.info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.TextView
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

                val stopWatchString = String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" +
                        String.format("%03d", milliseconds)
                stopWatch.text = stopWatchString
                handler.postDelayed(this, 0)

            }

        }

        linearLayout.setOnClickListener {

            when (stopWatchStatus) {
                0 -> {
                    stopWatch.text = "00:00:000"
                    stopWatchStatus = 1


                }
                1 -> {
                    stopWatchStatus = 2
                    startTime = SystemClock.uptimeMillis()
                    handler.postDelayed(runnable, 0)

                    val times = findViewById<RecyclerView>(R.id.lastTimesRV)
                    times.visibility = View.INVISIBLE
                }
                2 -> {
                    stopWatchStatus = 0
                    handler.removeCallbacks(runnable)

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

}