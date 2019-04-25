package ehsanfatahizadehgmail.com.jinni;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.adapters.DateDayAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.DateMonthAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.DateYearAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.libs.SmartFragmentStatePagerAdapter;
import ehsanfatahizadehgmail.com.jinni.models.CheckGet;
import ehsanfatahizadehgmail.com.jinni.models.CheckGive;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import ehsanfatahizadehgmail.com.jinni.services.CheckReceiver;
import retrofit2.Call;
import retrofit2.Response;

public class ChecksActivity extends AppCompatActivity {



    ApiInterface apis;
    RetrofitSetting retrofit;
    ProgressDialog dialog;

    int NO_DATE = 15000;

    int position_day;
    int position_month;
    int position_year;

    int position_day_give;
    int position_month_give;
    int position_year_give;

    int position_day_search_az;
    int position_month_search_az;
    int position_year_search_az;

    int position_day_search_ta;
    int position_month_search_ta;
    int position_year_search_ta;


    public static void showFABMenu(){
        isFABOpen=true;
        fab_get.animate().translationY(-100);
        fab_give.animate().translationY(-190);

        fab.animate().rotation(45f);
    }


    static boolean isFABOpen;

    public static void closeFABMenu(){
        isFABOpen=false;
        fab_get.animate().translationY(0);
        fab_give.animate().translationY(0);

        fab.animate().rotation(0f);
    }


    static FloatingActionButton fab;
    static FloatingActionButton fab_get;
    static FloatingActionButton fab_give ;
    FrameLayout frame_get , frame_give;




    TextView txt_vaziat_get , txt_tarikh_get;



    List<String> days_31;
    List<String> days_30;
    List<String> months;
    List<String> years;


    Button btn_sabt;


    EditText edt_get_pardakht_tavasote , edt_get_shomare_check , edt_get_gheymat , edt_get_tozihat;


    String g_y , g_m , g_d;
    TextView tw_back_get;
    ImageView img_back_get;


    String mobile_sh;

    ImageView img_back_give;
    TextView tw_back_give;


    TextView tw_vaziate_check_give;
    String g_y_give , g_m_give , g_d_give;

    String g_y_az_search , g_m_az_search , g_d_az_search;
    String g_y_ta_search , g_m_ta_search , g_d_ta_search;


    TextView tw_tarikh_check_give;

    TextView tw_sabt_give;

    EditText edt_dar_vajhe_give ,  edt_az_hesabe_give ,  edt_name_bank_give ,  edt_shomare_check_give ,  edt_mablagh_give ,  edt_tozihat_give;



    ImageView img_back;
    TextView tw_back;

    TextView search;

    Spinner vaziat_check_search;
    String[] list_vaziat_check = new String[]{"همه","در جریان","کلر شده","پاس شده","برگشتی","مسترد شده"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checks);






        SharedPreferences sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        mobile_sh = sh.getString("mobile" , null);


        position_day_give = 0;
        position_month_give = 0;
        position_year_give = 0;

        position_day_search_az = 0;
        position_month_search_az = 0;
        position_year_search_az = 0;

        position_day_search_ta = 0;
        position_month_search_ta = 0;
        position_year_search_ta = 0;

        position_day = 0;
        position_month = 0;
        position_year = 0;

        days_31 = new ArrayList<>();
        days_30 = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();


        for (int i = 1 ; i < 32 ; i++){
            days_31.add(String.valueOf(i));
            if (i<13){
                months.add(String.valueOf(i));
            }
            if (i<31){
                days_30.add(String.valueOf(i));
            }
            years.add(String.valueOf(1397 + i));
        }










        img_back = (ImageView) findViewById(R.id.imageview_back_checks_activity);
        tw_back = (TextView) findViewById(R.id.textview_back_checks_activity);

        search = (TextView) findViewById(R.id.textview_search_checks_activity);




