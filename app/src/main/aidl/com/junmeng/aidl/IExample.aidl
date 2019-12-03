package com.junmeng.aidl;

import com.junmeng.aidl.SomeBean;
import com.junmeng.aidl.InoutBean;

interface IExample {


    const int VERSION = 1;
    const String s = "test";

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    SomeBean testIn(in SomeBean someBean);

    SomeBean testOut(out SomeBean someBean);

    InoutBean testInOut(inout InoutBean someBean);
}