package org.threeflow.utils.sortablelist.test.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Root
public class Test implements Parcelable, Serializable {

    @Attribute(required = false)
    private String name;

    @ElementList(required = false)
    private LinkedList<Question> questions;

    @ElementList(required = false)
    private LinkedList<TestResult> results;

    public Test(){}

    public Test(Parcel source) {
        name = source.readString();
        source.readList(questions, Question.class.getClassLoader());
        source.readList(results, TestResult.class.getClassLoader());
    }

    public LinkedList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(LinkedList<Question> questions) {
        this.questions = questions;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public LinkedList<TestResult> getResults() {
        return results;
    }

    public void setResults(LinkedList<TestResult> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeList(questions);
        dest.writeList(results);
    }

    public static final Parcelable.Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel source) {
            return new Test(source);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };
}