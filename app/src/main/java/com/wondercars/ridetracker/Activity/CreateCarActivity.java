package com.wondercars.ridetracker.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.text.TextUtils.isEmpty;

public class CreateCarActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_car_name)
    EditText etCarName;
    @BindView(R.id.spinner_select_variant)
    Spinner spinnerSelectVariant;
    @BindView(R.id.et_car_number)
    EditText etCarNumber;
    @BindView(R.id.button_create)
    Button buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_car);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_create:
                break;
        }
    }


    private boolean validateFields() {

        if (isEmpty(etCarName.getText().toString())) {
            showLongToast("Please enter car name");
        }
        if (isEmpty((etCarNumber.getText().toString()))) {
            showLongToast("Please enter car number");
        }
        return true;
    }
}
