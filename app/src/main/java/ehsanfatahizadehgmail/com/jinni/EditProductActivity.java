package ehsanfatahizadehgmail.com.jinni;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import de.hdodenhof.circleimageview.CircleImageView;
import ehsanfatahizadehgmail.com.jinni.adapters.CategoryListAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.ColorsAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.PhotosProductAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.PriceTedadAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.PropertyAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.SizesAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.libs.ImageOrientation;
import ehsanfatahizadehgmail.com.jinni.models.CategoriesList;
import ehsanfatahizadehgmail.com.jinni.models.PhotoAddress;
import ehsanfatahizadehgmail.com.jinni.models.Products;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;

public class EditProductActivity extends AppCompatActivity {

    ProgressDialog dialog;
    ApiInterface apis;
    RetrofitSetting retrofit;


    List<String> list_of_photos;

    List<Products> list_p;


    EditText edt_code , edt_mahiat , edt_jensiat , edt_brand , edt_model , edt_vizhegi_titr , edt_size_titr , edt_tozihat;



    TextView tw_colors;
    ColorsAdapter adapter;


    Button btn_add_size;
    EditText edt_new_size;
    RecyclerView recycler_sizes;
    SizesAdapter adapter_size;

    TextView tw_choose_category;
    CategoryListAdapter adapter_categories;



    Button btn_add_property;
    EditText edt_property_descrip , edt_property_properties;
    RecyclerView recycler_property;
    PropertyAdapter adapter_property;



    CheckBox checkBox_haraji , checkBox_special;
    EditText edt_haraji;


    EditText edt_name_vahed , edt_gheymate_vahed , edt_vahede_farei , edt_zaribe_vahede_farei;


    Button btn_add_prices;
    EditText edt_az_tedad;
    EditText edt_ta_tedad;
    EditText edt_gheymat_tedad;
    RecyclerView recycler_tedad;
    PriceTedadAdapter adapter_tedad;




    RecyclerView recycler_photos;
    Button btn_add_photo;
    Intent GalIntent;
    PhotosProductAdapter adapter_photos;



    RelativeLayout relativeLayout_photos;


    TextView tw_tamam;



    JSONArray ja_add;
    JSONArray ja_remove;


    Button btn_sabt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        ja_add = new JSONArray();
        ja_remove = new JSONArray();


        list_of_photos = new ArrayList<>();
        list_p = new ArrayList<>();
        list_p = ProductActivity.list_p;

        list_of_photos = list_p.get(0).getPhotos();

        tw_tamam = (TextView) findViewById(R.id.textview_tamam_edit_relative_photos);

        relativeLayout_photos = (RelativeLayout) findViewById(R.id.relative_parent_photos_edit_product_act);

        btn_sabt = (Button) findViewById(R.id.button_new_p_final_sabt_edit_p);