        tw_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ChecksActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.alert_search_checks, null);
                dialogBuilder.setView(dialogView);

                final EditText edt_mablagh_az = (EditText) dialogView.findViewById(R.id.edittext_search_price_az);
                final EditText edt_mablagh_ta = (EditText) dialogView.findViewById(R.id.edittext_search_price_ta);


                final EditText edt_name = (EditText) dialogView.findViewById(R.id.edittext_search_name);
                final EditText edt_shomare_check = (EditText) dialogView.findViewById(R.id.edittext_search_shomare_check);


                final CheckBox checkBox_mablagh = (CheckBox) dialogView.findViewById(R.id.checkbox_price_search);
                final LinearLayout linearLayout_mablagh = (LinearLayout) dialogView.findViewById(R.id.linear_search_check_mablagh);


                final CheckBox checkBox_date = (CheckBox) dialogView.findViewById(R.id.checkbox_date_search);
                final LinearLayout linearLayout_date = (LinearLayout) dialogView.findViewById(R.id.linear_search_date);

                RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.radio_button_group_search);
                final RadioButton radio_get = (RadioButton) radioGroup.findViewById(R.id.radio_button_get_check_search);
                RadioButton radio_give = (RadioButton) radioGroup.findViewById(R.id.radio_button_give_check_search);

                radio_get.setChecked(true);

                radio_get.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            edt_name.setHint("نام پرداخت کننده");
                        }else{
                            edt_name.setHint("نام مشتری");
                        }
                    }
                });


                checkBox_mablagh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            linearLayout_mablagh.setVisibility(View.VISIBLE);
                        }else{
                            linearLayout_mablagh.setVisibility(View.GONE);
                        }

                    }
                });


                checkBox_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            linearLayout_date.setVisibility(View.VISIBLE);
                        }else{
                            linearLayout_date.setVisibility(View.GONE);
                        }

                    }
                });





                position_day_search_az = 0;
                position_month_search_az = 0;
                position_year_search_az = 0;

                position_day_search_ta = 0;
                position_month_search_ta = 0;
                position_year_search_ta = 0;












                final RecyclerView recyclerView_az_sal = (RecyclerView) dialogView.findViewById(R.id.recyclerview_search_az_sal);
                final RecyclerView recyclerView_az_mah = (RecyclerView) dialogView.findViewById(R.id.recyclerview_search_az_mah);
                final RecyclerView recyclerView_az_ruz = (RecyclerView) dialogView.findViewById(R.id.recyclerview_search_az_ruz);

                DateYearAdapter adapter_az_sal = new DateYearAdapter(ChecksActivity.this);
                DateMonthAdapter adapter_az_mah = new DateMonthAdapter(ChecksActivity.this);
                final DateDayAdapter adapter_az_ruz = new DateDayAdapter(ChecksActivity.this);

                final LinearLayoutManager linearLayoutManager_az_sal = new LinearLayoutManager(ChecksActivity.this);
                final LinearLayoutManager linearLayoutManager_az_mah = new LinearLayoutManager(ChecksActivity.this);
                final LinearLayoutManager linearLayoutManager_az_ruz = new LinearLayoutManager(ChecksActivity.this);

                recyclerView_az_sal.setLayoutManager(linearLayoutManager_az_sal);
                recyclerView_az_mah.setLayoutManager(linearLayoutManager_az_mah);
                recyclerView_az_ruz.setLayoutManager(linearLayoutManager_az_ruz);

                recyclerView_az_sal.setAdapter(adapter_az_sal);
                recyclerView_az_mah.setAdapter(adapter_az_mah);
                recyclerView_az_ruz.setAdapter(adapter_az_ruz);

                adapter_az_ruz.add_list(days_31);
                adapter_az_mah.add_list(months);
                adapter_az_sal.add_list(years);




                final RecyclerView recyclerView_ta_sal = (RecyclerView) dialogView.findViewById(R.id.recyclerview_search_ta_sal);
                final RecyclerView recyclerView_ta_mah = (RecyclerView) dialogView.findViewById(R.id.recyclerview_search_ta_mah);
                final RecyclerView recyclerView_ta_ruz = (RecyclerView) dialogView.findViewById(R.id.recyclerview_search_ta_ruz);

                DateYearAdapter adapter_ta_sal = new DateYearAdapter(ChecksActivity.this);
                DateMonthAdapter adapter_ta_mah = new DateMonthAdapter(ChecksActivity.this);
                final DateDayAdapter adapter_ta_ruz = new DateDayAdapter(ChecksActivity.this);

                final RecyclerView.LayoutManager linearLayoutManager_ta_sal = new LinearLayoutManager(ChecksActivity.this);
                final RecyclerView.LayoutManager linearLayoutManager_ta_mah = new LinearLayoutManager(ChecksActivity.this);
                final RecyclerView.LayoutManager linearLayoutManager_ta_ruz = new LinearLayoutManager(ChecksActivity.this);

                recyclerView_ta_sal.setLayoutManager(linearLayoutManager_ta_sal);
                recyclerView_ta_mah.setLayoutManager(linearLayoutManager_ta_mah);
                recyclerView_ta_ruz.setLayoutManager(linearLayoutManager_ta_ruz);

                recyclerView_ta_sal.setAdapter(adapter_ta_sal);
                recyclerView_ta_mah.setAdapter(adapter_ta_mah);
                recyclerView_ta_ruz.setAdapter(adapter_ta_ruz);

                adapter_ta_ruz.add_list(days_31);
                adapter_ta_mah.add_list(months);
                adapter_ta_sal.add_list(years);




                vaziat_check_search = (Spinner) dialogView.findViewById(R.id.spinner_search_vaziat_check);


                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_item, list_vaziat_check);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                vaziat_check_search.setAdapter(adapter);







                recyclerView_az_ruz.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_day_search_az = ((LinearLayoutManager) linearLayoutManager_az_ruz).findFirstVisibleItemPosition();
                            recyclerView_az_ruz.smoothScrollToPosition(position_day_search_az);

                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });

                recyclerView_az_mah.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_month_search_az = ((LinearLayoutManager) linearLayoutManager_az_mah).findFirstVisibleItemPosition();
                            recyclerView_az_mah.smoothScrollToPosition(position_month_search_az);
                            if (position_month_search_az < 6){
                                adapter_az_ruz.add_list(days_31);
                            }else{
                                adapter_az_ruz.add_list(days_30);
                            }
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });

                recyclerView_az_sal.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_year_search_az = ((LinearLayoutManager) linearLayoutManager_az_sal).findFirstVisibleItemPosition();
                            recyclerView_az_sal.smoothScrollToPosition(position_year_search_az);
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });






                recyclerView_ta_ruz.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_day_search_ta = ((LinearLayoutManager) linearLayoutManager_ta_ruz).findFirstVisibleItemPosition();
                            recyclerView_ta_ruz.smoothScrollToPosition(position_day_search_ta);

                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });

                recyclerView_ta_mah.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_month_search_ta = ((LinearLayoutManager) linearLayoutManager_ta_mah).findFirstVisibleItemPosition();
                            recyclerView_ta_mah.smoothScrollToPosition(position_month_search_ta);
                            if (position_month_search_ta < 6){
                                adapter_ta_ruz.add_list(days_31);
                            }else{
                                adapter_ta_ruz.add_list(days_30);
                            }
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });

                recyclerView_ta_sal.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_year_search_ta = ((LinearLayoutManager) linearLayoutManager_ta_sal).findFirstVisibleItemPosition();
                            recyclerView_ta_sal.smoothScrollToPosition(position_year_search_ta);
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });








                Button btn_sabt = (Button) dialogView.findViewById(R.id.button_search_sabt);





