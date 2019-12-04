package com.app.id.spk_penerimaan_beasiswa.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.id.spk_penerimaan_beasiswa.*;
import com.app.id.spk_penerimaan_beasiswa.kelas.final_result;

import java.util.ArrayList;
import java.util.List;

public class adapter  extends ArrayAdapter<final_result> {

    private int resourceLayout;
    private Context mContext;

    public adapter(Context context, int resource, List<final_result> items) {
            super(context, resource, items);
            this.resourceLayout = resource;
            this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
            }

            final_result p = getItem(position);

            if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.tvListNisn);
            TextView tt2 = (TextView) v.findViewById(R.id.tvListNama);
            TextView tt3 = (TextView) v.findViewById(R.id.tvListScore);

                if (tt1 != null) {
                tt1.setText(p.getNisns_akhir());
                }

                if (tt2 != null) {
                tt2.setText(p.getNama_akhir());
                }

                if (tt3 != null) {
                tt3.setText(p.getHasil_akhir());
                }
            }

        return v;
    }

}
