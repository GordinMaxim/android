package org.threeflow.utils.sortablelist.test.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Root
public class Question {
    @Attribute(required = false)
    private String name;

    @ElementList(required = false)
    private LinkedList<Answer> answers;


    public LinkedList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(LinkedList<Answer> answers) {
        this.answers = answers;
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
}