//                Button btn_sabt = (Button) dialogView.findViewById(R.id.button_sabt_tarikh_get_check);
//
//                final RecyclerView recyclerView_year = (RecyclerView) dialogView.findViewById(R.id.recycler_alert_date_year);
//                final RecyclerView recyclerView_month = (RecyclerView) dialogView.findViewById(R.id.recycler_alert_date_month);
//                final RecyclerView recyclerView_day = (RecyclerView) dialogView.findViewById(R.id.recycler_alert_date_day);



                final AlertDialog alertDialog = dialogBuilder.create();


                btn_sabt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        if (checkBox_date.isChecked()) {

                            g_y_az_search = years.get(position_year_search_az);
                            g_m_az_search = months.get(position_month_search_az);
                            g_d_az_search = ((position_month_search_az < 6) ? days_31.get(position_day_search_az) : days_30.get(position_day_search_az));


                            g_y_ta_search = years.get(position_year_search_ta);
                            g_m_ta_search = months.get(position_month_search_ta);
                            g_d_ta_search = ((position_month_search_ta < 6) ? days_31.get(position_day_search_ta) : days_30.get(position_day_search_ta));

                        }else{
                            g_y_az_search = "none";
                            g_m_az_search = "none";
                            g_d_az_search = "none";


                            g_y_ta_search = "none";
                            g_m_ta_search = "none";
                            g_d_ta_search = "none";

                        }



                        String s_mablagh_az = "none";
                        String s_mablagh_ta = "none";
                        if (checkBox_mablagh.isChecked()){
                            s_mablagh_az = edt_mablagh_az.getText().toString().trim();
                            s_mablagh_ta = edt_mablagh_ta.getText().toString().trim();
                        }



                        String customer_name = edt_name.getText().toString().trim();
                        String number_check = edt_shomare_check.getText().toString().trim();

                        if (customer_name.equals("")){
                            customer_name = "none";
                        }

                        if (number_check.equals("")){
                            number_check = "none";
                        }

                        String vaziat_check = vaziat_check_search.getSelectedItem().toString();




                        //type 1 for get
                        //type 2 for give
                        String type = "";

                        if (radio_get.isChecked()){
                            type = "1";
                        }else{
                            type = "2";
                        }








                        Log.d("از سال " ,g_y_az_search );
                        Log.d("از ماه " ,g_m_az_search );
                        Log.d("از روز " , g_d_az_search);

                        Log.d("تا سال " , g_y_ta_search);
                        Log.d("تا ماه " , g_m_ta_search);
                        Log.d("تا روز " , g_d_ta_search);


                        Log.d("نام مشتری " , customer_name);
                        Log.d("شماره چک " , number_check);
                        Log.d("وضعیت چک " , vaziat_check);

                        Log.d("نوع چک " , type);

                        Log.d("مبلغ از " , s_mablagh_az);
                        Log.d("مبلغ تا " , s_mablagh_ta);




                        if (type.equals("1")){
                            new thread_search_checks_get(mobile_sh,
                                    g_y_az_search ,
                                    g_m_az_search ,
                                    g_m_az_search ,
                                    g_y_ta_search ,
                                    g_m_ta_search ,
                                    g_d_ta_search ,
                                    customer_name ,
                                    number_check ,
                                    vaziat_check ,
                                    s_mablagh_az ,
                                    s_mablagh_ta).execute();
                        }else if(type.equals("2")){
                            new thread_search_checks_give(mobile_sh,
                                    g_y_az_search ,
                                    g_m_az_search ,
                                    g_m_az_search ,
                                    g_y_ta_search ,
                                    g_m_ta_search ,
                                    g_d_ta_search ,
                                    customer_name ,
                                    number_check ,
                                    vaziat_check ,
                                    s_mablagh_az ,
                                    s_mablagh_ta).execute();
                        }


                        g_y_az_search= "";
                        g_m_az_search = "";
                        g_d_az_search = "";


                        g_y_ta_search= "";
                        g_m_ta_search = "";
                        g_d_ta_search = "";



                        alertDialog.dismiss();
                    }
                });


