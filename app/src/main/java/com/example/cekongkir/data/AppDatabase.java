package com.example.cekongkir.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cekongkir.data.dao.PaketDao;
import com.example.cekongkir.data.model.Paket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Paket.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PaketDao paketDao();
    public static final int NUMBER_OF_THREADS = 4;
    private static volatile AppDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static AppDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "cekongkir.db")
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                PaketDao paketDao = INSTANCE.paketDao();

                Paket paket = new Paket();
                paket.setPengirim("Ina");
                paket.setPenerima("Andi");
                paket.setAsal("Kota Yogyakarta");
                paket.setTujuan("Kabupaten Cirebon");
                paket.setNama_barang("Ikan");
                paket.setKurir("JNE");
                paket.setEstimasi("5-6");
                paket.setLayanan("OKE");
                paket.setBerat(1000);
                paket.setTotal(19000);
                paketDao.insert(paket);
            });
        }
    };
}
