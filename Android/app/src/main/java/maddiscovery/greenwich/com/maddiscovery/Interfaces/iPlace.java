package maddiscovery.greenwich.com.maddiscovery.Interfaces;

import com.google.android.gms.maps.model.LatLng;

public interface iPlace {
    int getId();

    String getTitle();

    String getDetail();

    LatLng getPosition();
}