//                btn_sabt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        g_y_give = years.get(position_year_give);
//                        g_m_give = months.get(position_month_give);
//                        g_d_give = ((position_month_give<6)?days_31.get(position_day_give):days_30.get(position_day_give));
//
//                        tw_tarikh_check_give.setText(g_y_give +"/"+g_m_give+"/"+g_d_give);
//
//                        alertDialog.dismiss();
//                    }
//                });

                alertDialog.show();





            }
        });


        edt_dar_vajhe_give = (EditText) findViewById(R.id.edittext_check_give_dar_vajhe);
        edt_az_hesabe_give = (EditText) findViewById(R.id.edittext_check_give_az_hesabe);
        edt_name_bank_give = (EditText) findViewById(R.id.edittext_check_give_name_bank);
        edt_shomare_check_give = (EditText) findViewById(R.id.edittext_check_give_shomare_check);
        edt_mablagh_give = (EditText) findViewById(R.id.edittext_check_give_mablagh);
        edt_tozihat_give = (EditText) findViewById(R.id.edittext_check_give_tozihat);





//        Log.d("days",days.toString());
//        Log.d("months",months.toString());
//        Log.d("years",years.toString());




        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab_get = (FloatingActionButton) findViewById(R.id.fab_new_get_check);
        fab_give = (FloatingActionButton) findViewById(R.id.fab_new_give_check);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fab_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame_give.setVisibility(View.VISIBLE);
            }
        });


        img_back_give = (ImageView) findViewById(R.id.imageview_back_new_check_give);
        tw_back_give = (TextView) findViewById(R.id.textview_back_frame_give_check);
        tw_sabt_give = (TextView) findViewById(R.id.textview_check_give_tarikhe_sabt);

        tw_vaziate_check_give = (TextView) findViewById(R.id.textview_check_give_vaziate_check);
        tw_tarikh_check_give = (TextView) findViewById(R.id.textview_check_give_tarikhe_reside_check);

        tw_back_get = (TextView) findViewById(R.id.textview_back_frame_get_check);
        img_back_get = (ImageView) findViewById(R.id.imageview_back_new_check_get);

        edt_get_pardakht_tavasote = (EditText) findViewById(R.id.edittext_whos_check_new_get_check);
        edt_get_shomare_check = (EditText) findViewById(R.id.edittext_shomare_check_get_check);
        edt_get_gheymat = (EditText) findViewById(R.id.edittext_price_get_check);
        edt_get_tozihat = (EditText) findViewById(R.id.edittext_tozihat_get_check);




        frame_get = (FrameLayout) findViewById(R.id.checks_new_get);
        frame_give = (FrameLayout) findViewById(R.id.checks_new_give);


        img_back_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame_get.setVisibility(View.GONE);
            }
        });

        tw_back_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame_get.setVisibility(View.GONE);
            }
        });


        fab_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame_get.setVisibility(View.VISIBLE);
            }
        });


        tw_sabt_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate_give();
            }
        });

        tw_tarikh_check_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ChecksActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_date_checks, null);
                dialogBuilder.setView(dialogView);



                g_y_give = "";
                g_m_give = "";
                g_d_give = "";


                Button btn_sabt = (Button) dialogView.findViewById(R.id.button_sabt_tarikh_get_check);

                final RecyclerView recyclerView_year = (RecyclerView) dialogView.findViewById(R.id.recycler_alert_date_year);
                final RecyclerView recyclerView_month = (RecyclerView) dialogView.findViewById(R.id.recycler_alert_date_month);
                final RecyclerView recyclerView_day = (RecyclerView) dialogView.findViewById(R.id.recycler_alert_date_day);

                final DateYearAdapter adapter_year = new DateYearAdapter(ChecksActivity.this);
                DateMonthAdapter adapter_month = new DateMonthAdapter(ChecksActivity.this);
                final DateDayAdapter adapter_day = new DateDayAdapter(ChecksActivity.this);


                final RecyclerView.LayoutManager layoutManager_day = new LinearLayoutManager(ChecksActivity.this);
                final RecyclerView.LayoutManager layoutManager_month = new LinearLayoutManager(ChecksActivity.this);
                final RecyclerView.LayoutManager layoutManager_year = new LinearLayoutManager(ChecksActivity.this);

                recyclerView_year.setLayoutManager(layoutManager_year);
                recyclerView_month.setLayoutManager(layoutManager_month);
                recyclerView_day.setLayoutManager(layoutManager_day);


                adapter_day.add_list(days_31);
                adapter_month.add_list(months);
                adapter_year.add_list(years);


                recyclerView_year.setAdapter(adapter_year);
                recyclerView_month.setAdapter(adapter_month);
                recyclerView_day.setAdapter(adapter_day);



                if (position_day_give != NO_DATE){
                    if (position_day_give == 30 && DateDayAdapter.list.size() == 30){
                        position_day_give = 29;
                    }
                    recyclerView_day.smoothScrollToPosition(position_day_give);
                }

                if (position_month_give != NO_DATE){
                    recyclerView_month.smoothScrollToPosition(position_month_give);

                    if (position_month_give < 6){
                        adapter_day.add_list(days_31);
                    }else{
                        adapter_day.add_list(days_30);
                    }


                }

                if (position_year_give != NO_DATE){
                    recyclerView_year.smoothScrollToPosition(position_year_give);
                }


                recyclerView_day.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_day_give = ((LinearLayoutManager) layoutManager_day).findFirstVisibleItemPosition();
                            recyclerView_day.smoothScrollToPosition(position_day_give);

                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });

                recyclerView_month.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_month_give = ((LinearLayoutManager) layoutManager_month).findFirstVisibleItemPosition();
                            recyclerView_month.smoothScrollToPosition(position_month_give);
                            if (position_month_give < 6){
                                adapter_day.add_list(days_31);
                            }else{
                                adapter_day.add_list(days_30);
                            }
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });

                recyclerView_year.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_year_give = ((LinearLayoutManager) layoutManager_year).findFirstVisibleItemPosition();
                            recyclerView_year.smoothScrollToPosition(position_year_give);
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });


                final AlertDialog alertDialog = dialogBuilder.create();

                btn_sabt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        g_y_give = years.get(position_year_give);
                        g_m_give = months.get(position_month_give);
                        g_d_give = ((position_month_give<6)?days_31.get(position_day_give):days_30.get(position_day_give));

                        tw_tarikh_check_give.setText(g_y_give +"/"+g_m_give+"/"+g_d_give);

                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        tw_vaziate_check_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ChecksActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_vaziate_check, null);
                dialogBuilder.setView(dialogView);

                final TextView dar_jaryan = (TextView) dialogView.findViewById(R.id.alert_vaziate_check_dar_jaryan);
                final TextView cler_shode = (TextView) dialogView.findViewById(R.id.alert_vaziate_check_cler_shode);
                final TextView pas_shode = (TextView) dialogView.findViewById(R.id.alert_vaziate_check_pas_shode);
                final TextView bargashti = (TextView) dialogView.findViewById(R.id.alert_vaziate_check_bargashti);
                final TextView mostarad = (TextView) dialogView.findViewById(R.id.alert_vaziate_check_mostarad);


                final AlertDialog alertDialog = dialogBuilder.create();

                dar_jaryan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tw_vaziate_check_give.setText(dar_jaryan.getText());
                        alertDialog.dismiss();
                    }
                });
                cler_shode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tw_vaziate_check_give.setText(cler_shode.getText());
                        alertDialog.dismiss();
                    }
                });
                pas_shode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tw_vaziate_check_give.setText(pas_shode.getText());
                        alertDialog.dismiss();
                    }
                });
                bargashti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tw_vaziate_check_give.setText(bargashti.getText());
                        alertDialog.dismiss();
                    }
                });
                mostarad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tw_vaziate_check_give.setText(mostarad.getText());
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });




        txt_vaziat_get = (TextView) findViewById(R.id.textview_vaziate_check_get_check);
        txt_tarikh_get = (TextView) findViewById(R.id.textview_tarikh_get_check);

        btn_sabt = (Button) findViewById(R.id.button_sabt_get_check);


        btn_sabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });


        txt_tarikh_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ChecksActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_date_checks, null);
                dialogBuilder.setView(dialogView);

                Button btn_sabt = (Button) dialogView.findViewById(R.id.button_sabt_tarikh_get_check);

                final RecyclerView recyclerView_year = (RecyclerView) dialogView.findViewById(R.id.recycler_alert_date_year);
                final RecyclerView recyclerView_month = (RecyclerView) dialogView.findViewById(R.id.recycler_alert_date_month);
                final RecyclerView recyclerView_day = (RecyclerView) dialogView.findViewById(R.id.recycler_alert_date_day);

                final DateYearAdapter adapter_year = new DateYearAdapter(ChecksActivity.this);
                DateMonthAdapter adapter_month = new DateMonthAdapter(ChecksActivity.this);
                final DateDayAdapter adapter_day = new DateDayAdapter(ChecksActivity.this);


                final RecyclerView.LayoutManager layoutManager_day = new LinearLayoutManager(ChecksActivity.this);
                final RecyclerView.LayoutManager layoutManager_month = new LinearLayoutManager(ChecksActivity.this);
                final RecyclerView.LayoutManager layoutManager_year = new LinearLayoutManager(ChecksActivity.this);

                recyclerView_year.setLayoutManager(layoutManager_year);
                recyclerView_month.setLayoutManager(layoutManager_month);
                recyclerView_day.setLayoutManager(layoutManager_day);


                adapter_day.add_list(days_31);
                adapter_month.add_list(months);
                adapter_year.add_list(years);


                recyclerView_year.setAdapter(adapter_year);
                recyclerView_month.setAdapter(adapter_month);
                recyclerView_day.setAdapter(adapter_day);



                if (position_day != NO_DATE){
                    if (position_day == 30 && DateDayAdapter.list.size() == 30){
                        position_day = 29;
                    }
                    recyclerView_day.smoothScrollToPosition(position_day);
                }

                if (position_month != NO_DATE){
                    recyclerView_month.smoothScrollToPosition(position_month);

                    if (position_month < 6){
                        adapter_day.add_list(days_31);
                    }else{
                        adapter_day.add_list(days_30);
                    }


                }

                if (position_year != NO_DATE){
                    recyclerView_year.smoothScrollToPosition(position_year);
                }


                recyclerView_day.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_day = ((LinearLayoutManager) layoutManager_day).findFirstVisibleItemPosition();
                            recyclerView_day.smoothScrollToPosition(position_day);

                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });

                recyclerView_month.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_month = ((LinearLayoutManager) layoutManager_month).findFirstVisibleItemPosition();
                            recyclerView_month.smoothScrollToPosition(position_month);
                            if (position_month < 6){
                                adapter_day.add_list(days_31);
                            }else{
                                adapter_day.add_list(days_30);
                            }
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });

                recyclerView_year.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.d("state is " , String.valueOf(newState));
                        if (newState == 0){
                            position_year = ((LinearLayoutManager) layoutManager_year).findFirstVisibleItemPosition();
                            recyclerView_year.smoothScrollToPosition(position_year);
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });


                final AlertDialog alertDialog = dialogBuilder.create();

                btn_sabt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        g_y = years.get(position_year);
                        g_m = months.get(position_month);
                        g_d = ((position_month<6)?days_31.get(position_day):days_30.get(position_day));

                        txt_tarikh_get.setText(g_y +"/"+g_m+"/"+g_d);

                        alertDialog.dismiss();
                    }
                });





