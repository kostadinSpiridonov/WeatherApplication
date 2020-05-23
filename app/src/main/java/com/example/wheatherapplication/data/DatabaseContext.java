package com.example.wheatherapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseContext extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "WeatherDB";

    private static final String CREATE_HISTORY_TABLE = "CREATE TABLE " + HistoryEntity.TableName+
            " ("+HistoryEntity.IdColumn + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 HistoryEntity.CityColumn + " VARCHAR(255) ," +
                 HistoryEntity.UpdateDateColumn + " VARCHAR(225));";

    private static final String DROP_HISTORY_TABLE ="DROP TABLE IF EXISTS " + HistoryEntity.TableName;


    public DatabaseContext(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_HISTORY_TABLE);
        } catch (Exception e) {
            //Message.message(context,"" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            //Message.message(context,"OnUpgrade");
            db.execSQL(DROP_HISTORY_TABLE);
            onCreate(db);
        }catch (Exception e) {
            //Message.message(context,""+e);
        }
    }
}