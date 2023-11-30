package com.example.study_2023_11_30

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.study_2023_11_30.databinding.ActivityMainBinding

// TedPermission Import
import com.gun0912.tedpermission.PermissionBuilder
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import android.Manifest


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
//            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show()
            requestPermission {
                todo() //requestPermission의 logic에 해당함.
            }
        }
    }

    private fun todo(){
        Toast.makeText(this@MainActivity, "OK", Toast.LENGTH_SHORT).show()
    }

    private fun requestPermission(logic : () -> Unit){
        TedPermission.create()
            .setPermissionListener(object  : PermissionListener {
                override fun onPermissionGranted() {
                    logic()
                    // 이미 권한이 있거나, 권한 요청이 허가됨 -> logic() 실행
                }
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(this@MainActivity, "권한을 허가해주세요", Toast.LENGTH_SHORT).show()
                    // 권한이 거부됨 -> 위 함수 실행함.
                }
            })
            .setDeniedMessage("권한을 허용해주세요. [설정] > [앱 및 알림] > [고급] > [앱 권한]")
            // requestPermission 실행했는데 setPermission에서 요구하는 펄미션이 아직 다 허용 안됬을 때 뜰 메시지...

            .setPermissions(Manifest.permission.BLUETOOTH, Manifest.permission.READ_CALENDAR, Manifest.permission.BATTERY_STATS)
            //요구하고자 하는 Permission...
            .check()
            //이건 그냥 써야하는 듯...
    }
}