        btn_sabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });

        tw_tamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_name_vahed = (EditText) findViewById(R.id.name_vahed_new_p_edit_p);
        edt_gheymate_vahed = (EditText) findViewById(R.id.price_har_vahed_new_p_edit_p);
        edt_vahede_farei = (EditText) findViewById(R.id.vahed_zarib_sefaresh_new_p_edit_p);
        edt_zaribe_vahede_farei = (EditText) findViewById(R.id.zarib_sefaresh_new_p_edit_p);




        edt_haraji = (EditText) findViewById(R.id.old_price_new_p_edit_p);
        checkBox_haraji = (CheckBox) findViewById(R.id.checkbox_cheap_edit_p);
        checkBox_special = (CheckBox) findViewById(R.id.checkbox_special_edit_p);


        if (list_p.get(0).getHaraji().equals("no")){
            edt_haraji.setVisibility(View.GONE);
        }else {
            checkBox_haraji.setChecked(true);
            edt_haraji.setText(list_p.get(0).getHaraji());
        }

        if (list_p.get(0).getSpecial().equals("yes")){
            checkBox_special.setChecked(true);
        }

        checkBox_haraji.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edt_haraji.setVisibility(View.VISIBLE);
                }else{
                    edt_haraji.setVisibility(View.GONE);
                }
            }
        });





        dialog = new ProgressDialog(EditProductActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");
        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();




        recycler_photos = (RecyclerView) findViewById(R.id.recycler_photos_new_p_edit_p);
        btn_add_photo = (Button) findViewById(R.id.button_add_pictures_edit_p);
        recycler_photos.setLayoutManager(new LinearLayoutManager(EditProductActivity.this, LinearLayoutManager.HORIZONTAL, false));
        adapter_photos = new PhotosProductAdapter(EditProductActivity.this);
        recycler_photos.setAdapter(adapter_photos);
        encoded_photos_list = new ArrayList<>();


        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(EditProductActivity.this);
                ab.setMessage("انتخاب عکس");
                ab.setPositiveButton("دوربین", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CameraOpen();
                    }
                });
                ab.setNegativeButton("گالری", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GalleryOpen();
                    }
                });
                ab.show();
            }
        });

        adapter_photos.setOnItemClickListener(new PhotosProductAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {

                new add_delete_photos(list_p.get(0).getMobile() , s_code , list_of_photos.get(position)).execute();

                Log.d("photo is " , "mobile="+list_p.get(0).getMobile()+"&&code="+list_p.get(0).getCode()+"&&address="+list_of_photos.get(position));

            }
        });






        edt_code = (EditText) findViewById(R.id.edittext_code_new_p_edit_p);
        edt_mahiat = (EditText) findViewById(R.id.mahiat_edittext_new_p_edit_p);
        edt_jensiat = (EditText) findViewById(R.id.jensiat_edittext_new_p_edit_p);
        edt_brand = (EditText) findViewById(R.id.brand_edittext_new_p_edit_p);
        edt_model = (EditText) findViewById(R.id.model_edittext_new_p_edit_p);
        edt_vizhegi_titr = (EditText) findViewById(R.id.special_property_edittext_new_p_edit_p);
        edt_size_titr = (EditText) findViewById(R.id.size_titr_edittext_new_p_edit_p);
        edt_tozihat = (EditText) findViewById(R.id.description_edittext_new_p_edit_p);


        tw_colors = (TextView) findViewById(R.id.colors_new_p_edit_p);
        adapter = new ColorsAdapter(EditProductActivity.this);


        tw_choose_category = (TextView) findViewById(R.id.add_to_category_edittext_new_p_edit_p);



        btn_add_property = (Button) findViewById(R.id.button_add_property_edit_p);
        edt_property_properties = (EditText) findViewById(R.id.edt_table_properties_edit_p);
        edt_property_descrip = (EditText) findViewById(R.id.edt_table_description_edit_p);
        recycler_property = (RecyclerView) findViewById(R.id.recyclerview_property__table_edit_p);
        recycler_property.setLayoutManager(new LinearLayoutManager(EditProductActivity.this));
        adapter_property = new PropertyAdapter(EditProductActivity.this);
        recycler_property.setAdapter(adapter_property);





        btn_add_prices = (Button) findViewById(R.id.button_add_row_price_table_edit_p);
        edt_az_tedad = (EditText) findViewById(R.id.edt_new_p_az_row_edit_p);
        edt_ta_tedad = (EditText) findViewById(R.id.edt_new_p_ta_row_edit_p);
        edt_gheymat_tedad = (EditText) findViewById(R.id.edt_new_p_price_row_edit_p);
        recycler_tedad = (RecyclerView) findViewById(R.id.recyclerview_big_price_edit_p);
        recycler_tedad.setLayoutManager(new LinearLayoutManager(EditProductActivity.this));
        adapter_tedad = new PriceTedadAdapter(EditProductActivity.this);
        recycler_tedad.setAdapter(adapter_tedad);


        btn_add_prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String az = edt_az_tedad.getText().toString().trim();
                String ta = edt_ta_tedad.getText().toString().trim();
                String prc = edt_gheymat_tedad.getText().toString().trim();

                if (!az.equals("")  && !prc.equals("")){

                    if (ta.equals("")){
                        ta = "به بالا";
                    }

                    adapter_tedad.add_row(az , ta , prc);

                }



            }
        });


        adapter_tedad.setOnItemClickListener(new PriceTedadAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                adapter_tedad.delete_row(position);
            }
        });





        btn_add_size = (Button) findViewById(R.id.button_add_size_new_p_edit_p);
        edt_new_size = (EditText) findViewById(R.id.edittext_size_new_p_edit_p);
        recycler_sizes = (RecyclerView) findViewById(R.id.recycler_size_new_p_edit_p);
        adapter_size = new SizesAdapter(EditProductActivity.this , 1);
        recycler_sizes.setLayoutManager(new LinearLayoutManager(EditProductActivity.this , LinearLayoutManager.HORIZONTAL , false));
        recycler_sizes.setAdapter(adapter_size);

        adapter_size.setOnItemClickListener(new SizesAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, String size) {
                adapter_size.delete_size(position);
            }
        });
        btn_add_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String size = edt_new_size.getText().toString().trim();
                if (!size.equals("")){
                    adapter_size.add_size(size);
                }
            }
        });
        tw_choose_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditProductActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_choose_category, null);
                dialogBuilder.setView(dialogView);

                RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.recyclerview_row_choose_category);
                TextView tw = (TextView) dialogView.findViewById(R.id.path_row_choose_category);
                adapter_categories = new CategoryListAdapter(EditProductActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EditProductActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter_categories);

                new getCategories(list_p.get(0).getMobile() , "0").execute();

                final AlertDialog alertDialog = dialogBuilder.create();

                adapter_categories.setOnItemClickListener(new CategoryListAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, CategoriesList categoriesList) {

                        if (categoriesList.num_of_subs.equals("0")){
                            tw_choose_category.setText(categoriesList.getName());
                            tw_choose_category.setTag(categoriesList.getId());
                            alertDialog.dismiss();
                        }else {
                            new getCategories(list_p.get(0).getMobile(), categoriesList.getId()).execute();
                        }
//                        s_title = categoriesList.getName();
//                        path_father.add(categoriesList.getParent_category());
//                        path__current_names.add(categoriesList.getName());
//                        path_current.add(categoriesList.getId());
                    }
                });



