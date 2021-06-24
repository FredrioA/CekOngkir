package com.example.cekongkir.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cekongkir.data.AppRepository;
import com.example.cekongkir.data.model.city.DataCity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchViewModel extends AndroidViewModel {

    protected AppRepository repository;

    public SearchViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = new AppRepository(application);
    }

    public LiveData<ArrayList<DataCity>> getCity() {
        return repository.getCity();
    }

}
