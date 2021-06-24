package com.example.cekongkir.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cekongkir.data.model.Paket;

import java.util.List;

@Dao
public interface PaketDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Paket aPaket);

    @Delete
    void delete(Paket paket);

    @Query("SELECT * FROM paket")
    LiveData<List<Paket>> getAllPaket();

    @Query("SELECT * FROM paket WHERE id = :id ")
    LiveData<Paket> get(int id);
}
