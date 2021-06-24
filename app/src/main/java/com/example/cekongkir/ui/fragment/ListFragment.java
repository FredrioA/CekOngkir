package com.example.cekongkir.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cekongkir.R;
import com.example.cekongkir.data.AppDatabase;
import com.example.cekongkir.data.dao.PaketDao;
import com.example.cekongkir.data.model.Paket;
import com.example.cekongkir.ui.adapter.HomeAdapter;
import com.example.cekongkir.ui.adapter.ListAdapter;
import com.example.cekongkir.ui.viewmodel.DetailViewModel;
import com.example.cekongkir.ui.viewmodel.ListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements ListAdapter.DeleteDialogListener{

    protected ListViewModel viewModel;
    protected RecyclerView recyclerView;
    protected ListAdapter adapter;
    private AlertDialog.Builder dialog;
    private ArrayList<Paket> paketList = new ArrayList<>();
    PaketDao paketDao;

    public ListFragment(){
        //empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerview(view);

        paketDao = AppDatabase.getDatabase(getContext()).paketDao();

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        viewModel.getAllPaket().observe(getViewLifecycleOwner(), list -> {
            if(list != null) {
                adapter.setData(list);
            }
        });
        deleteData();
    }

    private void initRecyclerview(View view) {
        recyclerView = view.findViewById(R.id.rvList);
        adapter  = new ListAdapter(requireActivity(), paketList);
        adapter.onDeleteListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    protected void loadData() {
        super.onResume();
        ArrayList<Paket> tempo = new ArrayList<>();

        List<Paket> data = (List<Paket>) paketDao.getAllPaket();
        tempo.clear();
        tempo.addAll(data);

        paketList = tempo;
        adapter.setData(paketList);
    }

    @Override
    public void deleteDialog(Paket paket) {
        paketDao.delete(paket);
        loadData();

//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setMessage("Delete?");
//        builder.setCancelable(true);
//
//        builder.setPositiveButton("Hapus", (dialog1, which) -> {
//
//            paketDao.delete(paket);
//            dialog1.dismiss();
//            loadData();
//        });
//
//        builder.setNegativeButton("Cancel", (dialog1, which) -> {
//            dialog1.cancel();
//        });
    }

    private void deleteData() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deletePaket(adapter.getPacketPosisi(viewHolder.getAdapterPosition()));
                Toast.makeText(requireActivity(), "BERHASIL", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
