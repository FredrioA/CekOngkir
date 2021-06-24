package com.example.cekongkir.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cekongkir.R;
import com.example.cekongkir.data.model.Paket;
import com.example.cekongkir.ui.viewmodel.DetailViewModel;

@SuppressLint("SetTextI18n")
public class DetailActivity extends AppCompatActivity {

    protected Paket paket;
    protected DetailViewModel viewModel;
    protected TextView pengirim, asal, penerima, tujuan, nama_barang, berat, estimasi, kurir, layanan, total;
    protected String txtpengirim, txtasal, txtpenerima, txttujuan, txtnama_barang, txtEstimasi, txtKurir, txtLayanan;
    protected int txtberat, txtTotal;
    protected Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        getExtra();
        setViews();
        insertDatabase();
    }

    private void initViews() {
        total       = findViewById(R.id.total);
        pengirim    = findViewById(R.id.pengirim);
        asal        = findViewById(R.id.asal);
        penerima    = findViewById(R.id.penerima);
        tujuan      = findViewById(R.id.tujuan);
        nama_barang = findViewById(R.id.namabarang);
        berat       = findViewById(R.id.berat);
        estimasi    = findViewById(R.id.estimasi);
        kurir       = findViewById(R.id.kurir);
        layanan     = findViewById(R.id.layanan);
        submit      = findViewById(R.id.btn_submit);
        viewModel   = new ViewModelProvider(this).get(DetailViewModel.class);
        paket = new Paket();
    }

    private void getExtra() {
        txtpengirim     = getIntent().getStringExtra("pengirim");
        txtasal         = getIntent().getStringExtra("asal");
        txtpenerima     = getIntent().getStringExtra("penerima");
        txttujuan       = getIntent().getStringExtra("tujuan");
        txtnama_barang  = getIntent().getStringExtra("nama_barang");
        txtberat        = getIntent().getIntExtra("berat",0);
        txtEstimasi     = getIntent().getStringExtra("estimasi");
        txtKurir        = getIntent().getStringExtra("kurir");
        txtLayanan      = getIntent().getStringExtra("layanan");
        txtTotal        = getIntent().getIntExtra("total",0);
    }

    private void setViews() {
        total.setText(String.valueOf((txtTotal)));
        pengirim.setText(txtpengirim);
        asal.setText(txtasal);
        penerima.setText(txtpenerima);
        tujuan.setText(txttujuan);
        nama_barang.setText(txtnama_barang);
        berat.setText(String.valueOf((txtberat)));
        estimasi.setText(txtEstimasi+" hari");
        kurir.setText(txtKurir);
        layanan.setText(txtLayanan);
    }

    private void insertDatabase() {
        submit.setOnClickListener(v -> {
            paket.setPengirim(txtpengirim);
            paket.setAsal(txtasal);
            paket.setPenerima(txtpenerima);
            paket.setTujuan(txttujuan);
            paket.setNama_barang(txtnama_barang);
            paket.setBerat(txtberat);
            paket.setEstimasi(txtEstimasi);
            paket.setKurir(txtKurir);
            paket.setLayanan(txtLayanan);
            paket.setTotal(txtTotal);

            viewModel.addPackage(paket);
            Toast.makeText(this, "Paket berhasil disimpan!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

}
