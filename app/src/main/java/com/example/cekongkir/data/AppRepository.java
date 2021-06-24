package com.example.cekongkir.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cekongkir.data.dao.PaketDao;
import com.example.cekongkir.data.model.Paket;
import com.example.cekongkir.data.model.city.DataCity;
import com.example.cekongkir.data.model.city.ResponseCity;
import com.example.cekongkir.data.model.cost.ResponseCost;
import com.example.cekongkir.data.service.ApiService;
import com.example.cekongkir.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {

    protected final PaketDao paketDao;
    protected final LiveData<List<Paket>> listPaket;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        paketDao = db.paketDao();
        listPaket = paketDao.getAllPaket();
    }

    // -- API
    private final ApiService apiService = new ApiService();

    public LiveData<ArrayList<DataCity>> getCity() {
        final MutableLiveData<ArrayList<DataCity>> cities = new MutableLiveData<>();

        Call<ResponseCity> responseCity = apiService.getApi().getCity(Constants.API_KEY);
        responseCity.enqueue(new Callback<ResponseCity>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCity> call, @NotNull Response<ResponseCity> response) {
                ResponseCity cityResponse = response.body();
                if (response.isSuccessful() && response.body() != null) {
                    assert cityResponse != null;
                    ArrayList<DataCity> city = cityResponse.rajaongkir.results;
                    cities.postValue(city);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCity> call, @NotNull Throwable t) {

            }
        });

        return cities;
    }

    public LiveData<ResponseCost> postCost(String origin, String destination, int weight, String courier) {
        final MutableLiveData<ResponseCost> cost = new MutableLiveData<>();

        Call<ResponseCost> responseCity = apiService.getApi().postCost(origin, destination, weight, courier, Constants.API_KEY);

        responseCity.enqueue(new Callback<ResponseCost>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCost> call, @NotNull Response<ResponseCost> response) {
                ResponseCost res = response.body();
                if(response.isSuccessful() && response.body() != null) {
                    assert res != null;
                    cost.postValue(res);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCost> call, @NotNull Throwable t) {

            }
        });

        return cost;
    }

    // -- Database
    public void insertPackage(Paket aPaket) {
        AppDatabase.databaseWriteExecutor.execute(() -> paketDao.insert(aPaket));
    }

    public LiveData<List<Paket>> getAllPaket() {
        return listPaket;
    }

    public void deletePacket(Paket paket){
        AppDatabase.databaseWriteExecutor.execute(() -> paketDao.delete(paket));
    }
}
