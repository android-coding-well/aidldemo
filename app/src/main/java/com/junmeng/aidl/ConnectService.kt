package com.junmeng.aidl

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.RemoteCallbackList
import android.widget.Toast

/**
 * 此服务演示了跨进程多binder及进程回调
 */
class ConnectService : Service() {

    //主线程
    var mMainHandler: Handler = Handler(Looper.getMainLooper())

    //是否连接
    var mIsConneted: Boolean = false

    //监听器，由于是跨进程，因此需要使用RemoteCallbackList
    var mListeners: RemoteCallbackList<IMessageListener> = RemoteCallbackList()


    val connectServiceBinder1 = object : IConnectService.Stub() {
        override fun isConnected(): Boolean {
            return mIsConneted
        }

        override fun asyncConnect() {
            connect()
        }

        override fun connect(): Boolean {
            showToast("connect")
            Thread.sleep(5000)
            mIsConneted = true
            return true
        }

        override fun disconnect() {
            showToast("disconnect")
            mIsConneted = false
        }


    }

    val messageServiceBinder1 = object : IMessageService.Stub() {
        override fun unregisterMessageListener(listener: IMessageListener?) {
            showToast("unregister")
            //使用RemoteCallbackList才能使得远程调用的注册与反注册成功生效，因为跨了进程
            mListeners.unregister(listener)
        }

        override fun registerMessageListener(listener: IMessageListener?) {
            showToast("register")
            mListeners.register(listener)
        }

        override fun sendMessage(msg: String?) {
            if (!mIsConneted) {
                return
            }
           var n= mListeners.beginBroadcast()
            for(  i in 0..n-1){
                //直接将客户端的信息返回
                mListeners.getBroadcastItem(i).onMessage(msg)
            }
            mListeners.finishBroadcast()
        }


    }

    private val serviceManagerBinder = object : IServiceManager.Stub() {
        override fun getConnectServiceBinder(): IBinder {
            return connectServiceBinder1
        }

        override fun getMessageServiceBinder(): IBinder {
            return messageServiceBinder1
        }


    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun onBind(intent: Intent): IBinder? {
        return serviceManagerBinder
    }

    fun showToast(text: String) {
        mMainHandler.post {
            Toast.makeText(baseContext, text, Toast.LENGTH_SHORT).show()
        }
    }
}

