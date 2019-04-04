package ehsanfatahizadehgmail.com.jinni;



import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;


public class NewProductActivity extends AppCompatActivity {

    ProgressDialog dialog;
    ApiInterface apis;
    RetrofitSetting retrofit;

    EditText mahiat;
    EditText jensiat;
    EditText brand;
    EditText model;
    EditText special_property;
    EditText size_titr;

    TextView colors;
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


    CheckBox checkbox_haraji , checkbox_special;
    EditText old_price;



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

    Button btn_send_info;


    EditText description;
    EditText name_vahed , gheymate_vahed , vahede_zaribe_sefaresh , zaribe_sefaresh;



    List<String> final_color_list;
    List<String> final_sizes_list;
    List<String> final_descrip_list;
    List<String> final_properties_list;
    List<String> final_tedad_az_lis;
    List<String> final_tedad_ta_list;
    List<String> final_tedad_price_list;



    String mobile;



    CheckBox checkBox_code;
    EditText edt_code;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        encoded_photos_list = new ArrayList<>();

        checkBox_code = (CheckBox) findViewById(R.id.checkbox_code_new_p);
        edt_code = (EditText) findViewById(R.id.edittext_code_new_p);


        checkBox_code.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edt_code.setVisibility(View.VISIBLE);
                }else{
                    edt_code.setVisibility(View.GONE);
                }
            }
        });




        name_vahed = (EditText) findViewById(R.id.name_vahed_new_p);
        gheymate_vahed = (EditText) findViewById(R.id.price_har_vahed_new_p);
        vahede_zaribe_sefaresh = (EditText) findViewById(R.id.vahed_zarib_sefaresh_new_p);
        zaribe_sefaresh = (EditText) findViewById(R.id.zarib_sefaresh_new_p);




        mahiat = (EditText) findViewById(R.id.mahiat_edittext_new_p);
        jensiat = (EditText) findViewById(R.id.jensiat_edittext_new_p);
        brand = (EditText) findViewById(R.id.brand_edittext_new_p);
        model = (EditText) findViewById(R.id.model_edittext_new_p);
        special_property = (EditText) findViewById(R.id.special_property_edittext_new_p);
        size_titr = (EditText) findViewById(R.id.size_titr_edittext_new_p);
        description = (EditText) findViewById(R.id.description_edittext_new_p);


        btn_send_info = (Button) findViewById(R.id.button_new_p_final_sabt);
        btn_send_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });

        tw_choose_category = (TextView) findViewById(R.id.add_to_category_edittext_new_p);
        btn_add_size = (Button) findViewById(R.id.button_add_size_new_p);
        edt_new_size = (EditText) findViewById(R.id.edittext_size_new_p);
        recycler_sizes = (RecyclerView) findViewById(R.id.recycler_size_new_p);



        recycler_photos = (RecyclerView) findViewById(R.id.recycler_photos_new_p);
        btn_add_photo = (Button) findViewById(R.id.button_add_pictures);
        recycler_photos.setLayoutManager(new LinearLayoutManager(NewProductActivity.this, LinearLayoutManager.HORIZONTAL, false));
        adapter_photos = new PhotosProductAdapter(NewProductActivity.this);
        recycler_photos.setAdapter(adapter_photos);


        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(NewProductActivity.this);
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



        btn_add_prices = (Button) findViewById(R.id.button_add_row_price_table);
        edt_az_tedad = (EditText) findViewById(R.id.edt_new_p_az_row);
        edt_ta_tedad = (EditText) findViewById(R.id.edt_new_p_ta_row);
        edt_gheymat_tedad = (EditText) findViewById(R.id.edt_new_p_price_row);
        recycler_tedad = (RecyclerView) findViewById(R.id.recyclerview_big_price);
        recycler_tedad.setLayoutManager(new LinearLayoutManager(NewProductActivity.this));
        adapter_tedad = new PriceTedadAdapter(NewProductActivity.this);
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




        checkbox_haraji = (CheckBox) findViewById(R.id.checkbox_cheap);
        checkbox_special = (CheckBox) findViewById(R.id.checkbox_special);
        old_price = (EditText) findViewById(R.id.old_price_new_p);



        checkbox_haraji.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    old_price.setVisibility(View.VISIBLE);
                }else{
                    old_price.setVisibility(View.GONE);
                }
            }
        });


        btn_add_property = (Button) findViewById(R.id.button_add_property);
        edt_property_properties = (EditText) findViewById(R.id.edt_table_properties);
        edt_property_descrip = (EditText) findViewById(R.id.edt_table_description);
        recycler_property = (RecyclerView) findViewById(R.id.recyclerview_property__table);
        recycler_property.setLayoutManager(new LinearLayoutManager(NewProductActivity.this));
        adapter_property = new PropertyAdapter(NewProductActivity.this);
        recycler_property.setAdapter(adapter_property);




        dialog = new ProgressDialog(NewProductActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");
        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();


        SharedPreferences sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        mobile = sh.getString("mobile" , null);




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




        adapter_size = new SizesAdapter(NewProductActivity.this , 1);
        recycler_sizes.setLayoutManager(new LinearLayoutManager(NewProductActivity.this , LinearLayoutManager.HORIZONTAL , false));
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



                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NewProductActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_choose_category, null);
                dialogBuilder.setView(dialogView);

                RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.recyclerview_row_choose_category);
                TextView tw = (TextView) dialogView.findViewById(R.id.path_row_choose_category);
                adapter_categories = new CategoryListAdapter(NewProductActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewProductActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter_categories);

                new getCategories(mobile , "0").execute();

                final AlertDialog alertDialog = dialogBuilder.create();

                adapter_categories.setOnItemClickListener(new CategoryListAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, CategoriesList categoriesList) {

                        if (categoriesList.num_of_subs.equals("0")){
                            tw_choose_category.setText(categoriesList.getName());
                            tw_choose_category.setTag(categoriesList.getId());
                            alertDialog.dismiss();
                        }else {
                            new getCategories(mobile, categoriesList.getId()).execute();
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







        colors = (TextView) findViewById(R.id.colors_new_p);

        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NewProductActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_colors, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();

//                List<String> colors_list = new ArrayList<>();
                RecyclerView recycler_colors = (RecyclerView) dialogView.findViewById(R.id.recycler_colors);
                Button submit_colors = (Button)dialogView.findViewById(R.id.submit_colors_alert);
                adapter = new ColorsAdapter(NewProductActivity.this);
                recycler_colors.setLayoutManager(new LinearLayoutManager(NewProductActivity.this , LinearLayoutManager.HORIZONTAL , false));
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
                    Toast.makeText(NewProductActivity.this, "errrror", Toast.LENGTH_SHORT).show();
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

                ActivityCompat.requestPermissions(NewProductActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.INT_PICK_GALLERY);
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
                Toast.makeText(NewProductActivity.this, "دسترسی داده نشد", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constants.INT_PICK_GALLERY) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),Constants.INT_PICK_GALLERY);


            } else {

                Toast.makeText(NewProductActivity.this, "دسترسی خواندن عکس از حافظه داده نشد!!", Toast.LENGTH_SHORT).show();
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
            adapter_photos.add_row(final_image);
            encoded_image = getEncodedImage(final_image);
            encoded_photos_list.add(encoded_image);
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
        if (takePictureIntent.resolveActivity(NewProductActivity.this.getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(NewProductActivity.this, "ehsanfatahizadehgmail.com.jinni.fileprovider", photoFile);
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








    void Validate(){

        checkBox_code = (CheckBox) findViewById(R.id.checkbox_code_new_p);
        String code;
        if (checkBox_code.isChecked()){
            code = edt_code.getText().toString().trim();
        }else {
            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 10) { // length of the random string.
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            code = salt.toString();
        }


        String s_mahiat = mahiat.getText().toString().trim();
        String s_jensiat = jensiat.getText().toString().trim();
        String s_brand = brand.getText().toString().trim();
        String s_model = model.getText().toString().trim();
        String s_special_property = special_property.getText().toString().trim();
        String s_size_titr = size_titr.getText().toString().trim();


        final_color_list = adapter.colors;
        final_sizes_list = adapter_size.sizes;


        String s_category = tw_choose_category.getTag().toString();
        String s_description = description.getText().toString().trim();


        final_descrip_list = adapter_property.list_descrip;
        final_properties_list = adapter_property.list_properties;


        String s_haraji;
        String s_special;
        if (checkbox_haraji.isChecked()){
            s_haraji = old_price.getText().toString().trim();
        }else{
            s_haraji = "no";
        }
        if (checkbox_special.isChecked()){
            s_special = "yes";
        }else{
            s_special = "no";
        }

        String s_name_vahed = name_vahed.getText().toString().trim();
        String s_gheymate_vahed = gheymate_vahed.getText().toString().trim();
        String s_vahede_zaribe_sefaresh = vahede_zaribe_sefaresh.getText().toString().trim();
        String s_zaribe_sefaresh = zaribe_sefaresh.getText().toString().trim();


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


            JSONArray jsonArray_photos;
            jsonArray_photos = new JSONArray();
            for (int i=0 ; i < encoded_photos_list.size() ; i++){
                JSONObject jo = new JSONObject();
                jo.put("photo" , encoded_photos_list.get(i));
                jsonArray_photos.put(jo);
            }



            jsonObject.put("mobile",mobile);
            jsonObject.put("code",code);
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
            jsonObject.put("photos" , jsonArray_photos);


            Log.d("Array is ", jsonObject.toString());








        } catch (JSONException e) {
            e.printStackTrace();
        }


        new thread_send_new_product(jsonObject.toString()).execute();





    }












    public class thread_send_new_product extends AsyncTask {


        String json_new_p;

        public thread_send_new_product(String json_new_p){

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
            Call<String> call = apis.sendNewProduct(json_new_p);

            try {
                Response<String> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(NewProductActivity.this, "errrror", Toast.LENGTH_SHORT).show();
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
                    finish();
                    Toast.makeText(NewProductActivity.this, "اطلاعات با موفقیت ارسال شد.", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(NewProductActivity.this, "خطا در ارسال اطلاعات \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }










}
