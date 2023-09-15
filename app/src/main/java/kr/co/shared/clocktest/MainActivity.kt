package kr.co.shared.clocktest

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import kr.co.shared.clocktest.data.RoutineData
import kr.co.shared.clocktest.databinding.ActivityMainBinding
import kr.co.shared.clocktest.db.DatabaseHelper
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val dateFormatTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val dateFormatDay = SimpleDateFormat("yyyy년 MM월 dd일 E요일", Locale.KOREA)
    private lateinit var binding : ActivityMainBinding
    var routine  = MutableLiveData<RoutineData>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
        }
        routine.value = RoutineData( "test", "date", 1,1,1,1,1)
        dateFormatTime.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        dateFormatDay.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        routine.observe(this) {
            binding.txtTime.text = routine.value!!.current_time;
            binding.txtDate.text = routine.value!!.current_date;
        }

        handler.post(updateTimeTask)

        initImageButton()

       // dbTest()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // 화면 회전 이벤트가 발생했을 때 수행할 작업을 여기에 추가합니다.
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            // 가로 방향으로 회전했을 때 수행할 작업
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
            initImageButton()

            Toast.makeText(this, "가로 방향으로 회전했습니다.", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 세로 방향으로 회전했을 때 수행할 작업
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
            initImageButton()

            Toast.makeText(this, "세로 방향으로 회전했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initImageButton() {
        binding.btnRoutine.setOnClickListener {
            Toast.makeText(applicationContext,
                "Enter both numbers", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, RoutineActivity::class.java)
            startActivity(intent)


        }

        binding.btnRoutine2.setOnClickListener {
            Toast.makeText(applicationContext,
                "Move RunActivity", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, RunActivity::class.java)
            intent.putExtra("routine",  RoutineData( "test", "date", 60,1,10,1,10))
            startActivity(intent)


        }
    }

    private val updateTimeTask = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()

            //Log.d("pjs", "currentTime : " + currentTime.toString());

            //binding.txtTime.text = dateFormatTime.format(Date(currentTime))
            //binding.txtDate.text = dateFormatDay.format(Date(currentTime))
            routine.postValue(RoutineData(dateFormatTime.format(Date(currentTime))
                                               , dateFormatDay.format(Date(currentTime)), 1,1,1,1, 1 ));



            handler.postDelayed(this, 1000)

        }
    }

    @SuppressLint("Range")
    fun dbTest() {
        Log.d("pjs", "dbTest start" );

        val context: Context = this;

        val dbHelper = DatabaseHelper(context)
        val db = dbHelper.writableDatabase // 데이터베이스 쓰기 모드

        val values = ContentValues()
        values.put("name", "John")
        val newRowId = db.insert("MyTable", null, values)

        var query = "INSERT INTO MyTable('name') values('pjongsik');"
        db.execSQL(query)

        // 데이터 조회 예시
        val cursor: Cursor = db.query("MyTable", null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            Log.d("pjs",  "id = "+  id + "name : " + name);
            // 데이터 처리
        }
        cursor.close()

        var query1 = "SELECT * FROM mytable;"
        var cursor2 = db.rawQuery(query1,null)
        while(cursor2.moveToNext()){

            Log.d("pjs", "name : " +cursor2.getString(cursor2.getColumnIndex("name")));
        }
        cursor2.close()

        db.close()
    }


}


