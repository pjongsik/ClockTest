package kr.co.shared.clocktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import kr.co.shared.clocktest.databinding.ActivityMainBinding
import kr.co.shared.clocktest.db.DatabaseHelper

class RoutineActivity : AppCompatActivity() {

    private lateinit var btnSave : Button
    private lateinit var txtWorkout : TextView
    private lateinit var txtRest : TextView
    private lateinit var txtSets : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)

        btnSave = findViewById(R.id.btn_save)
        txtWorkout = findViewById(R.id.txt_workout)
        txtRest = findViewById(R.id.txt_rest)
        txtSets = findViewById(R.id.txt_sets)


        val dbHelper = DatabaseHelper(this)

        btnSave.setOnClickListener {
            dbHelper.insertRoutine("first", Integer.parseInt(txtWorkout.text.toString()), Integer.parseInt(txtRest.text.toString()), Integer.parseInt(txtSets.text.toString()));

            //
            dbHelper.selectRoutine();
        }
    }
}