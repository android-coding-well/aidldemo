package com.junmeng.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * 演示了标签in out inout及跨进程对象传递
 */
class ExampleService : Service() {

    val TAG: String = this.javaClass.toString()
    var inoutServiceBean:InoutBean? = null
    val iExample = object : IExample.Stub() {


        /**
         * in：客户端流向服务端，服务端所做的修改，客户端不会发送变化
         * 一般参数传递都是采用in类型，双方互不影响
         */
        override fun testIn(someBean: SomeBean?): SomeBean? {
            Log.i(TAG, "testIn:someBean=" + someBean) //testIn:someBean=SomeBean{some='client'}
            //服务端对其进行修改
            someBean?.some = "service"
            return someBean
        }

        /**
         * out：服务端将会收到客户端对象，该对象不为空，但是它里面的字段为空，服务端所做的修改，客户端会同步变化
         * 对象需要有readFromParcel方法，如SomeBean的readFromParcel
         */
        override fun testOut(someBean: SomeBean?): SomeBean? {
            Log.i(TAG, "testOut:someBean=" + someBean) //testOut:someBean=SomeBean{some='null'}
            //服务端对其进行修改
            someBean?.some = "service"
            return someBean
        }

        /**
         * inout：服务端将会接收到客户端传来对象的完整信息，并且客户端将会同步服务端对该对象的任何变动
         * 对象需要有readFromParcel方法，如InoutBean的readFromParcel
         */
        override fun testInOut(inoutBean: InoutBean?): InoutBean? {
            Log.i(TAG, "testInOut:inoutBean=" + inoutBean) //testInOut:inoutBean=InoutBean{some='client'}
            //服务端对其进行修改
            inoutBean?.some = "service"
            inoutServiceBean=inoutBean
            return inoutBean
        }



        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {

        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return iExample
    }
}