//                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialog.show();
            }
        });

        btn_add_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String properties = edt_property_properties.getText().toString().trim();
                String description = edt_property_descrip.getText().toString().trim();

                if (!description.equals("") && !properties.equals("")){
                    adapter_property.add_row(properties , description);
                }

            }
        });

        adapter_property.setOnItemClickListener(new PropertyAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, String properties, String descrip) {
                adapter_property.delete_row(position);
            }
        });







        edt_name_vahed.setText(list_p.get(0).getName_vahed());
        edt_gheymate_vahed.setText(list_p.get(0).getGheymate_vahed());
        edt_vahede_farei.setText(list_p.get(0).getVahede_zaribe_sefaresh());
        edt_zaribe_vahede_farei.setText(list_p.get(0).getZaribe_sefaresh());


        adapter_photos.add_list(list_of_photos);


        adapter_tedad.add_list(list_p.get(0).getTedad_az() , list_p.get(0).getTedad_ta() , list_p.get(0).getTedad_gheymat());

        adapter_property.add_list(list_p.get(0).getProperty_name() , list_p.get(0).getProperty_description());

        tw_choose_category.setText(list_p.get(0).getCategory_name());
        adapter_size.add_list(list_p.get(0).getSizes());

        adapter.add_list(list_p.get(0).getColors());

        edt_code.setText(list_p.get(0).getCode());
        edt_mahiat.setText(list_p.get(0).getMahiat_titr());
        edt_jensiat.setText(list_p.get(0).getJensiat_titr());
        edt_brand.setText(list_p.get(0).getBrand_titr());
        edt_model.setText(list_p.get(0).getModel_titr());
        edt_vizhegi_titr.setText(list_p.get(0).getVizhegi_titr());
        edt_size_titr.setText(list_p.get(0).getSize_titr());
        edt_tozihat.setText(list_p.get(0).getTozihat());


















        tw_colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditProductActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_colors, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();

