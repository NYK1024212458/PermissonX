package com.example.permissonx

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.permissonx.databinding.ActivityMainBinding
import com.permissionx.kunkun.PermissionX

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        binding.btnCall.setOnClickListener {
            //  先申明打电话权限  在清单文件中
            PermissionX.request(this, Manifest.permission.CALL_PHONE) { allGranted, deniedList ->
                if (allGranted) {
                    callPhone()
                }else{
                    Toast.makeText(this,"权限被拒绝",Toast.LENGTH_SHORT).show()
                }

            }
        }
        binding.btnMore.setOnClickListener {
            PermissionX.request(this,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_NUMBERS){ allGated,denifeList->
                if (allGated){
                    //
                    Toast.makeText(this,"全部获取",Toast.LENGTH_SHORT).show()
                }else{
                    for (( index,result)in denifeList.withIndex() ){
                       Toast.makeText(this,"被拒绝的是"+ denifeList[index],Toast.LENGTH_SHORT).show()

                    }
                }

            }
        }
    }

    private fun callPhone() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }

    }
}