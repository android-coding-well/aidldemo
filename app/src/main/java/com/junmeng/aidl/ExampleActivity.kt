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

class ExampleActivity : AppCompatActivity() {

    var TAG = this.javaClass.toString()
    lateinit var serviceConnection: ServiceConnection
    var iExample: IExample? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        serviceConnection=object:ServiceConnection{
            override fun onServiceDisconnected(name: ComponentName?) {
                iExample=null
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                iExample=IExample.Stub.asInterface(service)

            }

        }
    }

    fun onClick(view: View) {
        //客户端some皆为client
        //服务端将some皆改为service

        var someBean=SomeBean("client")
        var inBean= iExample?.testIn(someBean)
        Log.i(TAG,"someBean="+someBean)//client,服务端虽然修改为service,但客户端这边不受影响
        Log.i(TAG,"inBean="+inBean)//service


        someBean=SomeBean("client")
        var outBean= iExample?.testOut(someBean)//服务端只会受到一个不为空的对象，但其字段都为null
        Log.i(TAG,"someBean="+someBean)//service,服务端修改为service,客户端受到影响
        Log.i(TAG,"outBean="+outBean)//service

        var inoutBean=InoutBean("client")
        var returnBean= iExample?.testInOut(inoutBean)
        Log.i(TAG,"inoutBean="+inoutBean)//service,服务端修改为service,客户端受到影响
        Log.i(TAG,"returnBean="+returnBean)//service




    }

    override fun onStart() {
        super.onStart()
        var intent = Intent(this, ExampleService::class.java)
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }

    fun showToast(text: String) {
        runOnUiThread {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }
}
