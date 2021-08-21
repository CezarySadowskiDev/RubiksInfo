package rubiks.info

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class LastTimesAdapter(private val timesList: ArrayList<String>) :
    RecyclerView.Adapter<LastTimesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastTimesAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.saved_time_row, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: LastTimesAdapter.ViewHolder, position: Int) {

        holder.itemTime.text = timesList[position]
    }

    override fun getItemCount(): Int {

        return timesList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTime: TextView = itemView.findViewById(R.id.lastTimeTV)
        private val editTimeDialog = Dialog(itemView.context)

        init {

            itemTime.setOnClickListener {

                // pobranie pozycji
                val position = absoluteAdapterPosition

                // ustawianie okna dialog
                editTimeDialog.setContentView(R.layout.edit_time_dialog)
                editTimeDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                editTimeDialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                editTimeDialog.setCancelable(false)
                editTimeDialog.show()

                // ustawiania przycisków okna dialog
                val acceptButton = editTimeDialog.findViewById<Button>(R.id.acceptBT)
                val declineButton = editTimeDialog.findViewById<Button>(R.id.declineBT)
                val deleteButton = editTimeDialog.findViewById<Button>(R.id.deleteBT)

                // ustawiania textView okna dialog
                val timeMinutes = editTimeDialog.findViewById<TextView>(R.id.timeMinutes)
                val timeSeconds = editTimeDialog.findViewById<TextView>(R.id.timeSeconds)
                val timeMilliseconds = editTimeDialog.findViewById<TextView>(R.id.timeMilliseconds)

                // nadawanie wartości polom textView okna dialog
                val time = timesList[position]
                timeMinutes.text = time.subSequence(0, time.indexOf(':'))
                timeSeconds.text = time.subSequence(time.indexOf(':') + 1, time.lastIndexOf(':'))
                timeMilliseconds.text = time.substring(time.lastIndexOf(':') + 1)

                // obsługa przycisku anulowania zmian
                declineButton.setOnClickListener {
                    editTimeDialog.dismiss()
                }

                // obsługa przycisku akceptowania zmian
                acceptButton.setOnClickListener {

                    val newTimeSeconds = formatTwoDigitData(timeSeconds.text.toString().trim())

                    if (newTimeSeconds.toInt() < 60) {

                        val newTimeMinutes = formatTwoDigitData(timeMinutes.text.toString().trim())
                        val newTimeMilliseconds =
                            formatThreeDigitData(timeMilliseconds.text.toString().trim())

                        val newTime = "$newTimeMinutes:$newTimeSeconds:$newTimeMilliseconds"

                        timesList[position] = newTime
                        editTimeDialog.dismiss()

                        val toast = Toast.makeText(
                            itemView.context,
                            R.string.time_updated,
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.TOP, 0, 20)
                        toast.show()

                        notifyDataSetChanged()
                    } else {

                        Toast.makeText(
                            itemView.context,
                            R.string.seconds_limit,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                // obsługa przycisku usuwania czasu
                deleteButton.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        R.string.time_deleting,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // obsługa przycisku usuwania czasu
                deleteButton.setOnLongClickListener {

                    timesList.removeAt(position)

                    val toast = Toast.makeText(
                        itemView.context,
                        R.string.time_deleted,
                        Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 20)
                    toast.show()

                    notifyDataSetChanged()

                    editTimeDialog.dismiss()

                    return@setOnLongClickListener true
                }
            }
        }
    }

    fun formatTwoDigitData(number: String): String {

        when {
            number.length == 1 -> {
                return "0$number"
            }
            number.isEmpty() -> {
                return "00"
            }
        }

        return number
    }

    fun formatThreeDigitData(number: String): String {

        return if (number.length <= 3) {

            number + "0".repeat(3 - number.length)
        } else {

            number.substring(0, 3)
        }
    }
}