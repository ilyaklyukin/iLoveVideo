package se.kentor.designdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import se.kentor.designdemo.page.CountryFragment;
import se.kentor.designdemo.page.FinalFragment;

/**
 * Created by Ilya Klyukin.
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private AppCompatActivity mainActivity;

    public ApplicationTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mainActivity = getActivity();
    }

    @UiThreadTest
    public void testBackButton_click() {
        ActionBar actionBar = mainActivity.getSupportActionBar();

        int position = 1;
        actionBar.setSelectedNavigationItem(position);
        assertEquals(position, actionBar.getSelectedNavigationIndex());

        Button mBackButton = (Button) mainActivity.findViewById(R.id.navigate_to_start_button);
        mBackButton.performClick();

        assertEquals(0, actionBar.getSelectedNavigationIndex());
    }

    @UiThreadTest
    public void testSelectContentTab() {
        ActionBar actionBar = mainActivity.getSupportActionBar();

        FragmentManager fm = mainActivity.getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();

        int position = 1;

        actionBar.setSelectedNavigationItem(position);
        assertEquals(position, actionBar.getSelectedNavigationIndex());
        Fragment currentFragment = fragments.get(position);
        assertTrue(currentFragment instanceof FinalFragment);

        position = 0;
        actionBar.setSelectedNavigationItem(position);
        assertEquals(position, actionBar.getSelectedNavigationIndex());
        currentFragment = fragments.get(position);
        assertTrue(currentFragment instanceof CountryFragment);
    }

    @UiThreadTest
    public void testCountryFlag_layout() {
        final View decorView = mainActivity.getWindow().getDecorView();

        ImageView mFlag = (ImageView) mainActivity.findViewById(R.id.country_flag);
        ViewAsserts.assertOnScreen(decorView, mFlag);

        final ViewGroup.LayoutParams layoutParams = mFlag.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, 0);
    }
}