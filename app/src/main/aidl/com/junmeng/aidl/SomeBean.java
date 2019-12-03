package com.junmeng.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class SomeBean implements Parcelable {
    private String some;

    public String getSome() {
        return some;
    }

    public void setSome(String some) {
        this.some = some;
    }

    @Override
    public String toString() {
        return "SomeBean{" +
                "some='" + some + '\'' +
                '}';
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

    public SomeBean() {
    }

    public SomeBean(String some){
        this.some=some;
    }

    protected SomeBean(Parcel in) {
        this.some = in.readString();
    }

    public static final Creator<SomeBean> CREATOR = new Creator<SomeBean>() {
        @Override
        public SomeBean createFromParcel(Parcel source) {
            return new SomeBean(source);
        }

        @Override
        public SomeBean[] newArray(int size) {
            return new SomeBean[size];
        }
    };
}
