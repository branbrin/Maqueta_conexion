package com.example.maqueta_conexion;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Botones.class}, version = 5, exportSchema = false)
public abstract class ViewRoomDatabase extends RoomDatabase {
    public abstract BotonesDao botonesDao();

    private static volatile ViewRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ViewRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ViewRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ViewRoomDatabase.class, "view_database").allowMainThreadQueries().fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
