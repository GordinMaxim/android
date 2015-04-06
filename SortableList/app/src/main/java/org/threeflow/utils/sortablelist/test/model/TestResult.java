package org.threeflow.utils.sortablelist.test.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root
public class TestResult {
    @Attribute(required = true)
    private int topValue;

    @Attribute(required = false)
    private String result = "";


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
}
