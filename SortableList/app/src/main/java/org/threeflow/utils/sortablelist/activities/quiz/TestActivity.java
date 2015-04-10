/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.threeflow.utils.sortablelist.activities.quiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.threeflow.utils.sortablelist.R;
import org.threeflow.utils.sortablelist.SortableListViewActivity;
import org.threeflow.utils.sortablelist.test.model.Answer;
import org.threeflow.utils.sortablelist.test.model.Question;
import org.threeflow.utils.sortablelist.test.model.Test;
import org.threeflow.utils.sortablelist.test.model.TestResult;

import java.util.LinkedList;
import java.util.List;


public class TestActivity extends FragmentActivity implements OnRadioButtonSelectionListener {
    public static final String EXTRA_RESULT = "EXTRA_RESULT";
    private Test test;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments representing
     * each object in a collection. We use a {@link android.support.v4.app.FragmentStatePagerAdapter}
     * derivative, which will destroy and re-create fragments as needed, saving and restoring their
     * state in the process. This is important to conserve memory and is a best practice when
     * allowing navigation between objects in a potentially large collection.
     */
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;

    Answer[] answers;

    /**
     * The {@link android.support.v4.view.ViewPager} that will display the object collection.
     */
    ViewPager mViewPager;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_demo);
        Intent intent = getIntent();
        String key = intent.getStringExtra(SortableListViewActivity.EXTRA_TEST);
        test = SortableListViewActivity.TESTS.get(key);

        // Create an adapter that when requested, will return a fragment representing an object in
        // the collection.
        // 
        // ViewPager and its adapters use support library fragments, so we must use
        // getSupportFragmentManager.
        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager(), test);

        answers = new Answer[DemoCollectionPagerAdapter.questions.size()];
        // Set up action bar.
//        final ActionBar actionBar = getActionBar();

        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
//        actionBar.setDisplayHomeAsUpEnabled(true);

        // Set up the ViewPager, attaching the adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                // Create a simple intent that starts the hierarchical parent activity and
                // use NavUtils in the Support Package to ensure proper handling of Up.
                Intent upIntent = new Intent(this, SortableListViewActivity.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is not part of the application's task, so create a new task
                    // with a synthesized back stack.
                    TaskStackBuilder.from(this)
                            // If there are ancestor activities, they should be added here.
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else {
                    // This activity is part of the application's task, so simply
                    // navigate up to the hierarchical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRadioButtonSelect(int id, Answer answer) {
        answers[id] = answer;
        int i = 0;
        for(; i < answers.length; i++) {
            if(null == answers[i]) break;
        }
//        System.out.println("index i == " + i);
        int sum = 0;
        String result = "";
        if(i == answers.length) {
            for(Answer a : answers) {
                if(a.isStop()) break;
                sum += a.getValue();
            }
//            for(Answer a : answers) {
//                if(a.isStop()) break;
//                if()
//            }

            for(TestResult res : test.getResults()) {
                if(sum < res.getTopValue()) {
                    result += res.getResult() + ".\n";
                    break;
                }
            }
            Intent intent = new Intent(this, FinalActivity.class);
            intent.putExtra(EXTRA_RESULT, result);
            startActivity(intent);
        }

        if(mViewPager.getCurrentItem() + 1 < mDemoCollectionPagerAdapter.getCount())
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    /**
     * A {@link android.support.v4.app.FragmentStatePagerAdapter} that returns a fragment
     * representing an object in the collection.
     */
    public static class DemoCollectionPagerAdapter extends FragmentPagerAdapter {
        public static List<Question> questions;

        public DemoCollectionPagerAdapter(FragmentManager fm, Test test) {
            super(fm);
            this.questions = test.getQuestions();
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            args.putInt(DemoObjectFragment.ARG_OBJECT, i); // Our object is just an integer :-P
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // For this contrived example, we have a 100-object collection.
            return questions.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Вопрос " + (position + 1);
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DemoObjectFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
        private OnRadioButtonSelectionListener mCallback;

        public static final String ARG_OBJECT = "object";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            try {
                mCallback = (OnRadioButtonSelectionListener) activity;
            }
            catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() +
                        " must implement OnRadioButtonSelectionListener");
            }
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_collection_object, container, false);
            Bundle args = getArguments();
            int id = args.getInt(ARG_OBJECT);

            TextView title = (TextView)rootView.findViewById(R.id.question_title);
            title.setText(DemoCollectionPagerAdapter.questions.get(id).getName());
            title.setTextSize(32);

            RadioGroup radios = (RadioGroup) rootView.findViewById(R.id.radio_group);
            radios.setOnCheckedChangeListener(this);
            List<Answer> answers = DemoCollectionPagerAdapter.questions.get(id).getAnswers();
            for(Answer answer : answers) {
                RadioButton button = new RadioButton((Activity)mCallback);
                button.setText(answer.getName());
                button.setId(answers.indexOf(answer));
                button.setTextSize(24);
                button.setLayoutParams(new ActionBar.LayoutParams(radios.getLayoutParams()));
                button.setPaddingRelative(0, 30, 0, 30);
                radios.addView(button);
            }
            return rootView;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Bundle args = getArguments();
            int id = args.getInt(ARG_OBJECT);
            System.out.println("Question: " + id + ", checked radio button ID " + checkedId);
            Answer answer = DemoCollectionPagerAdapter.questions.get(id).getAnswers().get(checkedId);
            mCallback.onRadioButtonSelect(id, answer);
        }
    }
}
