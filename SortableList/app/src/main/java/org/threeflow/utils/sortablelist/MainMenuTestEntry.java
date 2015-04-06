package org.threeflow.utils.sortablelist;


import org.threeflow.utils.sortablelist.test.model.Test;

import java.io.Serializable;

/**
 * Created by gormakc on 4/5/15.
 */
public class MainMenuTestEntry extends MainMenuEntry implements Serializable {
    private Test test;

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
        setTitle(test.getName());
    }
}
