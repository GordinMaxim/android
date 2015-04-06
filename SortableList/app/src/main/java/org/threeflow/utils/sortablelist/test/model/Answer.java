package org.threeflow.utils.sortablelist.test.model;


import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root
public class Answer implements Serializable, Parcelable {
    @Attribute(required = true)
    private String name;

    @Attribute(required = true)
    private int value;

    @Attribute(required = false)
    private boolean stop = false;

    @Attribute(required = false)
    private String result = "";

    public Answer(){}

    public Answer(Parcel source) {
        name = source.readString();
        value = source.readInt();
        stop = (source.readInt() == 1);
        result = source.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
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
        dest.writeString(name);
        dest.writeInt(value);
        dest.writeInt(stop ? 1 : 0);
        dest.writeString(result);
    }

    Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel source) {
            return new Answer(source);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}
