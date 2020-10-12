package app.cekongkir.oldjavaversion.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import app.cekongkir.R;
import app.cekongkir.oldjavaversion.activity.SummaryActivity;
import app.cekongkir.oldjavaversion.activity.TabActivity;
import app.cekongkir.oldjavaversion.adapter.ResiAdapter;
import app.cekongkir.oldjavaversion.data.DatabaseHelper;
import app.cekongkir.oldjavaversion.data.model.Resi;
import app.cekongkir.oldjavaversion.utils.NonScrollListView;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResiFragment extends Fragment {

    private EditText edt_search;
    private NonScrollListView lst_result;
    private LinearLayout linearKosong;
    private SwipeRefreshLayout swipeRefresh;

    // SQLite
    private DatabaseHelper db;
    private List<Resi> resiList = new ArrayList<>();

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    public ResiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        TabActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_adapter();
            }
        });
        View view = inflater.inflate(R.layout.fragment_resi, container, false);

        edt_search      = view.findViewById(R.id.edt_search);
        lst_result      = view.findViewById(R.id.lst_result);
        swipeRefresh    = view.findViewById(R.id.swipeRefresh);
        linearKosong    = view.findViewById(R.id.linearKosong);

        getHistori();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lst_result.setAdapter(null); resiList.clear();
                getHistori();
            }
        });

        edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_adapter();
//                CommonUtils.Toast(getContext(), "Histori masih dalam perbaikan");
            }
        });

        return view;
    }

    private void getHistori(){
        swipeRefresh.setRefreshing(false);

        db = new DatabaseHelper(getContext());
        resiList.addAll(db.getAllResi());
        Log.i("_logResi", String.valueOf(db.getResisCount()) );
        if (db.getResisCount() > 0){
            linearKosong.setVisibility(View.GONE);
            lst_result.setVisibility(View.VISIBLE);
        }

        lst_result.setAdapter(new ResiAdapter(getContext(), resiList));
        lst_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TabActivity.KURIR   = resiList.get(position).getKurir().toLowerCase();
                TabActivity.NO_RESI = resiList.get(position).getResi();
                startActivity(new Intent(getContext(), SummaryActivity.class));
            }
        });

    }

    private void dialog_adapter() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_courier);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final EditText edt_resi     = dialog.findViewById(R.id.edt_resi);
        final ImageView img_resi    = dialog.findViewById(R.id.img_resi);
        final Spinner spn_courier   = dialog.findViewById(R.id.spn_courier);
        final FancyButton btn_oke   = dialog.findViewById(R.id.btn_oke);

//        edt_resi.setText("JS1945157888");
//        edt_resi.setText("032460008891918");

//        jne, pos, tiki, wahana, jnt, rpx, sap, sicepat, pcp, jet, dse, first, slis, nss
        String courier[] = {"JNE", "POS", "TIKI", "WAHANA", "JNT", "RPX", "SAP", "SICEPAT", "PCP", "JET", "DSE", "FIRST", "SLIS", "NSS"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_item, courier);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spn_courier.setAdapter(spinnerArrayAdapter);

        img_resi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CommonUtils.Toast(getContext(), "Masih dalam perbaikan");
                scanBar(v);

            }
        });

        btn_oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CommonUtils.Toast(getContext(), spn_courier.getSelectedItem().toString().toLowerCase() );
                if (edt_resi.length() > 3){

                    TabActivity._adsCount += 1;
                    if (TabActivity._adsCount > 4){
                        TabActivity.interstitialAd.show();
                        TabActivity._adsCount = 0;
                    }

                    TabActivity.KURIR   = spn_courier.getSelectedItem().toString().toLowerCase();
                    TabActivity.NO_RESI = edt_resi.getText().toString();
                    startActivity(new Intent(getContext(), SummaryActivity.class));
                    dialog.dismiss();
                }
            }
        });
    }

    // BARCODE SCANNER
    public void scanBar(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(getActivity(), "No Scanner Found", "Download a scanner code activity?",
                    "Yes", "No").show();
        }
    }

    private static AlertDialog showDialog(
            final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    // RESULT OF BARCODE SCAN
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == getActivity().RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

//                edt_resi.setText(contents.toString());
            }
        }
    }

}
