package com.junmeng.aidl

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    var TAG = this.javaClass.toString()

    lateinit var serviceConnection: ServiceConnection
    var iConnectService: IConnectService? = null
    var iMessageService: IMessageService? = null
    var iServiceManager: IServiceManager? = null
    var iMessageListener: IMessageListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initConnectService()
    }
    override fun onStart() {
        super.onStart()
        var intent = Intent(this, ConnectService::class.java)
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }

    private fun initConnectService() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                Log.i(TAG, "onServiceConnected:" + name.flattenToShortString())
                iServiceManager = IServiceManager.Stub.asInterface(service)
                iConnectService =
                    IConnectService.Stub.asInterface(iServiceManager?.connectServiceBinder)
                iMessageService =
                    IMessageService.Stub.asInterface(iServiceManager?.messageServiceBinder)

            }

            override fun onServiceDisconnected(name: ComponentName) {
                Log.i(TAG, "onServiceDisconnected:" + name.flattenToShortString())
                showToast("onServiceDisconnected:" + name.flattenToShortString())
                iServiceManager = null
                iConnectService = null
                iMessageService = null
            }
        }
    }



    fun showToast(text: String) {
        runOnUiThread {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }


    /////////////////////////onClick//////////////////////////////////////////
    fun onClickAsyncConnect(view: View) {
        iConnectService?.asyncConnect()
    }

    fun onClickDisconnect(view: View) {
        iConnectService?.disconnect()
    }

    fun onClickConnect(view: View) {
        iConnectService?.connect()
    }

    fun onClickConnectStatus(view: View) {
        showToast("" + iConnectService?.isConnected)
    }

    fun onClickRegister(view: View) {
        iMessageListener = object : IMessageListener.Stub() {
            override fun onMessage(msg: String?) {
                showToast("收到服务端信息：" + msg)
            }
        }
        iMessageService?.registerMessageListener(iMessageListener)
    }

    fun onClickUnregister(view: View) {
        iMessageService?.unregisterMessageListener(iMessageListener)
    }

    fun onClickSend(view: View) {
        iMessageService?.sendMessage("随机数" + Random().nextInt())
    }

    fun onClickExample(view: View) {
        startActivity(Intent(this,ExampleActivity::class.java))
    }

}
