package org.threeflow.utils.sortablelist.test.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root
public class TestResult implements Serializable, Parcelable {
    @Attribute(required = true)
    private int topValue;

    @Attribute(required = false)
    private String result = "";

    public TestResult(){}

    public TestResult(Parcel source) {
        topValue = source.readInt();
        result = source.readString();
    }

    public int getTopValue() {
        return topValue;
    }

    public void setTopValue(int topValue) {
        this.topValue = topValue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(topValue);
        dest.writeString(result);
    }

    Creator<TestResult> CREATOR = new Creator<TestResult>() {
        @Override
        public TestResult createFromParcel(Parcel source) {
            return new TestResult(source);
        }

        @Override
        public TestResult[] newArray(int size) {
            return new TestResult[size];
        }
    };
}
