package com.example.cekongkir.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cekongkir.R;
import com.example.cekongkir.data.model.cost.DataType;
import com.example.cekongkir.ui.DetailActivity;
import com.example.cekongkir.utils.Constants;

import java.util.ArrayList;

@SuppressLint("SetTextI18n")
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    ArrayList<DataType> data = new ArrayList<>();
    ArrayList<String> courier = new ArrayList<>();
    String pengirim, asal, penerima, tujuan, nama_barang;
    int imgLogo, berat;
    public HomeAdapter(Context context){
        this.context = context;
    }

    public void setData(ArrayList<DataType> data, ArrayList<String> courier, String pengirim, String kota_tujuan, String penerima, String kota_penerima, String nama_barang, int berat){
        this.data.clear();
        this.courier.clear();

        this.pengirim = pengirim;
        this.asal = kota_tujuan;
        this.penerima = penerima;
        this.tujuan = kota_penerima;
        this.nama_barang = nama_barang;
        this.berat = berat;

        this.data.addAll(data);
        this.courier.addAll(courier);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("size","size courier: "+data.size());
        String strLogo = courier.get(position);

        switch (strLogo) {
            case "JNE":
                imgLogo = R.drawable.logo_jne;
                break;
            case "POS":
                imgLogo = R.drawable.logo_pos;
                break;
            case "TIKI":
                imgLogo = R.drawable.logo_tiki;
                break;
        }

        holder.imgLogoKurir.setImageResource(imgLogo);
        holder.tvType.setText("Jenis Layanan : " + data.get(position).getService());
        holder.tvPrice.setText(Constants.formatRupiah(data.get(position).getCost().get(0).getValue()));
        holder.tvEst.setText(data.get(position).getCost().get(0).getEtd() + " Hari");

        holder.card_courier.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);

            intent.putExtra("pengirim",pengirim);
            intent.putExtra("asal",asal);
            intent.putExtra("penerima",penerima);
            intent.putExtra("tujuan",tujuan);
            intent.putExtra("nama_barang",nama_barang);
            intent.putExtra("berat",berat);
            intent.putExtra("estimasi",data.get(position).getCost().get(0).getEtd());
            intent.putExtra("kurir",courier.get(position));
            intent.putExtra("layanan",data.get(position).getService());
            intent.putExtra("total",data.get(position).getCost().get(0).getValue());

            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEst;
        TextView tvPrice;
        TextView tvType;
        ImageView imgLogoKurir;
        CardView card_courier;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEst = itemView.findViewById(R.id.tvEst);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvType = itemView.findViewById(R.id.tvType);
            imgLogoKurir = itemView.findViewById(R.id.imgLogo);
            card_courier = itemView.findViewById(R.id.card_courier);
        }
    }
}

