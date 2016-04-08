package maddiscovery.greenwich.com.maddiscovery;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import maddiscovery.greenwich.com.maddiscovery.Dao.EventDao;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {
    private LatLng edtPosition;
    private EditText edtTitle;
    private View addView;
    private EditText edtTime;
    private EditText edtDate;
    private EditText edtLocation;
    private Button okButton;
    private EditText editOrganizer;

    private static final int PLACE_PICKER_REQUEST = 1;
    private LatLng currentLocation;

    private int min, hour, day, month, year, id;

 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        this.edtTitle = (EditText) findViewById(R.id.editTitle);
        this.editOrganizer = (EditText) findViewById(R.id.editOrg);
        this.edtTime = (EditText) findViewById(R.id.editTime);
        this.edtTime.setInputType(InputType.TYPE_NULL);
        this.edtTime.setOnClickListener(this);
        this.edtDate = (EditText) findViewById(R.id.editDate);
        this.edtDate.setInputType(InputType.TYPE_NULL);
        this.edtDate.setOnClickListener(this);
        this.edtLocation = (EditText) findViewById(R.id.editLocation);
        this.edtLocation.setInputType(InputType.TYPE_NULL);
        this.edtLocation.setOnClickListener(this);
        this.okButton = (Button) findViewById(R.id.okButton);
        this.okButton.setOnClickListener(this);

        this.setTime();

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
            showDialog(0);
        } else if (view == this.edtTime) {
            hideSoftKeyboard();
            this.showDialog(-1);
        }
    }

    private void setTime() {
        Calendar calendar = Calendar.getInstance();
        min = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
    }


    private void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    private void getLocation() throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
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
        String org = editOrganizer.getText().toString();
        EventDao.getInstance().createData(title, total, location, org, String.valueOf(currentLocation.latitude), String.valueOf(currentLocation.longitude));

        this.finish();
    }

    private String getStringformTime(int arg1, int arg2, int arg3) {
        String Sarg1 = String.valueOf(arg1);
        String Sarg2 = String.valueOf(arg2);
        String Sarg3 = String.valueOf(arg3);
        if (arg3 == -1) {
            if (arg1 < 10) {
                Sarg1 = "0" + String.valueOf(arg1);
            }
            if (arg2 < 10) {
                Sarg2 = "0" + String.valueOf(arg2);
            }
            return Sarg1 + " : " + Sarg2;
        } else {
            if (arg1 < 10) {
                Sarg1 = "0" + String.valueOf(arg1);
            }
            if (arg2 < 10) {
                Sarg2 = "0" + String.valueOf(arg2);
            }
            return Sarg1 + " / " + Sarg2 + " / " + Sarg3;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 0) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        } else {
            return new TimePickerDialog(this, myTimeListener, hour, min, false);
        }
    }


    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = getStringformTime(dayOfMonth, monthOfYear, year);
            edtDate.setText(date);
        }
    };
    private TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = getStringformTime(hourOfDay, minute, -1);
            edtTime.setText(time);

        }
    };
}