//                List<String> colors_list = new ArrayList<>();
                RecyclerView recycler_colors = (RecyclerView) dialogView.findViewById(R.id.recycler_colors);
                Button submit_colors = (Button)dialogView.findViewById(R.id.submit_colors_alert);

                recycler_colors.setLayoutManager(new LinearLayoutManager(EditProductActivity.this , LinearLayoutManager.HORIZONTAL , false));
                recycler_colors.setAdapter(adapter);




                submit_colors.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                adapter.setOnItemClickListener(new ColorsAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, String color) {
                        adapter.delete_color(position);
                    }
                });











                final CircleImageView c1_1 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_1);
                final CircleImageView c1_2 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_2);
                final CircleImageView c1_3 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_3);
                final CircleImageView c1_4 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_4);
                final CircleImageView c1_5 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_5);
                final CircleImageView c1_6 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_6);
                final CircleImageView c1_7 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_7);
                final CircleImageView c1_8 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_8);
                final CircleImageView c1_9 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_9);
                final CircleImageView c1_10 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_10);
                final CircleImageView c1_11 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_11);
                final CircleImageView c1_12 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_12);
                final CircleImageView c1_13 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_13);
                final CircleImageView c2_1 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_1);
                final CircleImageView c2_2 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_2);
                final CircleImageView c2_3 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_3);
                final CircleImageView c2_4 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_4);
                final CircleImageView c2_5 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_5);
                final CircleImageView c2_6 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_6);
                final CircleImageView c2_7 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_7);
                final CircleImageView c2_8 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_8);
                final CircleImageView c2_9 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_9);
                final CircleImageView c2_10 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_10);
                final CircleImageView c2_11 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_11);
                final CircleImageView c2_12 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_12);
                final CircleImageView c2_13 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_13);
                final CircleImageView c3_1 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_1);
                final CircleImageView c3_2 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_2);
                final CircleImageView c3_3 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_3);
                final CircleImageView c3_4 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_4);
                final CircleImageView c3_5 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_5);
                final CircleImageView c3_6 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_6);
                final CircleImageView c3_7 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_7);
                final CircleImageView c3_8 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_8);
                final CircleImageView c3_9 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_9);
                final CircleImageView c3_10 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_10);
                final CircleImageView c3_11 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_11);
                final CircleImageView c3_12 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_12);
                final CircleImageView c3_13 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_13);
                final CircleImageView c4_1 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_1);
                final CircleImageView c4_2 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_2);
                final CircleImageView c4_3 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_3);
                final CircleImageView c4_4 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_4);
                final CircleImageView c4_5 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_5);
                final CircleImageView c4_6 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_6);
                final CircleImageView c4_7 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_7);
                final CircleImageView c4_8 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_8);
                final CircleImageView c4_9 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_9);
                final CircleImageView c4_10 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_10);
                final CircleImageView c4_11 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_11);
                final CircleImageView c4_12 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_12);
                final CircleImageView c4_13 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_13);
                final CircleImageView c5_1 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_1);
                final CircleImageView c5_2 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_2);
                final CircleImageView c5_3 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_3);
                final CircleImageView c5_4 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_4);
                final CircleImageView c5_5 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_5);
                final CircleImageView c5_6 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_6);
                final CircleImageView c5_7 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_7);
                final CircleImageView c5_8 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_8);
                final CircleImageView c5_9 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_9);
                final CircleImageView c5_10 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_10);
                final CircleImageView c5_11 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_11);
                final CircleImageView c5_12 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_12);
                final CircleImageView c5_13 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_13);





                c1_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_1.getTag().toString());
                    }
                });

                c1_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_2.getTag().toString());
                    }
                });
                c1_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_3.getTag().toString());
                    }
                });
                c1_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_4.getTag().toString());
                    }
                });
                c1_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_5.getTag().toString());
                    }
                });
                c1_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_6.getTag().toString());
                    }
                });
                c1_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_7.getTag().toString());
                    }
                });
                c1_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_8.getTag().toString());
                    }
                });

                c1_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_9.getTag().toString());
                    }
                });
                c1_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_10.getTag().toString());
                    }
                });
                c1_11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_11.getTag().toString());
                    }
                });
                c1_12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_12.getTag().toString());
                    }
                });
                c1_13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_13.getTag().toString());
                    }
                });
                c2_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_1.getTag().toString());
                    }
                });
                c2_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_2.getTag().toString());
                    }
                });

                c2_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_3.getTag().toString());
                    }
                });
                c2_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_4.getTag().toString());
                    }
                });
                c2_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_5.getTag().toString());
                    }
                });
                c2_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_6.getTag().toString());
                    }
                });
                c2_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_7.getTag().toString());
                    }
                });
                c2_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_8.getTag().toString());
                    }
                });
                c2_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_9.getTag().toString());
                    }
                });

                c2_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_10.getTag().toString());
                    }
                });
                c2_11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_11.getTag().toString());
                    }
                });
                c2_12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_12.getTag().toString());
                    }
                });
                c2_13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_13.getTag().toString());
                    }
                });
                c3_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_1.getTag().toString());
                    }
                });
                c3_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_2.getTag().toString());
                    }
                });
                c3_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_3.getTag().toString());
                    }
                });

                c3_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_4.getTag().toString());
                    }
                });
                c3_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_5.getTag().toString());
                    }
                });
                c3_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_6.getTag().toString());
                    }
                });
                c3_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_7.getTag().toString());
                    }
                });
                c3_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_8.getTag().toString());
                    }
                });
                c3_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_9.getTag().toString());
                    }
                });
                c3_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_10.getTag().toString());
                    }
                });

                c3_11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_11.getTag().toString());
                    }
                });
                c3_12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_12.getTag().toString());
                    }
                });
                c3_13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_13.getTag().toString());
                    }
                });
                c4_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_1.getTag().toString());
                    }
                });
                c4_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_2.getTag().toString());
                    }
                });
                c4_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_3.getTag().toString());
                    }
                });
                c4_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_4.getTag().toString());
                    }
                });

                c4_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_5.getTag().toString());
                    }
                });
                c4_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_6.getTag().toString());
                    }
                });
                c4_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_7.getTag().toString());
                    }
                });
                c4_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_8.getTag().toString());
                    }
                });
                c4_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_9.getTag().toString());
                    }
                });
                c4_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_10.getTag().toString());
                    }
                });
                c4_11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_11.getTag().toString());
                    }
                });

                c4_12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_12.getTag().toString());
                    }
                });
                c4_13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_13.getTag().toString());
                    }
                });
                c5_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_1.getTag().toString());
                    }
                });
                c5_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_2.getTag().toString());
                    }
                });
                c5_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_3.getTag().toString());
                    }
                });
                c5_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_4.getTag().toString());
                    }
                });
                c5_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_5.getTag().toString());
                    }
                });

                c5_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_6.getTag().toString());
                    }
                });
                c5_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_7.getTag().toString());
                    }
                });
                c5_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_8.getTag().toString());
                    }
                });
                c5_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_9.getTag().toString());
                    }
                });
                c5_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_10.getTag().toString());
                    }
                });
                c5_11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_11.getTag().toString());
                    }
                });
                c5_12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_12.getTag().toString());
                    }
                });

                c5_13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_13.getTag().toString());
                    }
                });





                alertDialog.show();
            }
        });







    }






    Uri imageUri;
    private void CameraOpen() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.INT_CAMERA);
            }
        }
        else {
            dispatchTakePictureIntent();
        }
    }

    private void GalleryOpen() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){

                GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),Constants.INT_PICK_GALLERY);

            } else {

                ActivityCompat.requestPermissions(EditProductActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.INT_PICK_GALLERY);
            }
        }
        else {
            GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),Constants.INT_PICK_GALLERY);
        }
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.INT_CAMERA) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                dispatchTakePictureIntent();

            } else {
                Toast.makeText(EditProductActivity.this, "دسترسی داده نشد", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constants.INT_PICK_GALLERY) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),Constants.INT_PICK_GALLERY);


            } else {

                Toast.makeText(EditProductActivity.this, "دسترسی خواندن عکس از حافظه داده نشد!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    List<String> encoded_photos_list;
    String encoded_image;
    Uri final_image;
    Uri selectedImage;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Constants.INT_CAMERA && resultCode == RESULT_OK){


            getContentResolver().notifyChange(selectedImage, null);
            ContentResolver cr = getContentResolver();
            int orientation = 0;
            ExifInterface ei = null;
            try {
                ei = new ExifInterface(photoFile.getPath());
                orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
            } catch(Exception e) {
                Log.e("logtagAddAdv", e.toString());
            }
            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    bitmap = bitmap;
            }

            try{
                Intent CropIntent;
                CropIntent = new Intent("com.android.camera.action.CROP");
                CropIntent.setDataAndType(getImageUri(bitmap),"image/*");
                CropIntent.putExtra("crop","true");
                CropIntent.putExtra("outputX",480);
                CropIntent.putExtra("outputY",480);
                CropIntent.putExtra("aspectX",8);
                CropIntent.putExtra("aspectY",8);
                CropIntent.putExtra("scaleUpIfNeeded",true);

                String rndm = getRandomString();
                File f = new File(Environment.getExternalStorageDirectory()+"/"+rndm+"/");
                final_image = Uri.fromFile(f);
                CropIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, final_image);
                startActivityForResult(CropIntent, Constants.INT_CROP);
            }
            catch (ActivityNotFoundException anfe) {
                String errorMessage = "Your device doesn't support the crop action!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }

        }
        else if (requestCode == Constants.INT_CROP && resultCode == RESULT_OK){
//            adapter_photos.add_row(final_image);
            encoded_image = getEncodedImage(final_image);
            new add_delete_photos(list_p.get(0).getMobile() , s_code , encoded_image).execute();
//            encoded_photos_list.add(encoded_image);
//            Log.d("photo is  : " , " "+encoded_image);
        }
        else if (requestCode == Constants.INT_PICK_GALLERY && resultCode == RESULT_OK){

            try {
                Uri filePath = data.getData();
                //..First convert the Image to the allowable size so app do not throw Memory_Out_Bound Exception
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath), null, options);
                int resolution = 500;
                options.inSampleSize = calculateInSampleSize(options, resolution  , resolution);
                options.inJustDecodeBounds = false;
                Bitmap bitmap1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath), null, options);
                //...Now You have the 'bitmap' to rotate....
                //...Rotate the bitmap to its original Orientation...
                bitmap = ImageOrientation.modifyOrientation(getApplicationContext(),bitmap1,filePath);
                try{
                    Intent CropIntent;
                    CropIntent = new Intent("com.android.camera.action.CROP");
                    CropIntent.setDataAndType(getImageUri(bitmap),"image/*");
                    CropIntent.putExtra("crop","true");
                    CropIntent.putExtra("outputX",480);
                    CropIntent.putExtra("outputY",480);
                    CropIntent.putExtra("aspectX",8);
                    CropIntent.putExtra("aspectY",8);
                    CropIntent.putExtra("scaleUpIfNeeded",true);

                    String rndm = getRandomString();
                    File f = new File(Environment.getExternalStorageDirectory()+"/"+rndm+"/");
                    final_image = Uri.fromFile(f);
                    CropIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, final_image);
                    startActivityForResult(CropIntent, Constants.INT_CROP);


                }catch (ActivityNotFoundException ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                Log.d("Image_exception",e.toString());
            }
        }

    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    String currentPhotoPath;
    File image;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    File photoFile;
    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(EditProductActivity.this.getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(EditProductActivity.this, "ehsanfatahizadehgmail.com.jinni.fileprovider", photoFile);
                    selectedImage = imageUri;
                } else {
                    imageUri = Uri.fromFile(photoFile);
                    selectedImage = imageUri;
                }
                takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, Constants.INT_CAMERA);
            }
        }
    }

    Bitmap bitmap;

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    protected String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }


    public String getEncodedImage(Uri uri){
        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver() ,uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



































    List<CategoriesList> list = new ArrayList<CategoriesList>();

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
                    Toast.makeText(EditProductActivity.this, "errrror", Toast.LENGTH_SHORT).show();
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

//            int num = path_father.size();
//            Log.d("size of list " , String.valueOf(list.size()));
            if (!list.isEmpty()) {
                adapter_categories.add_list(list);
            }
            dialog.dismiss();

//            if (num != 0){
//                title.setText(path__current_names.get(path__current_names.size()-1));
//                back_cate.setVisibility(View.VISIBLE);
//            }else {
//                title.setText("دسته بندی اصلی");
//                back_cate.setVisibility(View.GONE);
//            }


        }
    }








    public class add_delete_photos extends AsyncTask {

        String mobile;
        String code;
        String address;
        public add_delete_photos(String mobile , String code , String address){
            this.mobile = mobile;
            this.code = code;
            this.address = address;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        List<PhotoAddress> result;

        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<PhotoAddress>> call = apis.delete_remove_photo(mobile , code , address);

            try {
                Response<List<PhotoAddress>> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(EditProductActivity.this, "errrror", Toast.LENGTH_SHORT).show();
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

            if (result != null){

                List<String> new_list = new ArrayList<>();
                for (int i =0 ; i < result.size() ; i++){
                    new_list.add(result.get(i).getAddress());
                }
                list_of_photos = new_list;
                adapter_photos.add_list(new_list);

            }else{
                Toast.makeText(EditProductActivity.this, "خطای ارتباط با سرور!", Toast.LENGTH_SHORT).show();
            }



        }
    }







    public class thread_send_edit_product extends AsyncTask {


        String json_new_p;

        public thread_send_edit_product(String json_new_p){
            this.json_new_p = json_new_p;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        String result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<String> call = apis.sendEditProduct(json_new_p);

            try {
                Response<String> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(EditProductActivity.this, "errrror", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(EditProductActivity.this, "اطلاعات با موفقیت ارسال شد.", Toast.LENGTH_SHORT).show();
                    relativeLayout_photos.setVisibility(View.VISIBLE);
                }
            }else {
                Toast.makeText(EditProductActivity.this, "خطا در ارسال اطلاعات \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }











    List<String> final_color_list;
    List<String> final_sizes_list;
    List<String> final_descrip_list;
    List<String> final_properties_list;
    List<String> final_tedad_az_lis;
    List<String> final_tedad_ta_list;
    List<String> final_tedad_price_list;


    String s_code;
    void Validate(){




        s_code = edt_code.getText().toString().trim();
        if (s_code.equals("")){

            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 10) { // length of the random string.
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            s_code = salt.toString();
        }


        String s_mahiat = edt_mahiat.getText().toString().trim();
        String s_jensiat = edt_jensiat.getText().toString().trim();
        String s_brand = edt_brand.getText().toString().trim();
        String s_model = edt_model.getText().toString().trim();
        String s_special_property = edt_vizhegi_titr.getText().toString().trim();
        String s_size_titr = edt_size_titr.getText().toString().trim();


        final_color_list = adapter.colors;
        final_sizes_list = adapter_size.sizes;


        String s_category = tw_choose_category.getText().toString();
        String s_description = edt_tozihat.getText().toString().trim();


        final_descrip_list = adapter_property.list_descrip;
        final_properties_list = adapter_property.list_properties;


        String s_haraji;
        String s_special;
        if (checkBox_haraji.isChecked()){
            s_haraji = edt_haraji.getText().toString().trim();
        }else{
            s_haraji = "no";
        }
        if (checkBox_special.isChecked()){
            s_special = "yes";
        }else{
            s_special = "no";
        }

        String s_name_vahed = edt_name_vahed.getText().toString().trim();
        String s_gheymate_vahed = edt_gheymate_vahed.getText().toString().trim();
        String s_vahede_zaribe_sefaresh = edt_vahede_farei.getText().toString().trim();
        String s_zaribe_sefaresh = edt_zaribe_vahede_farei.getText().toString().trim();


        final_tedad_az_lis = adapter_tedad.list_az;
        final_tedad_ta_list = adapter_tedad.list_ta;
        final_tedad_price_list = adapter_tedad.list_price;


//        encoded_photos_list;

//        if (s_mahiat.equals("")){
//            s_mahiat = "nothing";
//        }else if (s_jensiat.equals("")){
//            s_jensiat = "nothing";
//        }else if (s_brand.equals("")){
//            s_brand = "nothing";
//        }else if (s_model.equals("")){
//            s_model = "nothing";
//        }else if (s_special_property.equals("")){
//            s_special_property = "nothing";
//        }else if (s_size_titr.equals("")){
//            s_size_titr = "nothing";
//        }else if (s_jensiat.equals("")){
//            s_jensiat = "nothing";
//        }else if (s_jensiat.equals("")){
//            s_jensiat = "nothing";
//        }else if (s_jensiat.equals("")){
//            s_jensiat = "nothing";
//        }else if (s_jensiat.equals("")){
//            s_jensiat = "nothing";
//        }








//        Log.d("s_mahiat" , s_mahiat);
//        Log.d("s_jensiat" ,s_jensiat );
//        Log.d("s_brand" ,s_brand );
//        Log.d("s_model" , s_model);
//        Log.d("s_special_property" , s_special_property);
//        Log.d("s_size_titr" , s_size_titr);
//
//        Log.d("final_color_list" , final_color_list.toString());
//        Log.d("final_sizes_list" ,final_sizes_list.toString() );
//
//        Log.d("s_category" , s_category);
//        Log.d("s_description" ,s_description );
//
//        Log.d("final_descrip_list" , final_descrip_list.toString());
//        Log.d("final_properties_list" , final_properties_list.toString());
//
//        Log.d("s_haraji" , s_haraji);
//        Log.d("s_special" , s_special);
//
//        Log.d("s_name_vahed" , s_name_vahed);
//        Log.d("s_gheymate_vahed" ,s_gheymate_vahed );
//        Log.d("s_vahede_zaribe_se" ,s_vahede_zaribe_sefaresh );
//        Log.d("s_zaribe_sefaresh" ,s_zaribe_sefaresh );
//
//        Log.d("final_tedad_az_lis" ,final_tedad_az_lis.toString() );
//        Log.d("final_tedad_ta_list" , final_tedad_ta_list.toString());
//        Log.d("final_tedad_price_list" , final_tedad_price_list.toString());
//        Log.d("encoded_photos_list" , encoded_photos_list.toString());



        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray_colors;
        try {

            jsonArray_colors = new JSONArray();
            for (int i=0 ; i < final_color_list.size() ; i++){
                JSONObject jo = new JSONObject();
                jo.put("name" , final_color_list.get(i));
                jsonArray_colors.put(jo);
            }


            JSONArray jsonArray_sizes;
            jsonArray_sizes = new JSONArray();
            for (int i=0 ; i < final_sizes_list.size() ; i++){
                JSONObject jo = new JSONObject();
                jo.put("name" , final_sizes_list.get(i));
                jsonArray_sizes.put(jo);
            }



            JSONArray jsonArray_property;
            jsonArray_property = new JSONArray();
            for (int i=0 ; i < final_properties_list.size() ; i++){
                JSONObject jo = new JSONObject();
                jo.put("name" , final_properties_list.get(i));
                jo.put("description" , final_descrip_list.get(i));
                jsonArray_property.put(jo);
            }



            JSONArray jsonArray_prices;
            jsonArray_prices = new JSONArray();
            for (int i=0 ; i < final_tedad_az_lis.size() ; i++){
                JSONObject jo = new JSONObject();
                jo.put("az" , final_tedad_az_lis.get(i));
                jo.put("ta" , final_tedad_ta_list.get(i));
                jo.put("price" , final_tedad_price_list.get(i));
                jsonArray_prices.put(jo);
            }


//            JSONArray jsonArray_photos;
//            jsonArray_photos = new JSONArray();
//            for (int i=0 ; i < encoded_photos_list.size() ; i++){
//                JSONObject jo = new JSONObject();
//                jo.put("photo" , encoded_photos_list.get(i));
//                jsonArray_photos.put(jo);
//            }



            jsonObject.put("mobile",list_p.get(0).getMobile());
            jsonObject.put("last_code",list_p.get(0).getCode());
            jsonObject.put("code",s_code);
            jsonObject.put("mahiat",s_mahiat);
            jsonObject.put("jensiat",s_jensiat);
            jsonObject.put("brand",s_brand);
            jsonObject.put("model",s_model);
            jsonObject.put("special_property",s_special_property);
            jsonObject.put("size_titr",s_size_titr);
            jsonObject.put("colors" , jsonArray_colors);
            jsonObject.put("sizes" , jsonArray_sizes);
            jsonObject.put("category" , s_category);
            jsonObject.put("description" , s_description);
            jsonObject.put("properties" , jsonArray_property);
            jsonObject.put("haraji" , s_haraji);
            jsonObject.put("special" , s_special);
            jsonObject.put("name_vahed" , s_name_vahed);
            jsonObject.put("gheymate_vahed" , s_gheymate_vahed);
            jsonObject.put("vahede_zaribe_se" , s_vahede_zaribe_sefaresh);
            jsonObject.put("zaribe_sefaresh" , s_zaribe_sefaresh);
            jsonObject.put("prices" , jsonArray_prices);
//            jsonObject.put("photos" , jsonArray_photos);


            Log.d("Array is ", jsonObject.toString());








        } catch (JSONException e) {
            e.printStackTrace();
        }


        new thread_send_edit_product(jsonObject.toString()).execute();





    }


















}
