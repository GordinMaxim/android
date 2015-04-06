package org.threeflow.utils.sortablelist;

import org.threeflow.utils.sortablelist.MainMenuEntry;

/**
 * Created by gormakc on 4/6/15.
 */
public class MainMenuTestEntry extends MainMenuEntry {
    private String extraKey;

    public String getExtraKey() {
        return extraKey;
    }

    public void setExtraKey(String extraKey) {
        this.extraKey = extraKey;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
