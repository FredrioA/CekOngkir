package com.example.cekongkir.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cekongkir.data.AppRepository;
import com.example.cekongkir.data.model.cost.ResponseCost;

import org.jetbrains.annotations.NotNull;

public class HomeViewModel extends AndroidViewModel {
    protected AppRepository repository;

    public HomeViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = new AppRepository(application);
    }

    public LiveData<ResponseCost> postCost(String origin, String destination, int weight, String courier) {
        return repository.postCost(origin, destination, weight, courier);
    }
}
