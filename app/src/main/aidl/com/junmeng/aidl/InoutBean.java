package com.junmeng.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * inout类型的话除了Parcelable接口还必须增加readFromParcel方法的实现
 */
public class InoutBean implements Parcelable {

   private String some;

    public String getSome() {
        return some;
    }

    public void setSome(String some) {
        this.some = some;
    }

    public InoutBean(String some) {
        this.some = some;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public void readFromParcel(Parcel reply){
        this.some = reply.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.some);
    }

    protected InoutBean(Parcel in) {
        this.some = in.readString();
    }

    public static final Creator<InoutBean> CREATOR = new Creator<InoutBean>() {
        @Override
        public InoutBean createFromParcel(Parcel source) {
            return new InoutBean(source);
        }

        @Override
        public InoutBean[] newArray(int size) {
            return new InoutBean[size];
        }
    };

    @Override
    public String toString() {
        return "InoutBean{" +
                "some='" + some + '\'' +
                '}';
    }
}