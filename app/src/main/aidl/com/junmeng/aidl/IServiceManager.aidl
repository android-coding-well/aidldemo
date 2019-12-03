package com.junmeng.aidl;

import android.os.IBinder;

interface IServiceManager {

    IBinder getConnectServiceBinder();
    IBinder getMessageServiceBinder();
}