package ehsanfatahizadehgmail.com.jinni;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.libs.BaseActivity;
import ehsanfatahizadehgmail.com.jinni.libs.PinEntryEditText;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;

public class TellActivity extends BaseActivity {



    ApiInterface apis;
    RetrofitSetting retrofit;
    ProgressDialog dialog;


    ConstraintLayout constrain_code , constrain_mobile;
    TextView tw_timer;
    String mob;
    String verify_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell);


        final EditText edt_mobile = (EditText) findViewById(R.id.editText_mobile_tell);
        Button btn_tell = (Button) findViewById(R.id.button_tell_activity);
        Button btn_send_code = (Button) findViewById(R.id.button_send_code);
        final PinEntryEditText txtPinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        tw_timer = (TextView)findViewById(R.id.code_timer);
        constrain_code = (ConstraintLayout) findViewById(R.id.constrain_verify);
        constrain_mobile = (ConstraintLayout) findViewById(R.id.constrain_mob);





        dialog = new ProgressDialog(TellActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");



        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();



        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,null);
        final TextView text = (TextView) layout.findViewById(R.id.text);
        LinearLayout lin = (LinearLayout) layout.findViewById(R.id.toast_layout_root);
        lin.setBackgroundResource(R.drawable.back_toast_red);



        btn_tell.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {


                mob = edt_mobile.getText().toString().trim();
//                if (!mob.equals("")){
//                    Intent intent = new Intent(TellActivity.this , HomeActivity.class);
//                    SharedPreferences.Editor sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE).edit();
//                    sh.putString("mobile" , mob);
//                    sh.commit();
//                    startActivity(intent);
//                }





                if (mob.equals("")){
                    text.setText("موبایل خود را وارد کنید!");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }else if (mob.length() != 11){
                    text.setText("تعداد رقم ها اشتباه است!");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }else if (!mob.matches("09[0-9]{9}")){
                    text.setText("معتبر نیست");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }else
                    new thread_send_mobile(mob).execute();


            }
        });


        btn_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify_code = txtPinEntry.getText().toString().trim();
//                if (!mob.equals("")){
//                    Intent intent = new Intent(TellActivity.this , HomeActivity.class);
//                    SharedPreferences.Editor sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE).edit();
//                    sh.putString("mobile" , mob);
//                    sh.commit();
//                    startActivity(intent);
//                }
                if (verify_code.equals("")){
                    text.setText("کد را وارد کنید!");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }else if (verify_code.length() != 4){
                    text.setText("تعداد رقم ها اشتباه است!");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }else if (!verify_code.matches("[0-9]{4}")){
                    text.setText("معتبر نیست");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }else
                    new thread_send_code(verify_code , mob).execute();
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
//                if (s.length() == "1234".length()) {
////                    Toast.makeText(TellActivity.this, "Succes", Toast.LENGTH_SHORT).show();
//                }
            }
        });


        tw_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (tw_timer.getText().toString().equals("دریافت مجدد کد")){
                    new thread_send_mobile(mob).execute();
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
                }

//                startActivity(new Intent(TellActivity.this , HomeActivity.class));


            }
        });



    }








    public class thread_send_mobile extends AsyncTask {

        String mobile;
        public thread_send_mobile(String mobile){
            this.mobile = mobile;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        String result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<String> call = apis.sendTell(mobile);

            try {
                Response<String> response = call.execute();

                if(!response.isSuccessful()) {
                    return null;
                }
                result = response.body();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            dialog.dismiss();

            if (result != null) {
                if (result.equals("1")) {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, null);
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    LinearLayout lin = (LinearLayout) layout.findViewById(R.id.toast_layout_root);
                    lin.setBackgroundResource(R.drawable.back_toast_green);
                    text.setText("کد برای شما ارسال شد");

                    Toast toast = new Toast(getApplicationContext());
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                    constrain_mobile.setVisibility(View.GONE);
                    constrain_code.setVisibility(View.VISIBLE);

                    new CountDownTimer(100000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            tw_timer.setText("ارسال مجدد کد تا " + millisUntilFinished / 1000 + " ثانیه ی دیگر ");
                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            tw_timer.setText("دریافت مجدد کد");

                        }

                    }.start();
                }
            }else {
                Toast.makeText(TellActivity.this, "مشکل در ارتباط با سرور \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }






    String result_code;
    public class thread_send_code extends AsyncTask {

        String code;
        String mobile;
        public thread_send_code(String code , String mobile){
            this.code = code;
            this.mobile = mobile;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        String result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<String> call = apis.sendVerifyCode(mobile , code);

            try {
                Response<String> response = call.execute();

                if(!response.isSuccessful()) {

                    return null;
                }
                result_code = response.body();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }





        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            dialog.dismiss();

            if (result_code != null) {
                if (result_code.equals("1")) {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, null);
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    LinearLayout lin = (LinearLayout) layout.findViewById(R.id.toast_layout_root);
                    lin.setBackgroundResource(R.drawable.back_toast_green);
                    text.setText("ورود موفقیت آمیز");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                    Intent intent = new Intent(TellActivity.this, HomeActivity.class);
                    SharedPreferences.Editor sh = getSharedPreferences(Constants.SHARED_USER, MODE_PRIVATE).edit();
                    sh.putString("mobile", mob);
                    sh.commit();
                    startActivity(intent);
                } else if (result_code.equals("2")) {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, null);
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    LinearLayout lin = (LinearLayout) layout.findViewById(R.id.toast_layout_root);
                    lin.setBackgroundResource(R.drawable.back_toast_red);
                    text.setText("کد اشتباه است!");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            } else {
                Toast.makeText(TellActivity.this, "ارتباط با سرور برقرار نشد!", Toast.LENGTH_SHORT).show();
            }
        }

    }







}
