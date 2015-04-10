package org.threeflow.utils.sortablelist.activities.quiz;

/**
 * Created by gormakc on 4/10/15.
 */
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.threeflow.utils.sortablelist.R;

public class FinalActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        TextView textView = new TextView(this);
        textView.setTextSize(40);

        String string = "результат:\n";
        string += intent.getStringExtra(TestActivity.EXTRA_RESULT);

        textView.setText(string);


        setContentView(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return super.onCreateOptionsMenu(menu);
    }


    public void exitApp(MenuItem item) {
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
