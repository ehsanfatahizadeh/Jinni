package ehsanfatahizadehgmail.com.jinni;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.github.johnkil.print.PrintView;
import java.io.IOException;
import java.util.List;
import ehsanfatahizadehgmail.com.jinni.adapters.CategoriesHomeAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.libs.BaseActivity;
import ehsanfatahizadehgmail.com.jinni.libs.SmartFragmentStatePagerAdapter;
import ehsanfatahizadehgmail.com.jinni.models.CategoriesList;
import ehsanfatahizadehgmail.com.jinni.models.Products;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;


public class HomeActivity extends BaseActivity {


    DrawerLayout drawerLayout;
    TextView txt_signUp;
    PrintView printView_drawer;
    TextView list_categories;

    RecyclerView recyclerView_categories;
    CategoriesHomeAdapter adapter_categories;
    ApiInterface apis;
    RetrofitSetting retrofit;
    ProgressDialog dialog;


    TextView manage_checks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        manage_checks = (TextView) findViewById(R.id.textview_modiriat_checkha);


        manage_checks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this , ChecksActivity.class));
            }
        });

        recyclerView_categories = (RecyclerView) findViewById(R.id.recycler_home_categories);
        adapter_categories = new CategoriesHomeAdapter(HomeActivity.this);


        adapter_categories.setOnItemClickListener(new CategoriesHomeAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, CategoriesList categoriesList) {
//                Toast.makeText(HomeActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();


                if (!categoriesList.getNum_of_subs().equals("0")){
                    Intent intent = new Intent(HomeActivity.this , CategoriesHomeActivity.class);
                    intent.putExtra("categoryId" , categoriesList.getId());
                    intent.putExtra("categoryName" , categoriesList.getName());
                    startActivity(intent);
                }else{
                    Intent intent2 = new Intent(HomeActivity.this , ProductsOfCategoryActivity.class);
                    intent2.putExtra("parent_category_id" , categoriesList.getId());
                    intent2.putExtra("parent_category_name" , categoriesList.getName());
                    startActivity(intent2);
                }

            }
        });


        dialog = new ProgressDialog(HomeActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");


        SharedPreferences sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        String mobile = sh.getString("mobile" , null);


        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();




        TextView new_product = (TextView) findViewById(R.id.new_product_home);
        txt_signUp = (TextView) findViewById(R.id.sign_up_home);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        PrintView printView_drawer = (PrintView) findViewById(R.id.printview_home_drawer);

        list_categories = (TextView) findViewById(R.id.new_product_list_of_categories);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("حراجی"),0);
        tabLayout.addTab(tabLayout.newTab().setText("ویژه ها"),1);
        tabLayout.addTab(tabLayout.newTab().setText("جدیدترین ها"),2);

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);


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

        pager.setCurrentItem(2);



        list_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this , CategoriesListActivity.class));
            }
        });

        txt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this , SignUpActivity.class));
            }
        });

        new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(HomeActivity.this , NewProductActivity.class));
            }
        });




        printView_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_close_drawer();
            }
        });

        pager.setOffscreenPageLimit(3);



        new thread_send_new_product(mobile).execute();



    }

    private class MyPageAdapter extends SmartFragmentStatePagerAdapter {
        public MyPageAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public int getCount()
        {
            return 3;
        }
        @Override
        public Fragment getItem(int position)
        {
            if(position == 0) {
//                selectedTab =1;
                return new CheapFragment();
            }else if(position == 1) {
//                selectedTab =0;
//                return new BookmarkTutorialFragment();
                return new SpecialFragment();
            }else if(position == 2) {
//                selectedTab =0;
//                return new BookmarkTutorialFragment();
                return new NewestFragment();
            }
            else
                return null;
        }
    }
    public class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
        private final ViewPager pager;

        public ViewPagerOnTabSelectedListener(ViewPager pager) {
            this.pager = pager;
        }

        public void onTabSelected(TabLayout.Tab tab) {
            this.pager.setCurrentItem(tab.getPosition());
        }

        public void onTabUnselected(TabLayout.Tab tab) {
        }

        public void onTabReselected(TabLayout.Tab tab) {
        }
    }




    public void open_close_drawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawers();
        }else {
            drawerLayout.openDrawer(GravityCompat.END);
        }
    }






    public class thread_send_new_product extends AsyncTask {


        String mobile;

        public thread_send_new_product(String mobile){

            this.mobile = mobile;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        List<CategoriesList> result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<CategoriesList>> call = apis.getCategories(mobile , "0");

            try {
                Response<List<CategoriesList>> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "errrror", Toast.LENGTH_SHORT).show();
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
                if (result.size() > 0) {
                    if (recyclerView_categories.getVisibility() == View.GONE) {
                        recyclerView_categories.setVisibility(View.VISIBLE);
                        recyclerView_categories.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView_categories.setAdapter(adapter_categories);

                    }
                    adapter_categories.add_list(result);
                }else {
                    if (recyclerView_categories.getVisibility() == View.VISIBLE) {
                        recyclerView_categories.setVisibility(View.GONE);
                    }
                }
            }else {
                Toast.makeText(HomeActivity.this, "خطا در ارتباط با سرورد", Toast.LENGTH_SHORT).show();
            }
        }
    }








}
