package rubiks.info

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EditDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data)

        val dbHelper = DataBaseHelper(this)

        var allDataList = dbHelper.getData("allData")

        val allDataRV = findViewById<RecyclerView>(R.id.allDataRV)
        allDataRV.layoutManager = LinearLayoutManager(this)
        allDataRV.adapter = EditDataAdapter(allDataList, dbHelper)

        val deleteAllDataButton = findViewById<Button>(R.id.deleteAllDataBT)
        deleteAllDataButton.setOnClickListener {

            // potwierdzenie usunięcia czasów
            // ustawienie okna dialog
            val confirmDeletingDataDialog = Dialog(this)
            confirmDeletingDataDialog.setContentView(R.layout.confirm_deleting_dialog)
            confirmDeletingDataDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            confirmDeletingDataDialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            confirmDeletingDataDialog.setCancelable(false)
            confirmDeletingDataDialog.show()

            // ustawienie przycisków okna dialog
            val cancelButton =
                confirmDeletingDataDialog.findViewById<Button>(R.id.confirm_deleting_cancel)
            val deleteButton =
                confirmDeletingDataDialog.findViewById<Button>(R.id.confirm_deleting_delete)

            cancelButton.setOnClickListener {
                confirmDeletingDataDialog.dismiss()
            }

            deleteButton.setOnClickListener {
                baseContext.deleteDatabase(TableInfo.TABLE_NAME)

                confirmDeletingDataDialog.dismiss()
                allDataList = arrayListOf()
                allDataRV.adapter = EditDataAdapter(allDataList, dbHelper)

                Toast.makeText(this, R.string.deleting_all_data_confirmation, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}