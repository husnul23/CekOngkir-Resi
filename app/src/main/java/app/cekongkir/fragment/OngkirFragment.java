package app.cekongkir.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import app.cekongkir.R;
import app.cekongkir.activity.TabActivity;
import app.cekongkir.data.Api;
import app.cekongkir.data.SessionManager;
import app.cekongkir.utils.NonScrollListView;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class OngkirFragment extends Fragment {

    private static String WEIGHT = "1000";

    private MaterialEditText edt_origin, edt_destination, edt_subdistrict;
    private ProgressBar progressBar;
    private FancyButton btn_preview;

    private String ORIGIN_ID        = "";
    private String DESTINATION_ID   = "";
    private String SUBDISTRICT_ID   = "";

    private String REQ_STATUS   = "";
    private ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

    SimpleAdapter simpleAdapter;
    SessionManager session;

    // List
    NonScrollListView lst_result;
    ArrayList<HashMap<String, String>> lists = new ArrayList<HashMap<String, String>>();
    private String _code, _service, _description;


    public OngkirFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongkir, container, false);

        edt_origin      = view.findViewById(R.id.edt_origin);
        edt_destination = view.findViewById(R.id.edt_destination);
        edt_subdistrict = view.findViewById(R.id.edt_subdistrict);
        btn_preview     = view.findViewById(R.id.btn_preview);

        session = new SessionManager(getActivity());
        if (session.isExist()){

            HashMap<String, String> users = session.getData();

            ORIGIN_ID       = users.get(session.ORIGIN_ID);
            DESTINATION_ID  = users.get(session.DESTIONATION_ID);
            SUBDISTRICT_ID  = users.get(session.SUBDISTRICT_ID);

            edt_origin.setText(users.get(session.ORIGIN_NAME));
            edt_destination.setText(users.get(session.DESTIONATION_NAME));
            edt_subdistrict.setText(users.get(session.SUBDISTRICT_NAME));
        }

        edt_origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                REQ_STATUS = "ORIGIN_ID";
                dialog_adapter();
            }
        });

        edt_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                REQ_STATUS = "DESTINATION_ID";
                dialog_adapter();
            }
        });

        edt_subdistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DESTINATION_ID.equals("")){
                    REQ_STATUS = "SUBDISTRICT";
                    dialog_adapter();
                }
            }
        });

        btn_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ORIGIN_ID.equals("") ){
                    edt_origin.setErrorColor(R.color.colorHint);
                    edt_origin.setError("Kota asal tidak boleh kosong");
                } else if (DESTINATION_ID.equals("")){
                    edt_destination.setErrorColor(R.color.colorHint);
                    edt_destination.setError("Kota tujuan tidak boleh kosong");
                } else if (SUBDISTRICT_ID.equals("")){
                    edt_subdistrict.setErrorColor(R.color.colorHint);
                    edt_subdistrict.setError("Kecamatan tujuan tidak boleh kosong");
                } else {
                    getCost();
                    session.createSession(ORIGIN_ID, edt_origin.getText().toString(), DESTINATION_ID,
                            edt_destination.getText().toString(), SUBDISTRICT_ID, edt_subdistrict.getText().toString());

                    TabActivity._adsCount += 1;
                    if (TabActivity._adsCount > 4){
                        TabActivity.interstitialAd.show();
                        TabActivity._adsCount = 0;
                    }
                }
            }
        });

        // List
        lst_result      = view.findViewById(R.id.lst_result);
        progressBar     = view.findViewById(R.id.progressBar);

        return view;
    }

    private void getCity(){
        arrayList.clear();
        progressBar.setVisibility(View.VISIBLE);
        Log.d("_logUrl", Api.CITY);
        AndroidNetworking.get(Api.CITY)
                .addHeaders("key", Api.KEY)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("_logJson", response.toString());
                        JSONObject jsonObject   = response.optJSONObject("rajaongkir");
                        JSONArray JSONArray     = jsonObject.optJSONArray("results");

                        int i;
                        for (i = 0; i < JSONArray.length(); i++) {
                            JSONObject jsonObject1  = JSONArray.optJSONObject(i);
//                            Log.d("Log city", jsonObject1.optString("city_name"));
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("id",       jsonObject1.optString("city_id"));
                            map.put("name",     jsonObject1.optString("city_name"));
                            arrayList.add(map);
                        }
                        Log.d("Log counts", "counts : " + String.valueOf(i));

                        progressBar.setVisibility(View.GONE);

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void getSubdistrict(){
        arrayList.clear();
        progressBar.setVisibility(View.VISIBLE);
        Log.d("_logUrl", Api.SUBDISTRICT);
        AndroidNetworking.get(Api.SUBDISTRICT)
                .addHeaders("key", Api.KEY)
                .addQueryParameter("city", DESTINATION_ID)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("_logJson", response.toString());
                        JSONObject jsonObject   = response.optJSONObject("rajaongkir");
                        JSONArray JSONArray     = jsonObject.optJSONArray("results");

                        int i;
                        for (i = 0; i < JSONArray.length(); i++) {
                            JSONObject jsonObject1  = JSONArray.optJSONObject(i);
//                            Log.d("Log city", jsonObject1.optString("city_name"));
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("id",       jsonObject1.optString("subdistrict_id"));
                            map.put("name",     jsonObject1.optString("subdistrict_name"));
                            arrayList.add(map);
                        }
                        Log.d("Log counts", "counts : " + String.valueOf(i));

                        progressBar.setVisibility(View.GONE);

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void dialog_adapter(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_city);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final EditText edt_search   = dialog.findViewById(R.id.edt_search);
        final ListView lst_result   = dialog.findViewById(R.id.lst_result);
        progressBar                 = dialog.findViewById(R.id.progressBar);

        if (REQ_STATUS.equals("SUBDISTRICT")){
            edt_search.setHint("Cari Kecamatan");
            getSubdistrict();
        } else {
            edt_search.setHint("Cari Kota");
            getCity();
        }

        simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.adapter_city,
                new String[] { "id", "name"}, new int[] {R.id.txtId, R.id.txtName});
        lst_result.setAdapter(simpleAdapter);
        lst_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String list_id      = ((TextView) view.findViewById(R.id.txtId)).getText().toString();
                String list_name    = ((TextView) view.findViewById(R.id.txtName)).getText().toString();

                switch (REQ_STATUS){
                    case "ORIGIN_ID":
                        Log.e("_logOriginId", list_id);

                        ORIGIN_ID = list_id;
                        edt_origin.setText(list_name);

                        break;
                    case "DESTINATION_ID":
                        Log.e("_logDesId", list_id);

                        DESTINATION_ID = list_id;
                        edt_destination.setText(list_name);

                        SUBDISTRICT_ID = "";
                        edt_subdistrict.setText("");

                        break;
                    case "SUBDISTRICT":
                        Log.e("_logSubId", list_id);

                        SUBDISTRICT_ID = list_id;
                        edt_subdistrict.setText(list_name);

                        break;
                }

                dialog.dismiss();
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                // When user changed the Text
                (simpleAdapter).getFilter().filter(edt_search.getText());
            }
        });
    }

    private void getCost(){

        progressBar.setVisibility(View.VISIBLE);
        lists.clear(); lst_result.setAdapter(null);

        Locale localeID = new Locale("in", "ID");
        final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);

        AndroidNetworking.post(Api.COST)
                .addHeaders("key", Api.KEY)
                .addBodyParameter("origin",             ORIGIN_ID)
                .addBodyParameter("originType",         "city")
                .addBodyParameter("destination",        SUBDISTRICT_ID)
                .addBodyParameter("destinationType",    "subdistrict")
                .addBodyParameter("weight",             WEIGHT)
                .addBodyParameter("courier",            "jne:pos:tiki:wahana:sicepat:jnt")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("_logCost", response.toString());

                        JSONObject _rajaongkir   = response.optJSONObject("rajaongkir");
                        JSONArray _results = _rajaongkir.optJSONArray("results");
                        for (int i = 0; i < _results.length(); i++) {
                            JSONObject jsonObject  = _results.optJSONObject(i);

                            _code = jsonObject.optString("code").toUpperCase();


                            JSONArray _costs     = jsonObject.optJSONArray("costs");
                            if (_costs.length() > 0) {
                                for (int j = 0; j < _costs.length(); j++) {
                                    JSONObject jsonObject2 = _costs.optJSONObject(j);

                                    _service        = jsonObject2.optString("service");
                                    _description    = jsonObject2.optString("description");

                                    JSONArray _cost     = jsonObject2.optJSONArray("cost");
                                    if (_cost.length() > 0) {
                                        for (int k = 0; k < _cost.length(); k++) {
                                            JSONObject jsonObject3 = _cost.optJSONObject(k);

                                            HashMap<String, String> map = new HashMap<String, String>();
                                            map.put("code", _code);
                                            map.put("service", _service);
                                            map.put("description", _description);
                                            map.put("value", numberFormat.format( Double.parseDouble( jsonObject3.optString("value") ) ).replace("Rp", "IDR ") );
                                            map.put("etd", jsonObject3.optString("etd").replace("HARI", "").replace(" ", "") + " day");

                                            lists.add(map);
                                        }
                                    }
                                }
                            }
                        }

                        ListAdapter();
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("_logErr", error.toString());
                    }
                });
    }

    private void ListAdapter(){

        simpleAdapter = new SimpleAdapter(getActivity(), lists, R.layout.adapter_ongkir,
                new String[] { "code", "service", "description", "value", "etd"},
                new int[] {R.id.txtCode, R.id.txtService, R.id.txtDescription, R.id.txtValue,
                        R.id.txtEtd});

        lst_result.setAdapter(simpleAdapter);
        lst_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                transaksi_id    = ((TextView) view.findViewById(R.id.text_transaksi_id)).getText().toString();
            }
        });

        progressBar.setVisibility(View.GONE);
    }

}
