package app.cekongkir.oldjavaversion.activity;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.cekongkir.R;
import app.cekongkir.oldjavaversion.data.Api;
import app.cekongkir.oldjavaversion.data.DatabaseHelper;
import app.cekongkir.oldjavaversion.utils.CommonUtils;
import app.cekongkir.oldjavaversion.utils.NonScrollListView;

public class SummaryActivity extends AppCompatActivity {

    Context context;

    private LinearLayout linearResult;
    private ProgressBar progressBar;
    private TextView txtOrDes,
            txtWaybill_number, txtWaybill_date, txtService_code, txtWeight, txtStatus, txtShipper_name, txtReceiver_name, txtOrigin, txtDestination,
            txtPod_date, txtPod_status, txtPod_receiver;
    private NonScrollListView lst_result;

    ArrayList<HashMap<String, String>> lists = new ArrayList<HashMap<String, String>>();

    // Ads
    AdView adView;
    InterstitialAd interstitialAd;
    AdRequest adRequest;

    // SQLite
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        context = this;

        db = new DatabaseHelper(this);

        linearResult    = findViewById(R.id.linearResult);
        progressBar     = findViewById(R.id.progressBar);
        txtOrDes        = findViewById(R.id.txtOrDes);

        txtWaybill_number   = findViewById(R.id.txtWaybill_number);
        txtWaybill_date     = findViewById(R.id.txtWaybill_date);
        txtService_code     = findViewById(R.id.txtService_code);
        txtWeight           = findViewById(R.id.txtWeight);
        txtStatus           = findViewById(R.id.txtStatus);
        txtShipper_name     = findViewById(R.id.txtShipper_name);
        txtReceiver_name    = findViewById(R.id.txtReceiver_name);
        txtOrigin           = findViewById(R.id.txtOrigin);
        txtDestination      = findViewById(R.id.txtDestination);

        txtPod_date     = findViewById(R.id.txtPod_date);
        txtPod_status   = findViewById(R.id.txtPod_status);
        txtPod_receiver = findViewById(R.id.txtPod_receiver);

        lst_result      = findViewById(R.id.lst_result);
        lst_result.setFocusable(false);

        getSupportActionBar().setTitle(TabActivity.NO_RESI + " (" + TabActivity.KURIR.toUpperCase()  + ")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWaybill();
        checkCourier();
        setAds();
    }

    @Override
    public boolean onSupportNavigateUp(){
        interstitialAd.show();
        finish();
        return true;
    }

    private void getWaybill(){
//        arrayList.clear();
        progressBar.setVisibility(View.VISIBLE);
        Log.i("_logUrl", Api.WAYBILL);
        AndroidNetworking.post(Api.WAYBILL)
                .addHeaders("key", Api.KEY)
                .addBodyParameter("waybill", TabActivity.NO_RESI)
                .addBodyParameter("courier", TabActivity.KURIR)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("_logJson", response.toString());

                        JSONObject _rajaongkir  = response.optJSONObject("rajaongkir");
                        JSONObject _status      = _rajaongkir.optJSONObject("status");

                        if (_status.optInt("code") != 200){
                            CommonUtils.Toast(context, _status.optString("description"));

                        } else {
                            JSONObject _result      = _rajaongkir.optJSONObject("result");

                            JSONObject _summary      = _result.optJSONObject("summary");
                            txtOrDes.setText(
                                    _summary.optString("shipper_name") + " - " + _summary.optString("receiver_name")
                            );

                            txtWaybill_number.setText( _summary.optString("waybill_number") );
                            txtWaybill_date.setText( _summary.optString("waybill_date") );
                            txtService_code.setText( _summary.optString("service_code") );
                            txtShipper_name.setText( _summary.optString("shipper_name") );
                            txtReceiver_name.setText( _summary.optString("receiver_name") );
                            txtOrigin.setText( _summary.optString("origin") );
                            txtDestination.setText( _summary.optString("destination") );

                            JSONObject _details      = _result.optJSONObject("details");
                            if (!_details.optString("weight").equals("") && ( _details.optString("weight") != null)){
                                txtWeight.setText( _details.optString("weight") + "kg" );
                            }

                            JSONObject _delivery_status = _result.optJSONObject("delivery_status");
                            txtStatus.setText( _delivery_status.optString("status") );

                            Log.i("_logQuick", _delivery_status.optString("pod_receiver").toString());
                            if (!_delivery_status.optString("pod_receiver").equals("null")){
                                txtPod_receiver.setText( _delivery_status.optString("pod_receiver") );
                                txtPod_date.setText(
                                        _delivery_status.optString("pod_date") + " "
                                                + _delivery_status.optString("pod_time")
                                );
                            }
                            txtPod_status.setText( _delivery_status.optString("status") );

                            JSONArray _manifest = _result.optJSONArray("manifest");
                            Log.i("_logManifest", String.valueOf(_manifest.length()) );
                            for (int i = 0; i < _manifest.length(); i++) {
                                JSONObject jsonObject  = _manifest.optJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("manifest_description", jsonObject.optString("manifest_description"));
                                map.put("manifest_date", jsonObject.optString("manifest_date") + " " + jsonObject.optString("manifest_time"));
                                lists.add(map);
                            }

                            ListAdapter();

                            // SQLte insert data
                            if (db.getResiExists(TabActivity.NO_RESI) > 0){
                                db.updateResii(TabActivity.NO_RESI, _delivery_status.optString("status"));
//                                CommonUtils.Toast(getApplicationContext(), "Updated!");
                            } else {
                                db.insertResi(
                                        TabActivity.NO_RESI, TabActivity.KURIR, _delivery_status.optString("status")
                                );
                            }

                        }

                        progressBar.setVisibility(View.GONE);

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.i("_logErr", error.toString());
                    }
                });
    }

    private void ListAdapter(){
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, lists, R.layout.adapter_manifest,
                new String[] { "manifest_description", "manifest_date"},
                new int[] {R.id.txtManifest_description, R.id.txtManifest_date});

        lst_result.setAdapter(simpleAdapter);

        linearResult.setVisibility(View.VISIBLE);
    }

    private void checkCourier(){

//        if (TabActivity.KURIR.equals("jnt") || TabActivity.KURIR.equals("pos")) {
//            txtOrDes.setVisibility(View.GONE);
//            findViewById(R.id.linearSummary1).setVisibility(View.GONE);
//            findViewById(R.id.linearSummary2).setVisibility(View.GONE);
//        }

        switch (TabActivity.KURIR){
            case "jnt" :
                txtOrDes.setVisibility(View.GONE);
                findViewById(R.id.linearSummary1).setVisibility(View.GONE);
                findViewById(R.id.linearSummary2).setVisibility(View.GONE);
                break;
            case "pos" :
                txtOrDes.setVisibility(View.GONE);
                break;
        }
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

}
