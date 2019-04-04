package ehsanfatahizadehgmail.com.jinni;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.adapters.CategoriesHomeAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.CategoryListAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.models.CategoriesList;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;

public class CategoriesHomeActivity extends AppCompatActivity {


    RecyclerView recyclerView_categories;
    CategoryListAdapter adapter_categories;


    ApiInterface apis;
    RetrofitSetting retrofit;
    ProgressDialog dialog;


    TextView titr_current_category;


    String current_category = "";
    String parent_category = "";
    String first_category="";


    ImageView back_img;

    List<String> titr = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_home);


        first_category = getIntent().getStringExtra("categoryId");
        current_category = getIntent().getStringExtra("categoryName");

        titr_current_category = (TextView) findViewById(R.id.categories_act_titr);
        back_img = (ImageView) findViewById(R.id.categories_act_back_img);
        titr.add(current_category);

        dialog = new ProgressDialog(CategoriesHomeActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");


        SharedPreferences sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        final String mobile = sh.getString("mobile" , null);


        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();




        recyclerView_categories = (RecyclerView) findViewById(R.id.recycler_categories_activity);
        adapter_categories = new CategoryListAdapter(CategoriesHomeActivity.this);
        recyclerView_categories.setLayoutManager(new LinearLayoutManager(CategoriesHomeActivity.this));
        recyclerView_categories.setAdapter(adapter_categories);



        adapter_categories.setOnItemClickListener(new CategoryListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, CategoriesList categoriesList) {
                if (!categoriesList.getNum_of_subs().equals("0")){
                    new thread_getCategories(mobile , categoriesList.getId()).execute();
                    current_category = categoriesList.getName();
                    parent_category = categoriesList.getParent_category();
                    titr.add(current_category);
                }else{
                    Intent intent = new Intent(CategoriesHomeActivity.this , ProductsOfCategoryActivity.class);
                    intent.putExtra("parent_category_id" , categoriesList.getId());
                    intent.putExtra("parent_category_name" , categoriesList.getName());
                    startActivity(intent);
                }
            }
        });


        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titr.size()==1){
                    finish();
                }else {
                    new thread_getCategories(mobile, parent_category).execute();
                    titr.remove(titr.size()-1);

                }
            }
        });



        new thread_getCategories(mobile , first_category).execute();

    }









    public class thread_getCategories extends AsyncTask {


        String mobile;
        String parent_category_id;

        public thread_getCategories(String mobile , String parent_category_id){

            this.mobile = mobile;
            this.parent_category_id = parent_category_id;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        List<CategoriesList> result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<CategoriesList>> call = apis.getCategories(mobile , parent_category_id);

            try {
                Response<List<CategoriesList>> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(CategoriesHomeActivity.this, "errrror", Toast.LENGTH_SHORT).show();
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


                    adapter_categories.add_list(result);

                    Log.d("titr list is " , titr.toString());
                    titr_current_category.setText(titr.get(titr.size()-1));




                }else {

                    Toast.makeText(CategoriesHomeActivity.this, "لیست خالی است!", Toast.LENGTH_SHORT).show();

                }
            }else {
                Toast.makeText(CategoriesHomeActivity.this, "خطا در ارتباط با سرورد", Toast.LENGTH_SHORT).show();
            }
        }
    }










}
