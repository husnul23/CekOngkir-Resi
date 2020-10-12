package app.cekongkir.oldjavaversion.activity;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import app.cekongkir.R;
import app.cekongkir.oldjavaversion.adapter.ViewPagerAdapter;
import app.cekongkir.oldjavaversion.fragment.OngkirFragment;
import app.cekongkir.oldjavaversion.fragment.ResiFragment;

public class TabActivity extends AppCompatActivity {

    public static String NO_RESI;
    public static String KURIR;

    public static FloatingActionButton fab;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    // Ads
    AdView adView;
    public static InterstitialAd interstitialAd;
    AdRequest adRequest;
    public static int _adsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        addTabs(viewPager);
        tabLayout.setupWithViewPager( viewPager );
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int posisition = tab.getPosition();
                Log.i("_logPosition", String.valueOf(posisition));

                if (posisition > 0){
//                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setAds();
    }

    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OngkirFragment(), "CEK ONGKIR");
        adapter.addFrag(new ResiFragment(), "CEK RESI");
        viewPager.setAdapter(adapter);
    }

    private void setAds(){
        // Banner
        adView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Interstitial
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.loadAd(adRequest);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // code here
                interstitialAd.loadAd(adRequest);
            }
        });
    }

    @Override
    public void onBackPressed(){
        interstitialAd.show();
        finish();
    }

}
