package org.threeflow.utils.sortablelist;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.threeflow.utils.sortablelist.activities.TestActivity;
import org.threeflow.utils.sortablelist.test.model.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class SortableListViewActivity extends ListActivity {
    public static final String EXTRA_TEST = "EXTRA_TEST";

    private static final String TEST_DIR = "tests";

    private Object[] sArray = {"failed to init, please report us"};
    private String entriesFile = "entries";

    private TouchInterceptor mList;

    private TouchInterceptor.DropListener mDropListener =
            new TouchInterceptor.DropListener() {
                public void drop(int from, int to) {
                    System.out.println("Droplisten from:"+from+" to:"+to);

//Assuming that item is moved up the list
                    int direction = -1;
                    int loop_start = from;
                    int loop_end = to;

//For instance where the item is dragged down the list
                    if(from < to) {
                        direction = 1;
                    }
                    Object target = sArray[from];
                    for(int i=loop_start;i!=loop_end;i=i+direction){
                        sArray[i] = sArray[i+direction];
                    }
                    sArray[to] = target;
                    System.out.println("Changed array is:"+ Arrays.toString(sArray));
                    ((BaseAdapter) mList.getAdapter()).notifyDataSetChanged();
                }
            };


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        File savedSession = getBaseContext().getFileStreamPath(entriesFile);
        if(!savedSession.exists()) {
            System.out.println("[ORDER FILE NOT FOUND]");
            List<MainMenuEntry> menuEntryList = new LinkedList<>();
            try {
                for (String s : getAssets().list(TEST_DIR)) {
                    InputStream in = getAssets().open(TEST_DIR + "/" + s);
                    Serializer serializer = new Persister();
                    Test test = serializer.read(Test.class, in);
                    MainMenuTestEntry testEntry = new MainMenuTestEntry();
                    testEntry.setTest(test);
                    testEntry.setActivityClass(TestActivity.class);
                    menuEntryList.add(testEntry);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            sArray = menuEntryList.toArray();
        }
        else {
            System.out.println("[ORDER FILE FOUND]");
            try {
                FileInputStream inputStream = openFileInput(entriesFile);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                sArray = (Object[]) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter adp = new ArrayAdapter(this, R.layout.listrow, sArray);
        setListAdapter(adp);
        mList = (TouchInterceptor) getListView();
        mList.setDropListener(mDropListener);
        registerForContextMenu(mList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        MainMenuEntry entry = (MainMenuEntry)sArray[position];
//        Toast.makeText(this, selection, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, entry.getActivityClass());

        if(entry instanceof MainMenuTestEntry) {
//            intent.putExtra(EXTRA_TEST, (android.os.Parcelable) ((MainMenuTestEntry) entry).getTest());
        }

        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onDestroy();
        try {
            ObjectOutputStream out = new ObjectOutputStream(openFileOutput(entriesFile, Context.MODE_PRIVATE));
            out.writeObject(sArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}