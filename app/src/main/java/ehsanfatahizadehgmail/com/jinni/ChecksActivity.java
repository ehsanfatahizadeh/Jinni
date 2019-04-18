package ehsanfatahizadehgmail.com.jinni;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
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


    TextView tw_tarikh_check_give;

    TextView tw_sabt_give;

    EditText edt_dar_vajhe_give ,  edt_az_hesabe_give ,  edt_name_bank_give ,  edt_shomare_check_give ,  edt_mablagh_give ,  edt_tozihat_give;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checks);


        edt_dar_vajhe_give = (EditText) findViewById(R.id.edittext_check_give_dar_vajhe);
        edt_az_hesabe_give = (EditText) findViewById(R.id.edittext_check_give_az_hesabe);
        edt_name_bank_give = (EditText) findViewById(R.id.edittext_check_give_name_bank);
        edt_shomare_check_give = (EditText) findViewById(R.id.edittext_check_give_shomare_check);
        edt_mablagh_give = (EditText) findViewById(R.id.edittext_check_give_mablagh);
        edt_tozihat_give = (EditText) findViewById(R.id.edittext_check_give_tozihat);



        SharedPreferences sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        mobile_sh = sh.getString("mobile" , null);


        position_day_give = 0;
        position_month_give = 0;
        position_year_give = 0;

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


        if (g_y_give == null){
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
