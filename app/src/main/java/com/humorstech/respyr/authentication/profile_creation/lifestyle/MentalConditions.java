package com.humorstech.respyr.authentication.profile_creation.lifestyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.utills.ProfileCreationData;
import java.util.ArrayList;


public class MentalConditions extends AppCompatActivity {

    private CheckBox chkNone, chk1, chk2, chk3, chk4, chk5, chk6, chk7, chk8;
    private ArrayList<String> conditions = new ArrayList<>();

    private Button buttonNext, buttonBack;
    private StringBuffer stringBuffer = new StringBuffer();

    private String mentalConditions;
    private boolean isSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_conditions);
        StatusBarColor statusBarColor = new StatusBarColor(MentalConditions.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        initVars();
        checkBoxes();
        onClicks();
    }

    private void initVars() {
        chkNone = findViewById(R.id.check_box_none);
        chk1 = findViewById(R.id.chk1);
        chk2 = findViewById(R.id.chk2);
        chk3 = findViewById(R.id.chk3);
        chk4 = findViewById(R.id.chk4);
        chk5 = findViewById(R.id.chk5);
        chk6 = findViewById(R.id.chk6);
        chk7 = findViewById(R.id.chk7);
        chk8 = findViewById(R.id.chk8);

        buttonNext = findViewById(R.id.button_next);
        buttonBack = findViewById(R.id.button_back);

        // dots
        LinearLayout linearLayout = findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(), linearLayout, 5, 5);
    }

    private void onClicks() {
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringBuffer.setLength(0);


                for (int i = 0; i < conditions.size(); i++) {

                    stringBuffer.append(conditions.get(i));
                    if (i != conditions.size() - 1) {
                        stringBuffer.append(",");
                    }
                }

                if (stringBuffer.length() == 0) {
                    mentalConditions = "None";
                } else {
                    mentalConditions = stringBuffer.toString();
                }

                if (isSelected) {

                    // store gender id for profile creation
                    SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ProfileCreationData.MENTAL_CONDITIONS, String.valueOf(mentalConditions));
                    editor.apply();


                    Intent intent = new Intent(getApplicationContext(), LifeStyleScore.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MentalConditions.this, "Please select any one of the following", Toast.LENGTH_SHORT).show();
                }


            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void checkBoxes() {
        chkNone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    chk1.setChecked(false);
                    chk2.setChecked(false);
                    chk3.setChecked(false);
                    chk4.setChecked(false);
                    chk5.setChecked(false);
                    chk6.setChecked(false);
                    chk7.setChecked(false);
                    chk8.setChecked(false);

                    conditions.remove("Anxiety/Stress");
                    conditions.remove("Depression");
                    conditions.remove("Post-Traumatic Stress Disorder (PTSD)");
                    conditions.remove("Schizophrenia");
                    conditions.remove("Eating Disorders");
                    conditions.remove("Disruptive behaviour and dissocial disorders");
                    conditions.remove("Autism spectrum");
                    conditions.remove("Attention deficit hyperactivity disorder (ADHD)");
                    isSelected = true;

                } else {

                    isSelected = false;
                }
            }
        });

        chk1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkNone.setChecked(false);
                    conditions.add("Anxiety/Stress");
                    isSelected = true;
                } else {
                    conditions.remove("Anxiety/Stress");
                    isSelected = false;
                }
            }
        });

        chk2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkNone.setChecked(false);
                    conditions.add("Depression");
                    isSelected = true;
                } else {
                    conditions.remove("Depression");
                    isSelected = false;
                }
            }
        });
        chk3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkNone.setChecked(false);
                    conditions.add("Post-Traumatic Stress Disorder (PTSD)");
                    isSelected = true;
                } else {
                    conditions.remove("Post-Traumatic Stress Disorder (PTSD)");
                    isSelected = false;
                }
            }
        });

        chk4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    chkNone.setChecked(false);
                    conditions.add("Schizophrenia");
                    isSelected = true;
                } else {
                    conditions.remove("Schizophrenia");
                    isSelected = false;
                }
            }
        });

        chk5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkNone.setChecked(false);
                    conditions.add("Eating Disorders");
                    isSelected = true;
                } else {


                    conditions.remove("Eating Disorders");
                    isSelected = false;
                }
            }
        });

        chk6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    chkNone.setChecked(false);
                    conditions.add("Disruptive behaviour and dissocial disorders");
                    isSelected = true;

                } else {

                    conditions.remove("Disruptive behaviour and dissocial disorders");
                    isSelected = false;
                }
            }
        });

        chk7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkNone.setChecked(false);
                    conditions.add("Autism spectrum");
                    isSelected = true;
                } else {

                    conditions.remove("Autism spectrum");
                    isSelected = false;
                }
            }
        });

        chk8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkNone.setChecked(false);
                    conditions.add("Attention deficit hyperactivity disorder (ADHD)");
                    isSelected = true;
                } else {
                    conditions.remove("Attention deficit hyperactivity disorder (ADHD)");
                    isSelected = false;
                }
            }
        });


    }


}