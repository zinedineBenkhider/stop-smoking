package com.stop.smoking.home.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.stop.smoking.R;
import com.stop.smoking.home.repository.ProfileRepository;
import com.stop.smoking.home.repository.dataBase.entity.Profile;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {
    private ImageButton cigarettesPerDayIncrementBtn,cigarettesPerDayDecrementBtn,cigarettesPerPackIncrementBtn, cigarettesPerPackDecrementBtn, yearOfSmokingIncrementBtn,yearOfSmokingDecrementBtn;
    private EditText cigarettesPerDayEditText,cigarettesPerPackEditText,yearOfSmokingEditText,pricePerPackEditText;
    private MaterialBetterSpinner deviseSpinner;
    private TextInputLayout cigarettesPerDayWrapper,cigarettesPerPackWrapper,yearOfSmokingWrapper,pricePerPackWrapper ;
    private ProfileRepository mProfileReposistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        configureToolbar();
        this.mProfileReposistory=ProfileRepository.getInstance(getApplication());
        cigarettesPerDayEditText=findViewById(R.id.cigarette_per_day_edit_text);
        cigarettesPerPackEditText=findViewById(R.id.nb_cigarettes_per_pack_edit_text);
        yearOfSmokingEditText=findViewById(R.id.years_of_smoking_edit_text);
        pricePerPackEditText=findViewById(R.id.price_per_pack_edit_text);
        cigarettesPerDayWrapper=findViewById(R.id.cigarette_per_day_edit_text_wrapper);
        cigarettesPerPackWrapper=findViewById(R.id.nb_cigarettes_per_pack_edit_text_wrapper);
        yearOfSmokingWrapper=findViewById(R.id.years_of_smoking_edit_text_wrapper);
        pricePerPackWrapper=findViewById(R.id.price_per_pack_edit_text_wrapper);

        cigarettesPerDayIncrementBtn=findViewById(R.id.cigarette_smoked_increment_btn);
        cigarettesPerDayDecrementBtn=findViewById(R.id.cigarette_smoked_decrement_btn);
        cigarettesPerPackIncrementBtn=findViewById(R.id.nb_cigarettes_per_pack_increment_btn);
        cigarettesPerPackDecrementBtn=findViewById(R.id.nb_cigarettes_per_pack_decrement_btn);
        yearOfSmokingIncrementBtn=findViewById(R.id.years_of_smoking_increment_btn);
        yearOfSmokingDecrementBtn=findViewById(R.id.years_of_smoking_decrement_btn);

        deviseSpinner = findViewById(R.id.devis_spinner_profile);
        //Set Listeners
        setListenersToIncrementAndDecrementBtn();
        setErrorListenersToRequiredFields();
        AppCompatImageButton backBtn = findViewById(R.id.back_btn_tool_bar);
        backBtn.setOnClickListener(v -> finish());
        FloatingActionButton saveButton = findViewById(R.id.btn_save_profile);
        saveButton.setOnClickListener(v->saveProfile());
        //Set data to devise spinner
        String[] devises=getResources().getStringArray(R.array.devises);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(ProfileActivity.this, android.R.layout.simple_dropdown_item_1line, devises);
        deviseSpinner.setAdapter(spinnerAdapter);
        this.getProfile();
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolBarTitle = findViewById(R.id.title_tool_bar);
        toolBarTitle.setText(R.string.profile);
    }

    private void getProfile(){
        Profile profile= this.mProfileReposistory.getProfile();
        System.out.println(profile.getCigarettesInPack());
        System.out.println(profile.getDevise());
        if(profile!=null){

            cigarettesPerPackEditText.setText(String.valueOf(profile.getCigarettesInPack()));
            yearOfSmokingEditText.setText(String.valueOf(profile.getYearsOfSmoking()));
            pricePerPackEditText.setText(String.valueOf(profile.getPricePerPack()));
            deviseSpinner.setText(String.valueOf(profile.getDevise()));
            cigarettesPerDayEditText.setText(String.valueOf(profile.getCigarettesPerDay()));
        }
    }

    private void saveProfile(){
        String cigarettesPerDay=cigarettesPerDayEditText.getText().toString();
        String cigarettesPerPack=cigarettesPerPackEditText.getText().toString();
        String yearOfSmoking=yearOfSmokingEditText.getText().toString();
        String pricePerPack=pricePerPackEditText.getText().toString();
        String devise=deviseSpinner.getText().toString();
        if(TextUtils.isEmpty(cigarettesPerDay)|| TextUtils.isEmpty(cigarettesPerPack)|| TextUtils.isEmpty(yearOfSmoking)|| TextUtils.isEmpty(pricePerPack)|| TextUtils.isEmpty(devise)){
            setErrorMessageToRequiredFields(cigarettesPerDayEditText,cigarettesPerDayWrapper,!TextUtils.isEmpty(cigarettesPerDayEditText.getText()));
            setErrorMessageToRequiredFields(cigarettesPerPackEditText,cigarettesPerPackWrapper,!TextUtils.isEmpty(cigarettesPerPackEditText.getText()));
            setErrorMessageToRequiredFields(yearOfSmokingEditText,yearOfSmokingWrapper,!TextUtils.isEmpty(yearOfSmokingEditText.getText()));
            setErrorMessageToRequiredFields(pricePerPackEditText,pricePerPackWrapper,!TextUtils.isEmpty(pricePerPackEditText.getText()));
            setErrorMessageToSpinner(deviseSpinner,!TextUtils.isEmpty(deviseSpinner.getText()));
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_required_fields_empty), Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            Profile profile=this.mProfileReposistory.getProfile();
            if(profile==null){
                profile=new Profile(UUID.randomUUID().toString(),"22-04-2022 12:02:05",Integer.valueOf(cigarettesPerDay),Integer.valueOf(cigarettesPerPack),Integer.valueOf(yearOfSmoking),Integer.valueOf(pricePerPack),devise);
                this.mProfileReposistory.insertProfile(profile);
            }
            else{
                profile=new Profile(profile.getId(),"22-04-2022 12:02:05",Integer.valueOf(cigarettesPerDay),Integer.valueOf(cigarettesPerPack),Integer.valueOf(yearOfSmoking),Integer.valueOf(pricePerPack),devise);
                this.mProfileReposistory.updateProfile(profile);
            }
            finish();
        }
    }

    private void changeValueOfEditText(EditText editText,TextInputLayout textInputLayout,boolean isIncrement){
        editText.requestFocus();
        int newValue=0;
        if(!editText.getText().toString().equals("")){
            newValue=Integer.parseInt(editText.getText().toString());
            if(isIncrement){
                newValue+=1;
            }
            else if (newValue>0){
                newValue-=1;
            }
        }
        editText.setText(String.valueOf(newValue));
        setErrorMessageToRequiredFields(editText,textInputLayout,true);
    }

    private void setListenersToIncrementAndDecrementBtn(){
        cigarettesPerDayIncrementBtn.setOnClickListener(v-> changeValueOfEditText(cigarettesPerDayEditText,cigarettesPerDayWrapper,true));
        cigarettesPerDayDecrementBtn.setOnClickListener(v-> changeValueOfEditText(cigarettesPerDayEditText,cigarettesPerDayWrapper,false));
        cigarettesPerPackIncrementBtn.setOnClickListener(v-> changeValueOfEditText(cigarettesPerPackEditText,cigarettesPerPackWrapper,true));
        cigarettesPerPackDecrementBtn.setOnClickListener(v-> changeValueOfEditText(cigarettesPerPackEditText,cigarettesPerPackWrapper,false));
        yearOfSmokingIncrementBtn.setOnClickListener(v-> changeValueOfEditText(yearOfSmokingEditText,yearOfSmokingWrapper,true));
        yearOfSmokingDecrementBtn.setOnClickListener(v-> changeValueOfEditText(yearOfSmokingEditText,yearOfSmokingWrapper,false));
    }

    private void setErrorListenersToRequiredFields(){
        cigarettesPerDayEditText.setOnFocusChangeListener((view, b) -> setErrorMessageToRequiredFields(cigarettesPerDayEditText,cigarettesPerDayWrapper,b));
        cigarettesPerPackEditText.setOnFocusChangeListener((view, b) -> setErrorMessageToRequiredFields(cigarettesPerPackEditText,cigarettesPerPackWrapper,b));
        yearOfSmokingEditText.setOnFocusChangeListener((view, b) -> setErrorMessageToRequiredFields(yearOfSmokingEditText,yearOfSmokingWrapper,b));
        pricePerPackEditText.setOnFocusChangeListener((view, b) -> setErrorMessageToRequiredFields(pricePerPackEditText,pricePerPackWrapper,b));
        deviseSpinner.setOnFocusChangeListener((view, b) -> setErrorMessageToSpinner(deviseSpinner,b));

    }

    private void setErrorMessageToRequiredFields(EditText editText,TextInputLayout textInputLayout,boolean b){
        if (!b && TextUtils.isEmpty(editText.getText())) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getResources().getString(R.string.field_required));
        }
        else{
            textInputLayout.setErrorEnabled(false);
        }
    }

    private void setErrorMessageToSpinner(MaterialBetterSpinner materialBetterSpinner,boolean b){
        if (!b && TextUtils.isEmpty(materialBetterSpinner.getText())) {
            materialBetterSpinner.setError(getResources().getString(R.string.field_required));
        }
        else{
            materialBetterSpinner.setError(null);
        }
    }
}