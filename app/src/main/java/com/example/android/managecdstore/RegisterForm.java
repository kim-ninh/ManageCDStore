package com.example.android.managecdstore;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterForm extends AppCompatActivity /*implements DatePickerDialog.OnDateSetListener*/
{

    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtRetrype;
    private EditText txtBirthdate;
    private Button btnSignUp;
    private Button btnSelect;
    private Button btnReset;
    private RadioGroup rdoGender;
    private CheckBox chkTennis;
    private CheckBox chkFootball;
    private CheckBox chkOthers;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtRetrype = (EditText) findViewById(R.id.txtRetype);
        txtBirthdate = (EditText) findViewById(R.id.txtBirthdate);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnReset = (Button) findViewById(R.id.btnReset);
        rdoGender = (RadioGroup) findViewById(R.id.rdoGender);
        chkTennis = (CheckBox) findViewById(R.id.chkTennis);
        chkFootball = (CheckBox) findViewById(R.id.chkFootball);
        chkOthers = (CheckBox) findViewById(R.id.chkOthers);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*DialogFragment datePicker = new DialogFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");*/
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterForm.this,
                        R.style.Theme_AppCompat_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            final String TAG = "RegisterForm";
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Log.d(TAG, "On DateSet Date: " + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                txtBirthdate.setText(date);
            }
        };

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flat = true;
                if(!IsDuplicate(txtPassword.getText().toString(), txtRetrype.getText().toString()))
                {
                    Toast.makeText(RegisterForm.this, "Retype is not match!", Toast.LENGTH_SHORT).show();
                    flat = false;
                }
                if(!IsValidDate(txtBirthdate.getText().toString()))
                {
                    Toast.makeText(RegisterForm.this, "Birthdate is unvalid", Toast.LENGTH_SHORT).show();
                    flat = false;
                }
                if(flat)
                {
                    Toast.makeText(RegisterForm.this,"User: " + txtUsername.getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterForm.this, "Password: " + txtPassword.getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterForm.this, "Birthdate: " + txtBirthdate.getText().toString(), Toast.LENGTH_SHORT).show();
                    int idCheck = rdoGender.getCheckedRadioButtonId();
                    switch (idCheck)
                    {
                        case R.id.rdomale:
                            Toast.makeText(RegisterForm.this, "Gender: Male", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.rdofemale:
                            Toast.makeText(RegisterForm.this, "Gender: Female", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    String s = "";

                    if(chkTennis.isChecked())
                        s += "Tennis";
                    if(chkFootball.isChecked())
                        s += " Football";
                    if(chkOthers.isChecked())
                        s += " Others";
                    if(!s.equals(""))
                        Toast.makeText(RegisterForm.this,"Hobbies" + s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUsername.setText("");
                txtPassword.setText("");
                txtRetrype.setText("");
                txtBirthdate.setText("");
                rdoGender.clearCheck();
                chkTennis.setChecked(false);
                chkFootball.setChecked(false);
                chkOthers.setChecked(false);
            }
        });
    }

    /*@Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());

        txtBirthdate.setText(currentDateString);
    }*/

    private boolean IsDuplicate(String pass, String retrype)
    {
        if(!(pass.equals(retrype)))
        {
            return false;
        }
        return true;
            //Toast.makeText(this, "Retrype are not duplicate!", Toast.LENGTH_SHORT).show();
    }
    private boolean IsValidDate(String inDate)
    {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

        //turn off lenient
        dateFormat.setLenient(false);

        try{
            dateFormat.parse(inDate.trim());
        } catch (ParseException e) {
            return false;
           // Toast.makeText(RegisterForm.this, "Birthdate is unvalid", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
