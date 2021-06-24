package com.example.cekongkir.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cekongkir.data.AppRepository;
import com.example.cekongkir.data.model.Paket;

import org.jetbrains.annotations.NotNull;

public class DetailViewModel extends AndroidViewModel {

    protected AppRepository repository;

    public DetailViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = new AppRepository(application);
    }

    public void addPackage(Paket aPaket) {
        repository.insertPackage(aPaket);
    }
}
