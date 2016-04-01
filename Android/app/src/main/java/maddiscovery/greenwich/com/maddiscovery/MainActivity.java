package maddiscovery.greenwich.com.maddiscovery;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import maddiscovery.greenwich.com.maddiscovery.Fragment.ListEventFragment;

public class MainActivity extends AppCompatActivity {
    Fragment listEventFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initialize();
    }

    private void initialize() {
        this.createLayers();
    }

    private void createLayers() {
        RelativeLayout mainContainer = (RelativeLayout) findViewById(R.id.mainContainer);
        int mainContainerId = mainContainer.getId();
        this.listEventFragment = new ListEventFragment();
        getSupportFragmentManager().beginTransaction().add(mainContainerId, this.listEventFragment, "tag").commit();
    }
}
