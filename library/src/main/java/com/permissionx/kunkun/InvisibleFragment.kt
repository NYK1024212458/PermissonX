package com.permissionx.kunkun

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.Fragment


typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {
    //进行优化

    //定义可一个回调  类型是函数类型 接受一个boolean和list类型的参数,没有返回值
//    var callBack: ((Boolean, List<String>) -> Unit)? = null
    // 优化后
    var callBack: PermissionCallback? = null

    //  定义了符合 requestNow 的高级函数方法   接受两个参数,一个callback 函数类型一个是可变参数 String
//    fun requestNow(cb: (Boolean, List<String>) -> Unit, vararg permissions: String) {
//        callBack = cb
//        // Fragment的requestPermissions 过时了
//        requestPermissions(permissions, 1)¬
//    }
    fun requestNow(cb: PermissionCallback, vararg permissions: String) {
        callBack = cb
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            // 存放被拒绝的权限列表
            val deniedList = ArrayList<String>()
            // 库函数  withIndex()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callBack.let {
                it?.let { it1 -> it1(allGranted, deniedList) }
            }
        }
    }


}