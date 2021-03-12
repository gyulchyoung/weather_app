package com.example.Magic_CnyangE.location;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Location.class}, version = 1, exportSchema = true)
public abstract class LocationDatabase extends RoomDatabase {
    private static volatile LocationDatabase LOCATION_INSTANCE;
    public static final String DB_NAME = "locations";

    public abstract LocationDao locationDao();

    public static LocationDatabase getDatabases(final Context context){
        
        if(LOCATION_INSTANCE == null){
            synchronized (LocationDatabase.class){
                if(LOCATION_INSTANCE == null){
                    LOCATION_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                LocationDatabase.class, DB_NAME)
                                .allowMainThreadQueries()
                                .fallbackToDestructiveMigration()
                                .createFromAsset("databases/locations.db")
                                .build();
                }
            }
        }
        return LOCATION_INSTANCE;
    }

    public static void destroyInstance(){
        LOCATION_INSTANCE = null;
    }
}
