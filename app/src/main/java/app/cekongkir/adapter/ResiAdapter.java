package app.cekongkir.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.cekongkir.R;
import app.cekongkir.data.model.Resi;

public class ResiAdapter extends ArrayAdapter<Resi> {

    public ResiAdapter(@NonNull Context context, @NonNull List<Resi> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Resi resi = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.adapter_resi, parent, false
            );
        }

        TextView txtResi    = convertView.findViewById(R.id.txtResi);
        TextView txtStatus  = convertView.findViewById(R.id.txtStatus);
        TextView txtCourier = convertView.findViewById(R.id.txtCourier);
        txtResi.setText( resi.getResi() );
        txtStatus.setText( resi.getStatus() );
        txtCourier.setText( resi.getKurir().toUpperCase() );

        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
