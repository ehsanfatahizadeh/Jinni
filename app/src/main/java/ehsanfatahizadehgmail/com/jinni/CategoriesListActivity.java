package ehsanfatahizadehgmail.com.jinni;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.adapters.CategoryListAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.models.CategoriesList;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;

public class CategoriesListActivity extends AppCompatActivity {


    ApiInterface apis;
    RetrofitSetting retrofit;
    ProgressDialog dialog;
    List<CategoriesList> list;

    RecyclerView recycler_categories;
    CategoryListAdapter adapter;


    Button btn_show_expandable;
    ExpandableLayout expandableLayout_add_category;
    Button btn_add_category;
    EditText edt_name_category;

    TextView title;

//    String id_parent_category;
    String s_title = "دسته بندی اصلی";
//    String Previous_category="nothing";


    RelativeLayout back_cate;

    List<String> path_father;
    List<String> path_current;
    List<String> path__current_names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);


        path_father = new ArrayList<>();
        path_current = new ArrayList<>();
        path__current_names = new ArrayList<>();
//        path_father.add("دسته بندی اصلی");

//        id_parent_category="0";



        btn_show_expandable = (Button) findViewById(R.id.button_add_category_list);
        expandableLayout_add_category = (ExpandableLayout) findViewById(R.id.expandableAddCategory);
        edt_name_category = (EditText) findViewById(R.id.edittext_name_category);
        btn_add_category = (Button) findViewById(R.id.button_sabt_categor);
        title = (TextView) findViewById(R.id.textview_title_categories_action_bar);
        back_cate = (RelativeLayout) findViewById(R.id.relative_back_categories);


        SharedPreferences sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        final String mobile = sh.getString("mobile" , null);



        back_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = path_father.size();
                new getCategories(mobile , path_father.get(num-1)).execute();
                path_current.remove(path_current.size()-1);
                path_father.remove(num-1);
                path__current_names.remove(path__current_names.size()-1);
            }
        });






        btn_show_expandable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout_add_category.toggle();
            }
        });

        btn_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_category = edt_name_category.getText().toString().trim();
                if (!name_category.equals("")){
//                    Log.d("path is : " , path_current.toString());
                    if (path_current.size()==0){
                        new addCategory(name_category , mobile , "0" ).execute();
                    }else {
                        new addCategory(name_category , mobile , path_current.get(path_current.size()-1) ).execute();
                    }

                }else {
                    Toast.makeText(CategoriesListActivity.this, "نام دسته بندی را وارد کنید!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        recycler_categories = (RecyclerView)findViewById(R.id.recycler_categories_list);
        adapter = new CategoryListAdapter(CategoriesListActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CategoriesListActivity.this);
        recycler_categories.setLayoutManager(layoutManager);
        recycler_categories.setAdapter(adapter);


        adapter.setOnItemClickListener(new CategoryListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, CategoriesList categoriesList) {
                new getCategories(mobile , categoriesList.getId()).execute();
                s_title = categoriesList.getName();
                path_father.add(categoriesList.getParent_category());
                path__current_names.add(categoriesList.getName());
                path_current.add(categoriesList.getId());
            }
        });




        dialog = new ProgressDialog(CategoriesListActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");

        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();

        new getCategories(mobile , "0").execute();




    }





    public class getCategories extends AsyncTask {

        String mobile;
        String parent_category;
        public getCategories(String mobile , String parent_category){
            this.mobile = mobile;
            this.parent_category = parent_category;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<CategoriesList>> call = apis.getCategories(mobile , parent_category);

            try {
                Response<List<CategoriesList>> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(CategoriesListActivity.this, "errrror", Toast.LENGTH_SHORT).show();
                    return null;
                }
                list = response.body();






            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            int num = path_father.size();
            Log.d("size of list " , String.valueOf(list.size()));
            adapter.add_list(list);
            dialog.dismiss();

            if (num != 0){
                title.setText(path__current_names.get(path__current_names.size()-1));
                back_cate.setVisibility(View.VISIBLE);
            }else {
                title.setText("دسته بندی اصلی");
                back_cate.setVisibility(View.GONE);
            }


        }
    }






    public class addCategory extends AsyncTask {


        String name;
        String mobile;
        String parent_category;
        public addCategory(String name , String mobile , String parent_category){
            this.name = name;
            this.mobile = mobile;
            this.parent_category = parent_category;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();


        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<CategoriesList>> call = apis.newCategory(name , mobile , parent_category);

            try {
                Response<List<CategoriesList>> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(CategoriesListActivity.this, "errrror", Toast.LENGTH_SHORT).show();
                    return null;
                }
                list = response.body();






            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            Log.d("size of list " , String.valueOf(list.size()));
            dialog.dismiss();

            if (list.size() == 0){
                Toast.makeText(CategoriesListActivity.this, "خطا در ارسال اطلاعات", Toast.LENGTH_SHORT).show();
            }else if (list.get(0).getMobile().equals("201942") && list.get(0).getParent_category().equals("201942") ){
                Toast.makeText(CategoriesListActivity.this, "این نام در این دسته بندی تکراری است!", Toast.LENGTH_SHORT).show();
            }else {
                expandableLayout_add_category.toggle();
                adapter.add_list(list);


            }


        }
    }







}
