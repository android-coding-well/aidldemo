// IConnectService.aidl
package com.junmeng.aidl;

// Declare any non-default types here with import statements

interface IConnectService {
    //The oneway keyword modifies the behavior of remote calls.
    //When used, a remote call does not block; it simply sends the transaction data and immediately returns.
    //The implementation of the interface eventually receives this as a regular call from the Binder thread pool as a normal remote call.
    //If oneway is used with a local call, there is no impact and the call is still synchronous
    //non-block,return immediately by using oneway
    oneway void asyncConnect();
    //block until return
    boolean  connect();

    void  disconnect();

    boolean isConnected();
}