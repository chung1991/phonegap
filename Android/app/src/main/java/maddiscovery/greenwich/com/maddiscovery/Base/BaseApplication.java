package maddiscovery.greenwich.com.maddiscovery.Base;

import android.app.Application;
import maddiscovery.greenwich.com.maddiscovery.Dao.EventDao;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EventDao.init(this);
    }
}
