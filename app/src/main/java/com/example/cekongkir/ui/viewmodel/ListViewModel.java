package com.example.cekongkir.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cekongkir.data.AppRepository;
import com.example.cekongkir.data.model.Paket;
import com.example.cekongkir.data.model.city.DataCity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends AndroidViewModel {

    protected AppRepository repository;
    public final LiveData<List<Paket>> paketList;

    public ListViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = new AppRepository(application);
        this.paketList = repository.getAllPaket();
    }

    public LiveData<List<Paket>> getAllPaket() {
        return paketList;
    }

    public void deletePaket(Paket paket){
        repository.deletePacket(paket);
    }
}
