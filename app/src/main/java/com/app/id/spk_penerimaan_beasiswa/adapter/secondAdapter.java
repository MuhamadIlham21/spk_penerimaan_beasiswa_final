package com.app.id.spk_penerimaan_beasiswa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.id.spk_penerimaan_beasiswa.kelas.*;
import com.app.id.spk_penerimaan_beasiswa.*;


import java.util.List;

public class secondAdapter extends RecyclerView.Adapter<secondAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<hasil> listHasil;

    public secondAdapter(Context mCtx, List<hasil> listHasil) {
        this.mCtx = mCtx;
        this.listHasil = listHasil;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_hasil_keputusan, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        hasil h = listHasil.get(position);

        holder.tvHasil.setText(h.getHasil_keputusan());
        holder.tvTanggal.setText(h.getTanggal_keputusan());
    }

    @Override
    public int getItemCount() {
        return listHasil.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvHasil, tvTanggal;

        public ProductViewHolder(View itemView) {
            super(itemView);

            tvHasil = itemView.findViewById(R.id.tvHasil);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
        }
    }
}