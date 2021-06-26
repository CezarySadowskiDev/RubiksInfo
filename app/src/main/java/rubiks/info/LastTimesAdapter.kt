package rubiks.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    override fun getItemCount(): Int {

        return timesList.size
    }

}

class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)