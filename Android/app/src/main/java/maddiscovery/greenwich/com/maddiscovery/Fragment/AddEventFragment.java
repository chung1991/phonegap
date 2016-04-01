package maddiscovery.greenwich.com.maddiscovery.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import maddiscovery.greenwich.com.maddiscovery.Dao.EventDao;
import maddiscovery.greenwich.com.maddiscovery.R;

/**
 * Created by chung1991 on 4/1/16.
 */
public class AddEventFragment extends Fragment implements View.OnClickListener {
    private LatLng edtPosition;
    private EditText edtTitle;
    private View addView;
    private EditText edtTime;
    private EditText edtDate;
    private EditText edtLocation;
    private Button okButton;

    private static final int PLACE_PICKER_REQUEST = 1;
    private LatLng currentLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        this.addView = inflater.inflate(R.layout.add_event, viewContainer, false);

        this.edtTitle = (EditText) this.addView.findViewById(R.id.edtTitle);
        this.edtTime = (EditText) this.addView.findViewById(R.id.edtTime);
        this.edtTime.setInputType(InputType.TYPE_NULL);
        this.edtTime.setOnClickListener(this);
        this.edtDate = (EditText) this.addView.findViewById(R.id.edtDate);
        this.edtDate.setInputType(InputType.TYPE_NULL);
        this.edtDate.setOnClickListener(this);
        this.edtLocation = (EditText) this.addView.findViewById(R.id.edtLocation);
        this.edtLocation.setInputType(InputType.TYPE_NULL);
        this.edtLocation.setOnClickListener(this);
        this.okButton = (Button) this.addView.findViewById(R.id.okButton);
        this.okButton.setOnClickListener(this);

        return this.addView;
    }

    @Override
    public void onClick(View view) {
        if (view == this.okButton) {
            this.onOkButtonClicked();
        } else if (view == this.edtLocation) {
            try {
                getLocation();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            }
        } else if (view == this.edtDate) {
            hideSoftKeyboard();
            getActivity().showDialog(0);
        } else if (view == this.edtTime) {
            hideSoftKeyboard();
            getActivity().showDialog(-1);
        }
    }

    private void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    private void getLocation() throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                edtLocation.setText(place.getAddress());
                edtPosition = place.getLatLng();
                currentLocation = place.getLatLng();
            }
        }
    }

    private void onOkButtonClicked() {
        String title = edtTitle.getText().toString();
        String time = edtTime.getText().toString();
        String date = edtDate.getText().toString();
        String total = time +" ; " +date;
        String location = edtLocation.getText().toString();
        EventDao.getInstance().createData(title, total, location, String.valueOf(currentLocation.latitude), String.valueOf(currentLocation.longitude));

        getActivity().finish();
    }
}
