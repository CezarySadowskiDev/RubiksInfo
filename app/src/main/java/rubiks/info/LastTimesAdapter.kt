package rubiks.info

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.saved_time_row.view.*
import kotlin.collections.ArrayList


class LastTimesAdapter(private val timesList: ArrayList<String>): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val timeRow = layoutInflater.inflate(R.layout.saved_time_row, parent, false)

        return MyViewHolder(timeRow)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        
        val time = holder.view.lastTimeTV

        for (item in arrayOf(holder.adapterPosition)) {
            time.text = timesList[item]
        }

        //sprawdzić holder.title.text = time.getItemAtIndex(position).title

    }

    override fun getItemCount(): Int {

        return timesList.size
    }

    interface OnTimeListener {

        fun onTimeClick(position: Int) {

        }
    }

//    fun showTimeDialogWindow(context: Context, position: Int) {
//        val timeDialog = Dialog(context)
//        timeDialog.setContentView(R.layout.edit_time_dialog)
//        timeDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        timeDialog.show()
//
//        val book = timesList[position]
//        val minutes = book.findV
//    }

}

class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){

    init {
        itemView.setOnLongClickListener {
            val position = adapterPosition
            val time = view.lastTimeTV

//            ((StopwatchActivity.)
//            showTimeDialogWindow(view.context, position)


            Toast.makeText(itemView.context, "kliknales${position + 1}", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
    }
//
//    override fun onClick(v: View?) {
//
//
//    }
}

