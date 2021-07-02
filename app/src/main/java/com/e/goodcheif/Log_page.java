package com.e.goodcheif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.e.goodcheif.log_in_fragment.Sign_Up;
import com.e.goodcheif.log_in_fragment.log_in;

import java.util.ArrayList;
import java.util.List;

public class Log_page extends AppCompatActivity {
    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new log_in(), "Sign In");
        adapter.addFragment(new Sign_Up(), "Sign Up");
        viewPager.setAdapter(adapter);
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_title);

        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.red));
        pagerTabStrip.setTextColor(getResources().getColor(R.color.red));

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mList = new ArrayList<>();
        private final List<String> mTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }
        @Override
        public Fragment getItem(int i) {
            return mList.get(i);
        }
        @Override
        public int getCount() {
            return mList.size();
        }
        public void addFragment(Fragment fragment, String title) {
            mList.add(fragment);
            mTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}
