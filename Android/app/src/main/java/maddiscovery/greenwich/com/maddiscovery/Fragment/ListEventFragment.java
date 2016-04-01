package maddiscovery.greenwich.com.maddiscovery.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import maddiscovery.greenwich.com.maddiscovery.Adapter.EventAdapter;
import maddiscovery.greenwich.com.maddiscovery.AddEventActivity;
import maddiscovery.greenwich.com.maddiscovery.Dao.EventDao;
import maddiscovery.greenwich.com.maddiscovery.R;

/**
 * Created by chung1991 on 4/1/16.
 */
public class ListEventFragment extends Fragment {
    public static final int ADD_RESULT = 1;
    private View currentView;
    private ListView listView;
    EventAdapter adapter;

    @Override
    public  View getView() {
        return currentView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        this.currentView = inflater.inflate(R.layout.list_event_layout, viewContainer, false);

        FloatingActionButton floatButton = (FloatingActionButton) this.currentView.findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddEventActivity();
            }
        });

        this.listView = (ListView) this.currentView.findViewById(R.id.lvList);

        return this.currentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.adapter = new EventAdapter(getActivity(), EventDao.getInstance().getAllEvents());
        this.listView.setAdapter(adapter);
    }

    private void goToAddEventActivity() {
        Intent intent = new Intent(getActivity(), AddEventActivity.class);
        startActivityForResult(intent, ADD_RESULT);
    }
}
