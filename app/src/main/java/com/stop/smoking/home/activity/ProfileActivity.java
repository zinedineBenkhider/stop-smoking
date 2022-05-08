package com.stop.smoking.home.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.stop.smoking.R;
import com.stop.smoking.home.repository.ProfileRepository;
import com.stop.smoking.home.repository.dataBase.entity.Profile;
import com.stop.smoking.home.util.Utility;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.sql.Time;
import java.util.Calendar;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {
    private ImageButton cigarettesPerDayIncrementBtn, cigarettesPerDayDecrementBtn, cigarettesPerPackIncrementBtn, cigarettesPerPackDecrementBtn, yearOfSmokingIncrementBtn, yearOfSmokingDecrementBtn;
    private EditText cigarettesPerDayEditText, cigarettesPerPackEditText, yearOfSmokingEditText, pricePerPackEditText, dateStoppingEditText, timeDepartureEditText;
    private MaterialBetterSpinner deviseSpinner;
    private TextInputLayout cigarettesPerDayWrapper, cigarettesPerPackWrapper, yearOfSmokingWrapper, pricePerPackWrapper, dateStoppingWrapper;
    private ProfileRepository mProfileReposistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        configureToolbar();
        this.mProfileReposistory = ProfileRepository.getInstance(getApplication());
        dateStoppingEditText = findViewById(R.id.date_stopping_edit_text);

        cigarettesPerDayEditText = findViewById(R.id.cigarette_per_day_edit_text);
        cigarettesPerPackEditText = findViewById(R.id.nb_cigarettes_per_pack_edit_text);
        yearOfSmokingEditText = findViewById(R.id.years_of_smoking_edit_text);
        pricePerPackEditText = findViewById(R.id.price_per_pack_edit_text);
        cigarettesPerDayWrapper = findViewById(R.id.cigarette_per_day_edit_text_wrapper);
        cigarettesPerPackWrapper = findViewById(R.id.nb_cigarettes_per_pack_edit_text_wrapper);
        yearOfSmokingWrapper = findViewById(R.id.years_of_smoking_edit_text_wrapper);
        pricePerPackWrapper = findViewById(R.id.price_per_pack_edit_text_wrapper);
        dateStoppingWrapper = findViewById(R.id.date_stopping_edit_text_wrapper);
        cigarettesPerDayIncrementBtn = findViewById(R.id.cigarette_smoked_increment_btn);
        cigarettesPerDayDecrementBtn = findViewById(R.id.cigarette_smoked_decrement_btn);
        cigarettesPerPackIncrementBtn = findViewById(R.id.nb_cigarettes_per_pack_increment_btn);
        cigarettesPerPackDecrementBtn = findViewById(R.id.nb_cigarettes_per_pack_decrement_btn);
        yearOfSmokingIncrementBtn = findViewById(R.id.years_of_smoking_increment_btn);
        yearOfSmokingDecrementBtn = findViewById(R.id.years_of_smoking_decrement_btn);
        deviseSpinner = findViewById(R.id.devis_spinner_profile);

        //Set Listeners
        setListenersToIncrementAndDecrementBtn();
        setErrorListenersToRequiredFields();
        AppCompatImageButton backBtn = findViewById(R.id.back_btn_tool_bar);
        backBtn.setOnClickListener(v -> finish());
        FloatingActionButton saveButton = findViewById(R.id.btn_save_profile);
        saveButton.setOnClickListener(v -> saveProfile());
        //Set data to devise spinner
        String[] devises = getResources().getStringArray(R.array.devises);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(ProfileActivity.this, android.R.layout.simple_dropdown_item_1line, devises);
        deviseSpinner.setAdapter(spinnerAdapter);
        this.getProfile();
        setEventsToFieldStoppingDate();
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolBarTitle = findViewById(R.id.title_tool_bar);
        toolBarTitle.setText(R.string.profile);
    }

    private void getProfile() {
        Profile profile = this.mProfileReposistory.getProfile();
        if(profile!=null){
            cigarettesPerPackEditText.setText(String.valueOf(profile.getCigarettesInPack()));
            yearOfSmokingEditText.setText(String.valueOf(profile.getYearsOfSmoking()));
            pricePerPackEditText.setText(String.valueOf(profile.getPricePerPack()));
            deviseSpinner.setText(String.valueOf(profile.getDevise()));
            cigarettesPerDayEditText.setText(String.valueOf(profile.getCigarettesPerDay()));
            dateStoppingEditText.setText(profile.getQuittingDate());
        }
    }

    private void saveProfile() {
        String cigarettesPerDay = cigarettesPerDayEditText.getText().toString();
        String cigarettesPerPack = cigarettesPerPackEditText.getText().toString();
        String yearOfSmoking = yearOfSmokingEditText.getText().toString();
        String pricePerPack = pricePerPackEditText.getText().toString();
        String devise = deviseSpinner.getText().toString();
        String stoppingDate = dateStoppingEditText.getText().toString();
        if (TextUtils.isEmpty(stoppingDate) || TextUtils.isEmpty(cigarettesPerDay) || TextUtils.isEmpty(cigarettesPerPack) || TextUtils.isEmpty(yearOfSmoking) || TextUtils.isEmpty(pricePerPack) || TextUtils.isEmpty(devise)) {
            setErrorMessageToRequiredFields(cigarettesPerDayEditText, cigarettesPerDayWrapper, !TextUtils.isEmpty(cigarettesPerDayEditText.getText()));
            setErrorMessageToRequiredFields(cigarettesPerPackEditText, cigarettesPerPackWrapper, !TextUtils.isEmpty(cigarettesPerPackEditText.getText()));
            setErrorMessageToRequiredFields(yearOfSmokingEditText, yearOfSmokingWrapper, !TextUtils.isEmpty(yearOfSmokingEditText.getText()));
            setErrorMessageToRequiredFields(pricePerPackEditText, pricePerPackWrapper, !TextUtils.isEmpty(pricePerPackEditText.getText()));
            setErrorMessageToRequiredFields(dateStoppingEditText, dateStoppingWrapper, !TextUtils.isEmpty(dateStoppingEditText.getText()));
            setErrorMessageToSpinner(deviseSpinner, !TextUtils.isEmpty(deviseSpinner.getText()));
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_required_fields_empty), Toast.LENGTH_LONG);
            toast.show();
        } else {
            Profile profile = this.mProfileReposistory.getProfile();
            if (profile == null) {
                profile = new Profile(UUID.randomUUID().toString(), stoppingDate, Integer.valueOf(cigarettesPerDay), Integer.valueOf(cigarettesPerPack), Integer.valueOf(yearOfSmoking), Integer.valueOf(pricePerPack), devise,0);
                this.mProfileReposistory.insertProfile(profile);
            } else {
                profile = new Profile(profile.getId(), stoppingDate, Integer.valueOf(cigarettesPerDay), Integer.valueOf(cigarettesPerPack), Integer.valueOf(yearOfSmoking), Integer.valueOf(pricePerPack), devise,profile.getMoneySpentOnRewards());
                this.mProfileReposistory.updateProfile(profile);
            }
            finish();
        }
    }

    private void changeValueOfEditText(EditText editText, TextInputLayout textInputLayout, boolean isIncrement) {
        editText.requestFocus();
        int newValue = 0;
        if (!editText.getText().toString().equals("")) {
            newValue = Integer.parseInt(editText.getText().toString());
            if (isIncrement) {
                newValue += 1;
            } else if (newValue > 0) {
                newValue -= 1;
            }
        }
        editText.setText(String.valueOf(newValue));
        setErrorMessageToRequiredFields(editText, textInputLayout, true);
    }

    private void setListenersToIncrementAndDecrementBtn() {
        cigarettesPerDayIncrementBtn.setOnClickListener(v -> changeValueOfEditText(cigarettesPerDayEditText, cigarettesPerDayWrapper, true));
        cigarettesPerDayDecrementBtn.setOnClickListener(v -> changeValueOfEditText(cigarettesPerDayEditText, cigarettesPerDayWrapper, false));
        cigarettesPerPackIncrementBtn.setOnClickListener(v -> changeValueOfEditText(cigarettesPerPackEditText, cigarettesPerPackWrapper, true));
        cigarettesPerPackDecrementBtn.setOnClickListener(v -> changeValueOfEditText(cigarettesPerPackEditText, cigarettesPerPackWrapper, false));
        yearOfSmokingIncrementBtn.setOnClickListener(v -> changeValueOfEditText(yearOfSmokingEditText, yearOfSmokingWrapper, true));
        yearOfSmokingDecrementBtn.setOnClickListener(v -> changeValueOfEditText(yearOfSmokingEditText, yearOfSmokingWrapper, false));
    }

    private void setErrorListenersToRequiredFields() {
        cigarettesPerDayEditText.setOnFocusChangeListener((view, b) -> setErrorMessageToRequiredFields(cigarettesPerDayEditText, cigarettesPerDayWrapper, b));
        cigarettesPerPackEditText.setOnFocusChangeListener((view, b) -> setErrorMessageToRequiredFields(cigarettesPerPackEditText, cigarettesPerPackWrapper, b));
        yearOfSmokingEditText.setOnFocusChangeListener((view, b) -> setErrorMessageToRequiredFields(yearOfSmokingEditText, yearOfSmokingWrapper, b));
        pricePerPackEditText.setOnFocusChangeListener((view, b) -> setErrorMessageToRequiredFields(pricePerPackEditText, pricePerPackWrapper, b));
        dateStoppingEditText.setOnFocusChangeListener((view, b) -> setErrorMessageToRequiredFields(dateStoppingEditText, dateStoppingWrapper, b));
        deviseSpinner.setOnFocusChangeListener((view, b) -> setErrorMessageToSpinner(deviseSpinner, b));
    }

    private void setErrorMessageToRequiredFields(EditText editText, TextInputLayout textInputLayout, boolean b) {
        if (!b && TextUtils.isEmpty(editText.getText())) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getResources().getString(R.string.field_required));
        } else {
            textInputLayout.setErrorEnabled(false);
        }
    }

    private void setErrorMessageToSpinner(MaterialBetterSpinner materialBetterSpinner, boolean b) {
        if (!b && TextUtils.isEmpty(materialBetterSpinner.getText())) {
            materialBetterSpinner.setError(getResources().getString(R.string.field_required));
        } else {
            materialBetterSpinner.setError(null);
        }
    }

    DatePickerDialog.OnDateSetListener onStoppingDateChangeLinstener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String dateStr=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;;
            if(!dateStoppingEditText.getText().toString().isEmpty()){
                dateStr=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year+" "+dateStoppingEditText.getText().toString().split(" ")[1];
            }

            dateStoppingEditText.setText(dateStr);
            showTime(onTimeStoppingSetListener, dateStoppingEditText);
        }
    };

    private void showCalender(final EditText editText, DatePickerDialog.OnDateSetListener onDateSetListener) {
        DatePickerDialog picker;
        int day;
        int month;
        int year;
        String dateStr="";
        if(!editText.getText().toString().isEmpty()){
            dateStr=editText.getText().toString().split(" ")[0];
        }
        int[] date = Utility.parseDate(dateStr);
        day = date[0];
        month = date[1];
        year = date[2];
        picker = new DatePickerDialog(ProfileActivity.this, R.style.DialogDateTheme, onDateSetListener, year, month, day);
        picker.show();
    }

    private void setEventsToFieldStoppingDate() {
        dateStoppingEditText.setInputType(InputType.TYPE_NULL);
        dateStoppingEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                showCalender(dateStoppingEditText, onStoppingDateChangeLinstener);
            }
        });
        dateStoppingEditText.setOnClickListener(view -> showCalender(dateStoppingEditText, onStoppingDateChangeLinstener));
    }

    private void showTime(TimePickerDialog.OnTimeSetListener listener, EditText editText) {
        TimePickerDialog timePicker;
        if (editText.getText().toString().split(" ").length==1) {
            timePicker = new TimePickerDialog(ProfileActivity.this, listener, 0, 0, true);
        } else {
            Time time = Time.valueOf(editText.getText().toString().split(" ")[1] + ":00");
            timePicker = new TimePickerDialog(ProfileActivity.this, listener, time.getHours(), time.getMinutes(), true);
        }
        timePicker.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeStoppingSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String dateTimeStr=dateStoppingEditText.getText().toString().split(" ")[0]+ " "+Utility.getFormatTime(hourOfDay, minute);
            dateStoppingEditText.setText(dateTimeStr);
        }
    };

}