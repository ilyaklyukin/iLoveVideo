package se.kentor.designdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import se.kentor.designdemo.page.CountryPage;

/**
 * Created by Ilya Klyukin.
 */
public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    private static final String TAG = "designdemo.kentor.se";

    private CountryPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        pagerAdapter = new CountryPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(pagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public void navigateToStart(View view) {
        viewPager.setCurrentItem(0);
    }

    public class CountryPagerAdapter extends FragmentStatePagerAdapter {
        private static final String ARG_PAGE_NUMBER = "position";

        public CountryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            CountryPage countryPage = CountryPage.getByPosition(position);
            Fragment fragment;
            try {
                fragment = (Fragment) countryPage.getClazz().newInstance();
            } catch (Exception e) {
                Log.e(TAG, "Error on creating page", e);
                return null;
            }

            Bundle args = new Bundle();
            args.putInt(ARG_PAGE_NUMBER, position + 1);
            fragment.setArguments(args);


            return fragment;
        }

        @Override
        public int getCount() {
            return CountryPage.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(R.string.page_title) + " " + (position + 1);
        }
    }
}
