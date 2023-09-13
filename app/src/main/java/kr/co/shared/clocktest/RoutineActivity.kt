package kr.co.shared.clocktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kr.co.shared.clocktest.databinding.ActivityMainBinding

class RoutineActivity : AppCompatActivity() {

    private lateinit var binding1 : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)

//        binding1 =  DataBindingUtil.setContentView(this, R.layout.activity_routine)
//
//        binding1.apply {
//            lifecycleOwner = this@RoutineActivity
//        }
//

    }
}