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
public class Test {

    @Attribute(required = false)
    private String name;

    @ElementList(required = false)
    private LinkedList<Question> questions;

    @ElementList(required = false)
    private LinkedList<TestResult> results;



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
}