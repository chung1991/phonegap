package maddiscovery.greenwich.com.maddiscovery.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.util.ArrayList;

import maddiscovery.greenwich.com.maddiscovery.Entity.TheEvent;


public class EventDao {
    private static final String DB_NAME = "MadDiscovery";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "TheEvent";
    private static final String CL_ID = "id";
    private static final String CL_TITLE = "title";
    private static final String CL_TIME = "time";
    private static final String CL_CONTENT = "content";
    private static final String CL_LAT = "lat";
    private static final String CL_LON = "lon";

    private static Context context;
    SQLiteDatabase db;
    private static EventDao sInstance;
    private OpenHelper openHelper;

    public EventDao(Context context) {
        EventDao.context = context;
        openHelper = new OpenHelper(context);
    }

    public static void init(Context context) {
        if (sInstance != null) {

        }
        sInstance = new EventDao(context);
    }

    public static EventDao getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Uninitialized.");
        }
        return sInstance;
    }


    private EventDao open() throws SQLException {
        if (openHelper != null) {
            db = openHelper.getWritableDatabase();
        }
        return this;
    }

    private void close() {
        if (openHelper != null) {
            openHelper.close();
        }

    }

    public long createData(String title, String time, String content, String lat, String lon) {
        long result = -1;
        try {
            open();
            ContentValues data = new ContentValues();
            data.put(CL_TITLE, title);
            data.put(CL_TIME, time);
            data.put(CL_CONTENT, content);
            data.put(CL_LAT, lat);
            data.put(CL_LON, lon);

            result = db.insert(DB_TABLE, null, data);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteData(String[] id) {
        try {
            open();
            for (int i = 0; i < id.length; i++) {
                int po = db.delete(DB_TABLE, CL_ID + " = " + id[i], null);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TheEvent getEvent(int id) {
        TheEvent result = null;
        try {
            open();
            TheEvent theEvent = new TheEvent();
            Cursor c = db.rawQuery(" SELECT * FROM " + DB_TABLE + " WHERE " + CL_ID + " = " + id, null);
            c.moveToFirst();
            if (c != null) {
                theEvent.setId(c.getInt(0));
                theEvent.setTitle(c.getString(1));
                theEvent.setTime(c.getString(2));
                theEvent.setLocation(c.getString(3));
                LatLng mPosition = new LatLng(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
                theEvent.setPosition(mPosition);
                result = theEvent;
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void updateData(String title, String time, String content, int id) {
        try {
            open();
            ContentValues data = new ContentValues();
            data.put(CL_TITLE, title);
            data.put(CL_TIME, time);
            data.put(CL_CONTENT, content);
            db.update(DB_TABLE, data, CL_ID + "=?", new String[]{String.valueOf(id)});
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TheEvent> getAllEvents() {
        ArrayList<TheEvent> result = new ArrayList<>();
        try {
            open();
            Cursor c = db.query(DB_TABLE, null, null, null, null, null, null);
            c.moveToFirst();
            while (c.isAfterLast() == false) {
                TheEvent theEvent = new TheEvent();
                theEvent.setId(c.getInt(0));
                theEvent.setTitle(c.getString(1));
                theEvent.setTime(c.getString(2));
                theEvent.setLocation(c.getString(3));
                LatLng mPosition = new LatLng(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
                theEvent.setPosition(mPosition);
                result.add(theEvent);
                c.moveToNext();
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    private static class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DB_TABLE + "("
                            + CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + CL_TITLE + " TEXT, "
                            + CL_TIME + " TEXT, "
                            + CL_CONTENT + " TEXT, "
                            + CL_LAT + " TEXT, "
                            + CL_LON + " TEXT);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}
