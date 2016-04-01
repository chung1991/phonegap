package maddiscovery.greenwich.com.maddiscovery.Entity;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

import maddiscovery.greenwich.com.maddiscovery.Interfaces.iPlace;

/**
 * Created by Himura on 2016/03/16.
 */
public class TheEvent implements Serializable, iPlace {
    private int id;
    private String title;
    private String time;
    private String location;
    private boolean ischeck;
    private LatLng position;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public TheEvent() {
    }

    public TheEvent(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getDetail() {
        return title;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TheEvent(String title, String time, String content, String location) {
        this.title = title;
        this.time = time;
        this.location = content;
        this.ischeck = false;
    }
}
