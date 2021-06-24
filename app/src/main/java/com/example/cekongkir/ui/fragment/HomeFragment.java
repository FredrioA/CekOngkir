package com.example.cekongkir.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cekongkir.R;
import com.example.cekongkir.data.model.cost.DataType;
import com.example.cekongkir.ui.SearchCityActivity;
import com.example.cekongkir.ui.adapter.HomeAdapter;
import com.example.cekongkir.ui.viewmodel.HomeViewModel;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static java.lang.Integer.parseInt;

public class HomeFragment extends Fragment {
    private static final int REQUEST_SOURCE = 1;
    private static final int REQUEST_DESTINATION = 2;
    protected LinearLayout llMain;
    protected HomeViewModel viewModel;
    protected HomeAdapter adapter;
    protected RecyclerView recyclerView;
    protected ProgressBar progressBar;
    protected Button btnCek;
    protected AlertDialog.Builder builder;
    protected EditText namaPengirim, kotaPengirim, namaPenerima, kotaPenerima, namaBarang, beratBarang;
    protected String source_id = "";
    protected String destination_id = "";

    ArrayList<DataType> dataCost = new ArrayList<>();
    ArrayList<String> courier    = new ArrayList<>();


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initRecyclerview();

        kotaPengirim.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SearchCityActivity.class);
            intent.putExtra("requestCode", REQUEST_SOURCE);
            startActivityForResult(intent, REQUEST_SOURCE);
        });

        kotaPenerima.setOnClickListener(v-> {
            Intent intent = new Intent(requireContext(), SearchCityActivity.class);
            intent.putExtra("requestCode", REQUEST_DESTINATION);
            startActivityForResult(intent, REQUEST_DESTINATION);
        });

        btnCek.setOnClickListener(v -> {
            String pengirim, kota_tujuan, penerima, kota_penerima, nama_barang, berat;
            onLoadData(true, 25);

            pengirim        = namaPengirim.getText().toString();
            kota_tujuan     = kotaPengirim.getText().toString();
            penerima        = namaPenerima.getText().toString();
            kota_penerima   = kotaPenerima.getText().toString();
            nama_barang     = namaBarang.getText().toString();
            berat           = beratBarang.getText().toString();

            if (pengirim.isEmpty() || kota_tujuan.isEmpty() || penerima.isEmpty() || kota_penerima.isEmpty() || nama_barang.isEmpty() || berat.isEmpty()) {
                    builder.setMessage(R.string.notice)
                            .setCancelable(false)
                            .setPositiveButton("Ok", (dialog, id) -> dialog.cancel());
                    AlertDialog alert = builder.create();
                    alert.setTitle("Notice");
                    alert.show();
                onLoadData(false, 0);
            } else {
                onLoadData(false, 100);
                cekOngkir(parseInt(berat));

                adapter.setData(dataCost, courier, pengirim, kota_tujuan, penerima, kota_penerima, nama_barang, parseInt(berat));
                dataCost.clear();
                courier.clear();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SOURCE && resultCode == RESULT_OK) {
            assert data != null;
            kotaPengirim.setText(data.getStringExtra("city"));
            source_id = data.getStringExtra("city_id");
        } else if (requestCode == REQUEST_DESTINATION && resultCode == RESULT_OK) {
            assert data != null;
            kotaPenerima.setText(data.getStringExtra("city"));
            destination_id = data.getStringExtra("city_id");
        }
    }

    private void onLoadData(Boolean loading, int progress) {
        if (loading) {
            llMain.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            progressBar.setProgress(progress);
        } else if (progress == 0){
            progressBar.setProgress(progress);
            llMain.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setProgress(progress);
            llMain.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void initRecyclerview() {
        adapter  = new HomeAdapter(requireActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void cekOngkir(int berat) {
        viewModel.postCost(source_id, destination_id, berat, "jne").observe(getViewLifecycleOwner(), responseCost -> {
            if(responseCost != null) {
                for (DataType data : responseCost.getRajaongkir().getResults().get(0).getCosts()) {
                    dataCost.add(data);
                    courier.add("JNE");
                }
            }
        });

        viewModel.postCost(source_id, destination_id, berat, "pos").observe(getViewLifecycleOwner(), responseCost -> {
            if(responseCost != null) {
                for (DataType data : responseCost.getRajaongkir().getResults().get(0).getCosts()) {
                    dataCost.add(data);
                    courier.add("POS");
                }
            }
        });

        viewModel.postCost(source_id, destination_id, berat, "tiki").observe(getViewLifecycleOwner(), responseCost -> {
            if(responseCost != null) {
                for (DataType data : responseCost.getRajaongkir().getResults().get(0).getCosts()) {
                    dataCost.add(data);
                    courier.add("TIKI");
                }
            }
        });
    }

    private void initViews(View view) {
        viewModel       = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        namaPengirim    = view.findViewById(R.id.in_nama_pengirim);
        kotaPengirim    = view.findViewById(R.id.in_kota_pengirim);
        namaPenerima    = view.findViewById(R.id.in_nama_penerima);
        kotaPenerima    = view.findViewById(R.id.in_kota_penerima);
        namaBarang      = view.findViewById(R.id.in_nama_barang);
        beratBarang     = view.findViewById(R.id.in_berat);
        llMain          = view.findViewById(R.id.llMain);
        recyclerView    = view.findViewById(R.id.rvMain);
        progressBar     = view.findViewById(R.id.progressBar);
        btnCek          = view.findViewById(R.id.btn_cekongkir);
        builder         = new AlertDialog.Builder(getActivity());
    }
}
