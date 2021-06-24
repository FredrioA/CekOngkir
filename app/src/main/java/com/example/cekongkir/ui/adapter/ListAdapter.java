package com.example.cekongkir.ui.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cekongkir.R;
import com.example.cekongkir.data.AppDatabase;
import com.example.cekongkir.data.dao.PaketDao;
import com.example.cekongkir.data.model.Paket;
import com.example.cekongkir.ui.fragment.ListFragment;
import com.example.cekongkir.utils.Constants;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("SetTextI18n")
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    protected Context context;
    protected List<Paket> listPaket;

    DeleteDialogListener deleteDialogListener;
    public ListAdapter(Context context, List<Paket> listPaket) {
        this.context = context;
        this.listPaket = listPaket;
    }
    private Dialog dialog;

    public interface Dialog{
        void onClick(int position);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public void setData(List<Paket> paket) {
        this.listPaket.clear();

        listPaket.addAll(paket);
        notifyDataSetChanged();
    }

    @NonNull @NotNull @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_db, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.kurir.setText(listPaket.get(position).getKurir());
        holder.biaya.setText(Constants.formatRupiah(listPaket.get(position).getTotal())+" ");
        holder.layanan.setText(listPaket.get(position).getLayanan());
        holder.pengirim.setText(listPaket.get(position).getPengirim());
        holder.asal.setText(listPaket.get(position).getAsal());
        holder.penerima.setText(listPaket.get(position).getPenerima());
        holder.tujuan.setText(listPaket.get(position).getTujuan());
        holder.namabarang.setText(listPaket.get(position).getNama_barang());
        holder.berat.setText(listPaket.get(position).getBerat()+" gram");
    }

    @Override
    public int getItemCount() {
        return listPaket.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kurir, biaya, layanan, pengirim, asal, penerima, tujuan, namabarang, berat;
        CardView listcv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kurir = itemView.findViewById(R.id.list_kurir);
            biaya = itemView.findViewById(R.id.list_biaya);
            layanan = itemView.findViewById(R.id.list_layanan);
            pengirim = itemView.findViewById(R.id.list_pengirim);
            asal = itemView.findViewById(R.id.list_asal);
            penerima = itemView.findViewById(R.id.list_penerima);
            tujuan = itemView.findViewById(R.id.list_tujuan);
            namabarang = itemView.findViewById(R.id.list_namabarang);
            berat = itemView.findViewById(R.id.list_berat);
            listcv = itemView.findViewById(R.id.card_courier_list);
        }
    }

    public void onDeleteListener(DeleteDialogListener deleteDialogListener){
        this.deleteDialogListener = deleteDialogListener;
    }

    public interface DeleteDialogListener{
        void deleteDialog(Paket paket);
    }

    public Paket getPacketPosisi(int posisi){
        return listPaket.get(posisi);
    }
}
