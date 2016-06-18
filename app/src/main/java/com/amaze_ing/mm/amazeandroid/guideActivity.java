package com.amaze_ing.mm.amazeandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;
import android.widget.TextView;

public class GuideActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        RadioButton[] radioButtons = new RadioButton[] {
                (RadioButton) findViewById(R.id.radio1),
                (RadioButton) findViewById(R.id.radio2),
                (RadioButton) findViewById(R.id.radio3),
                (RadioButton) findViewById(R.id.radio4),
                (RadioButton) findViewById(R.id.radio5) };
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), radioButtons);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public void toNextPage(View view) {
        Intent intent = new Intent(
                GuideActivity.this,
                LogInActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class GuideFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String TEXT_KEY = "text";

        public GuideFragment() { }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static GuideFragment newInstance(String text) {
            GuideFragment fragment = new GuideFragment();
            Bundle args = new Bundle();
            args.putString(TEXT_KEY, text);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_guide, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.guide_text);
            textView.setText(getArguments().getString(TEXT_KEY));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final RadioButton[] radioButtons;
        private Fragment[] fragments;
        private int currentPosition = 0;

        public SectionsPagerAdapter(FragmentManager fm, RadioButton[] radioButtons) {
            super(fm);
            this.radioButtons = radioButtons;
            fragments = new Fragment[5];
            fragments[0] = GuideFragment.newInstance(getString(R.string.guide_1));
            fragments[1] = GuideFragment.newInstance(getString(R.string.guide_2));
            fragments[2] = GuideFragment.newInstance(getString(R.string.guide_3));
            fragments[3] = GuideFragment.newInstance(getString(R.string.guide_4));
            fragments[4] = GuideFragment.newInstance(getString(R.string.guide_5));
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a GuideFragment (defined as a static inner class below).
            return this.fragments[position];
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (position == this.currentPosition)
                return;
            this.radioButtons[this.currentPosition].setChecked(false);
            this.currentPosition = position;
            this.radioButtons[position].setChecked(true);
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
            }
            return null;
        }
    }
}