//                dar_jaryan.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txt_vaziat_get.setText(dar_jaryan.getText());
//                        alertDialog.dismiss();
//                    }
//                });
//

                alertDialog.show();

            }
        });

        txt_vaziat_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ChecksActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_vaziate_check, null);
                dialogBuilder.setView(dialogView);

                final TextView dar_jaryan = (TextView) dialogView.findViewById(R.id.alert_vaziate_check_dar_jaryan);
                final TextView cler_shode = (TextView) dialogView.findViewById(R.id.alert_vaziate_check_cler_shode);
                final TextView pas_shode = (TextView) dialogView.findViewById(R.id.alert_vaziate_check_pas_shode);
                final TextView bargashti = (TextView) dialogView.findViewById(R.id.alert_vaziate_check_bargashti);
                final TextView mostarad = (TextView) dialogView.findViewById(R.id.alert_vaziate_check_mostarad);


                final AlertDialog alertDialog = dialogBuilder.create();

                dar_jaryan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_vaziat_get.setText(dar_jaryan.getText());
                        alertDialog.dismiss();
                    }
                });
                cler_shode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_vaziat_get.setText(cler_shode.getText());
                        alertDialog.dismiss();
                    }
                });
                pas_shode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_vaziat_get.setText(pas_shode.getText());
                        alertDialog.dismiss();
                    }
                });
                bargashti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_vaziat_get.setText(bargashti.getText());
                        alertDialog.dismiss();
                    }
                });
                mostarad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_vaziat_get.setText(mostarad.getText());
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });




        dialog = new ProgressDialog(ChecksActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");





        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_checks);
        tabLayout.addTab(tabLayout.newTab().setText("چک های پرداختی"),0);
        tabLayout.addTab(tabLayout.newTab().setText("چک های دریافتی"),1);


        ViewPager pager = (ViewPager) findViewById(R.id.viewpager_checks);


        MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        pager.setAdapter(pageAdapter);


        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

        tabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                TabLayout tabLayout = (TabLayout) view;
                tabLayout.removeOnLayoutChangeListener(this);
                tabLayout.fullScroll(View.FOCUS_RIGHT);

            }
        });

        pager.setCurrentItem(1);




        Intent notifyIntent = new Intent(this, CheckReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (ChecksActivity.this, 2 , notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
//                1000 * 60 * 60 * 24, pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                1000 * 60 , pendingIntent);


    }









    private class MyPageAdapter extends SmartFragmentStatePagerAdapter {
        public MyPageAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public int getCount()
        {
            return 2;
        }
        @Override
        public Fragment getItem(int position)
        {
            if(position == 0) {
                return new ChecksPardakhtFragment();
            }else if(position == 1) {
                return new ChecksDaryaftFragment();
            }
            else
                return null;
        }
    }



    public void Validate(){


        edt_get_pardakht_tavasote = (EditText) findViewById(R.id.edittext_whos_check_new_get_check);
        edt_get_shomare_check = (EditText) findViewById(R.id.edittext_shomare_check_get_check);
        edt_get_gheymat = (EditText) findViewById(R.id.edittext_price_get_check);
        edt_get_tozihat = (EditText) findViewById(R.id.edittext_tozihat_get_check);



        String tavasote = edt_get_pardakht_tavasote.getText().toString().trim();
        String shomare_check = edt_get_shomare_check.getText().toString().trim();
        String gheymat = edt_get_gheymat.getText().toString().trim();
        String tozihat = edt_get_tozihat.getText().toString().trim();
        String vaziat = txt_vaziat_get.getText().toString();

        if (vaziat.equals("وضعیت چک")){
            vaziat = "none";
        }
        if (tavasote.equals("")){
            tavasote = "none";
        }
        if (shomare_check.equals("")){
            shomare_check = "none";
        }
        if (gheymat.equals("")){
            gheymat = "none";
        }
        if (tozihat.equals("")){
            tozihat = "none";
        }

        if (g_y == null){
            Toast.makeText(this, "تاریخ چک را انتخاب کنید", Toast.LENGTH_SHORT).show();
        }else{
            new thread_send_check_get(mobile_sh , tavasote , shomare_check , gheymat , tozihat , vaziat , g_y , g_m , g_d).execute();
        }


    }






    public void Validate_give(){


        String s_dar_vajhe = edt_dar_vajhe_give.getText().toString().trim();
        String s_az_hesabe = edt_az_hesabe_give.getText().toString().trim();
        String s_name_bank = edt_name_bank_give.getText().toString().trim();
        String s_shomare_check = edt_shomare_check_give.getText().toString().trim();
        String s_mablagh = edt_mablagh_give.getText().toString().trim();
        String s_tozihat = edt_tozihat_give.getText().toString().trim();
        String s_vaziat = tw_vaziate_check_give.getText().toString();


        if (s_dar_vajhe.equals("")){
            s_dar_vajhe = "none";
        }
        if (s_az_hesabe.equals("")){
            s_az_hesabe = "none";
        }
        if (s_name_bank.equals("")){
            s_name_bank = "none";
        }
        if (s_shomare_check.equals("")){
            s_shomare_check = "none";
        }
        if (s_mablagh.equals("")){
            s_mablagh = "none";
        }
        if (s_tozihat.equals("")){
            s_tozihat = "none";
        }
        if (s_vaziat.equals("وضعیت چک")){
            s_vaziat = "none";
        }


        if (g_y_give.equals("")){
            Toast.makeText(this, "تاریخ چک را انتخاب کنید", Toast.LENGTH_SHORT).show();
        }else{
            new thread_send_check_give(mobile_sh , s_dar_vajhe , s_az_hesabe , s_name_bank , s_shomare_check , s_mablagh , s_tozihat , s_vaziat , g_y_give , g_m_give , g_d_give).execute();
        }




    }




    public class thread_send_check_give extends AsyncTask {

        String mobile;
        String dar_vajhe;
        String az_hesabe;
        String name_bank;
        String shomare_check;
        String mablagh;
        String tozihat;
        String vaziat;
        String year;
        String month;
        String day;
        public thread_send_check_give(String mobile ,
                                      String dar_vajhe ,
                                      String az_hesabe ,
                                      String name_bank ,
                                      String shomare_check ,
                                      String mablagh ,
                                      String tozihat ,
                                      String vaziat ,
                                      String year ,
                                      String month ,
                                      String day){

            this.mobile = mobile;
            this.dar_vajhe = dar_vajhe;
            this.az_hesabe = az_hesabe;
            this.name_bank = name_bank;
            this.shomare_check = shomare_check;
            this.mablagh = mablagh;
            this.tozihat = tozihat;
            this.vaziat = vaziat;
            this.year = year;
            this.month = month;
            this.day = day;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        String result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<String> call = apis.sendCheckGive(mobile , dar_vajhe , az_hesabe , name_bank , shomare_check , mablagh , tozihat , vaziat , year , month , day);

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

                    Toast.makeText(ChecksActivity.this, "ثبت شد", Toast.LENGTH_SHORT).show();
                    frame_give.setVisibility(View.GONE);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
            }else {
                Toast.makeText(ChecksActivity.this, "مشکل در ارتباط با سرور \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }










    public class thread_send_check_get extends AsyncTask {

        String mobile;
        String tavasote;
        String shomare_check;
        String gheymat;
        String tozihat;
        String vaziat;
        String year;
        String month;
        String day;
        public thread_send_check_get(String mobile ,
                                     String tavasote ,
                                     String shomare_check ,
                                     String gheymat ,
                                     String tozihat ,
                                     String vaziat ,
                                     String year ,
                                     String month ,
                                     String day){

            this.mobile = mobile;
            this.tavasote = tavasote;
            this.shomare_check = shomare_check;
            this.gheymat = gheymat;
            this.tozihat = tozihat;
            this.vaziat = vaziat;
            this.year = year;
            this.month = month;
            this.day = day;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        String result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<String> call = apis.sendCheckGet(mobile , tavasote , shomare_check , gheymat , tozihat , vaziat , year , month , day);

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

                    Toast.makeText(ChecksActivity.this, "ثبت شد", Toast.LENGTH_SHORT).show();
                    frame_give.setVisibility(View.GONE);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
            }else {
                Toast.makeText(ChecksActivity.this, "مشکل در ارتباط با سرور \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }




//                        Log.d("از سال " ,g_y_az_search );
//                        Log.d("از ماه " ,g_m_az_search );
//                        Log.d("از روز " , g_d_az_search);
//
//                        Log.d("تا سال " , g_y_ta_search);
//                        Log.d("تا ماه " , g_m_ta_search);
//                        Log.d("تا روز " , g_d_ta_search);
//
//
//                        Log.d("نام مشتری " , customer_name);
//                        Log.d("شماره چک " , number_check);
//                        Log.d("وضعیت چک " , vaziat_check);
//
//                        Log.d("نوع چک " , type);
//
//                        Log.d("مبلغ از " , s_mablagh_az);
//                        Log.d("مبلغ تا " , s_mablagh_ta);




    public class thread_search_checks_get extends AsyncTask {

        String mobile;
        String g_y_az;
        String g_m_az;
        String g_d_az;
        String g_y_ta;
        String g_m_ta;
        String g_d_ta;
        String customer_name;
        String number_check;
        String vaziat_check;
        String mablagh_az;
        String mablagh_ta;

        public thread_search_checks_get(String mobile,
                                    String g_y_az,
                                    String g_m_az,
                                    String g_d_az,
                                    String g_y_ta,
                                    String g_m_ta,
                                    String g_d_ta,
                                    String customer_name,
                                    String number_check,
                                    String vaziat_check,
                                    String mablagh_az,
                                    String mablagh_ta){


            this.mobile = mobile;
            this.g_y_az = g_y_az;
            this.g_m_az = g_m_az;
            this.g_d_az = g_d_az;
            this.g_y_ta = g_y_ta;
            this.g_m_ta = g_m_ta;
            this.g_d_ta = g_d_ta;
            this.customer_name = customer_name;
            this.number_check = number_check;
            this.vaziat_check = vaziat_check;
            this.mablagh_az = mablagh_az;
            this.mablagh_ta = mablagh_ta;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        List<CheckGet> result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<CheckGet>> call = apis.searchInChecksGet(mobile,
                                                    g_y_az,
                                                    g_m_az,
                                                    g_d_az,
                                                    g_y_ta,
                                                    g_m_ta,
                                                    g_d_ta,
                                                    customer_name,
                                                    number_check,
                                                    vaziat_check,
                                                    mablagh_az,
                                                    mablagh_ta);

            try {
                Response<List<CheckGet>> response = call.execute();

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


                ChecksDaryaftFragment.adapter_check_get.add_list(result);


            }else {
                Toast.makeText(ChecksActivity.this, "مشکل در ارتباط با سرور \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }







    public class thread_search_checks_give extends AsyncTask {

        String mobile;
        String g_y_az;
        String g_m_az;
        String g_d_az;
        String g_y_ta;
        String g_m_ta;
        String g_d_ta;
        String customer_name;
        String number_check;
        String vaziat_check;
        String mablagh_az;
        String mablagh_ta;

        public thread_search_checks_give(String mobile,
                                    String g_y_az,
                                    String g_m_az,
                                    String g_d_az,
                                    String g_y_ta,
                                    String g_m_ta,
                                    String g_d_ta,
                                    String customer_name,
                                    String number_check,
                                    String vaziat_check,
                                    String mablagh_az,
                                    String mablagh_ta){

            this.mobile = mobile;
            this.g_y_az = g_y_az;
            this.g_m_az = g_m_az;
            this.g_d_az = g_d_az;
            this.g_y_ta = g_y_ta;
            this.g_m_ta = g_m_ta;
            this.g_d_ta = g_d_ta;
            this.customer_name = customer_name;
            this.number_check = number_check;
            this.vaziat_check = vaziat_check;
            this.mablagh_az = mablagh_az;
            this.mablagh_ta = mablagh_ta;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        List<CheckGive> result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<CheckGive>> call = apis.searchInChecksGive(mobile,
                    g_y_az,
                    g_m_az,
                    g_d_az,
                    g_y_ta,
                    g_m_ta,
                    g_d_ta,
                    customer_name,
                    number_check,
                    vaziat_check,
                    mablagh_az,
                    mablagh_ta);

            try {
                Response<List<CheckGive>> response = call.execute();

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

                ChecksPardakhtFragment.adapter.add_list(result);


            }else {
                Toast.makeText(ChecksActivity.this, "مشکل در ارتباط با سرور \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }
















    public static void show_fab(){
        fab.animate().translationY(0);
        fab_get.animate().translationY(0);
        fab_give.animate().translationY(0);
    }

    public static void hidden_fab(){
        fab.animate().translationY(200);
        fab_get.animate().translationY(200);
        fab_give.animate().translationY(200);
    }


}
