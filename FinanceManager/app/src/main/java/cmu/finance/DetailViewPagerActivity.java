package cmu.finance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import cmu.exception.UiException;

/**
 * Created by Pedro on 22/11/2015.
 * View Pager container activity. This activity is used as a container for DetailViewPagerFragment.
 */
public class DetailViewPagerActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewPager pager;
        ViewPagerAdapter adapter;
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.detail_view_pager_layout);

            pager = (ViewPager) findViewById(R.id.detailPager);
            adapter = new ViewPagerAdapter(getSupportFragmentManager());

            pager.setAdapter(adapter);

            // Set pager initial position
            pager.setCurrentItem(1); // starts on second page (Monthly details)
        } catch (Exception e){
            UiException ue = new UiException(7);
            ue.fix();
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(getApplicationContext(), FinanceMainPageActivity.class);
        startActivity(startMain);
    }
}
