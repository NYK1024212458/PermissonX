package com.permissionx.kunkun

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

// 单例
object PermissionX {
    const val TAG = "InvisibleFragment"

    //提供给外界的接口   参数: activity  类型:FragmentActivity   参数二:请求的权限   参数三: 回调
    fun request(
        activity: FragmentActivity,
        vararg permissions: String,
        callback: PermissionCallback
    ) {
        //获取fragmentmanager
        val supportFragmentManager = activity.supportFragmentManager
        //获取我们创建的隐藏的fragment
        val exitFragment = supportFragmentManager.findFragmentByTag(TAG)

        var fragment = if (exitFragment != null) {
            // 转化为 InvisibleFragment
            exitFragment as InvisibleFragment
        } else {
            // 创建一个fragment
            val invisibleFragment = InvisibleFragment()
            //添加到fragmentmanager
            supportFragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        // 开始请求
        fragment.requestNow(callback,*permissions)

    }
}