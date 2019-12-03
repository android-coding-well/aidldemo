package com.junmeng.aidl;
//即使在同目录下，也要写全路径
import com.junmeng.aidl.IMessageListener;

interface IMessageService {

void sendMessage(String msg);
void registerMessageListener(IMessageListener listener);
void unregisterMessageListener(IMessageListener listener);
}