package ehsanfatahizadehgmail.com.jinni;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.adapters.NewestAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.models.Products;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;

public class ProductsOfCategoryActivity extends AppCompatActivity {


    ApiInterface apis;
    RetrofitSetting retrofit;
    ProgressDialog dialog;


    RecyclerView recyclerView;
    NewestAdapter adapter;


    TextView textView_titr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_of_category);

        String parent_category_id = getIntent().getStringExtra("parent_category_id");
        String parent_category_name = getIntent().getStringExtra("parent_category_name");




        textView_titr = (TextView) findViewById(R.id.textview_titr_products_of_category);
        textView_titr.setText(parent_category_name);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_p_a);
        adapter = new NewestAdapter(ProductsOfCategoryActivity.this , 3);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductsOfCategoryActivity.this));
        recyclerView.setAdapter(adapter);

        dialog = new ProgressDialog(ProductsOfCategoryActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");


        SharedPreferences sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        String mobile = sh.getString("mobile" , null);


        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();


        Log.d("parent_category_id is " , parent_category_id);

        new thread_get_products(mobile , parent_category_id).execute();

    }










    List<Products> result;
    public class thread_get_products extends AsyncTask {


        String mobile;
        String parent_category_id;
        public thread_get_products(String mobile , String parent_category_id){
            this.mobile = mobile;
            this.parent_category_id = parent_category_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }



        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<Products>> call = apis.getProductsOfCategory(mobile , parent_category_id);

            try {
                Response<List<Products>> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(ProductsOfCategoryActivity.this, "errrror2", Toast.LENGTH_SHORT).show();
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
                if (result.size()>0) {
                    adapter.addList(result);
                }else{
                    Toast.makeText(ProductsOfCategoryActivity.this, "محصولی هنوز در این دسته وجود ندارد!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ProductsOfCategoryActivity.this, "ارتباط با سرور برقرار نشد!", Toast.LENGTH_SHORT).show();
            }

        }

    }










}
