package ehsanfatahizadehgmail.com.jinni;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.libs.BaseActivity;
import ehsanfatahizadehgmail.com.jinni.libs.PinEntryEditText;

public class TellActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell);


        final EditText edt_mobile = (EditText) findViewById(R.id.editText_mobile_tell);
        Button btn_tell = (Button) findViewById(R.id.button_tell_activity);
        final PinEntryEditText txtPinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        final TextView tw_timer = (TextView)findViewById(R.id.code_timer);
        final ConstraintLayout constrain_code = (ConstraintLayout) findViewById(R.id.constrain_verify);
        final ConstraintLayout constrain_mobile = (ConstraintLayout) findViewById(R.id.constrain_mob);

        btn_tell.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {


                String mob = edt_mobile.getText().toString().trim();
                if (!mob.equals("")){
                    Intent intent = new Intent(TellActivity.this , HomeActivity.class);
                    SharedPreferences.Editor sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE).edit();
                    sh.putString("mobile" , mob);
                    sh.commit();
                    startActivity(intent);
                }



//                constrain_mobile.setVisibility(View.GONE);
//                constrain_code.setVisibility(View.VISIBLE);
//
//                new CountDownTimer(100000, 1000) {
//
//                        public void onTick(long millisUntilFinished) {
//                            tw_timer.setText("ارسال مجدد کد تا "+millisUntilFinished / 1000 + " ثانیه ی دیگر ");
//                            //here you can have your logic to set text to edittext
//                        }
//
//                        public void onFinish() {
//                            tw_timer.setText("دریافت مجدد کد");
//                        }
//
//                    }.start();


            }
        });



        txtPinEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == "1234".length()) {
                    Toast.makeText(TellActivity.this, "Succes", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tw_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (tw_timer.getText().toString().equals("دریافت مجدد کد")){
////                            new send_mobile(mobile).execute();
//                    new CountDownTimer(100000, 1000) {
//
//                        public void onTick(long millisUntilFinished) {
//                            tw_timer.setText("ارسال مجدد کد تا "+millisUntilFinished / 1000 + " ثانیه ی دیگر ");
//                            //here you can have your logic to set text to edittext
//                        }
//
//                        public void onFinish() {
//                            tw_timer.setText("دریافت مجدد کد");
//                        }
//
//                    }.start();
//                }

//                startActivity(new Intent(TellActivity.this , HomeActivity.class));


            }
        });






    }
}
