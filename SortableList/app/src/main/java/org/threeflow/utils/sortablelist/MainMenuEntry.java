package org.threeflow.utils.sortablelist;

import java.io.Serializable;

/**
 * Created by gormakc on 4/5/15.
 */
public class MainMenuEntry implements Serializable{
    private Class activityClass;
    private String title;

    public Class getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class activityClass) {
        this.activityClass = activityClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
