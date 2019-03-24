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
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.Random;

import ehsanfatahizadehgmail.com.jinni.adapters.SenfSpinnerAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.libs.BaseActivity;
import ehsanfatahizadehgmail.com.jinni.libs.ImageOrientation;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;



public class SignUpActivity extends BaseActivity {


    String[] ostanha =new String[]{"انتخاب استان:","آذربایجان شرقی","آذربایجان غربی","اردبیل","اصفهان","البرز","ایلام",
            "بوشهر","تهران","چهارمحال و بختیاری","خراسان جنوبی","خراسان رضوی","خراسان شمالی","خوزستان","زنجان","سمنان",
            "سیستان و بلوچستان","فارس","قزوین","قم","کردستان","کرمان","کرمانشاه","کهگیلویه و بویر احمد","گلستان","گیلان",
            "لرستان","مازندران","مرکزی","هرمزگان","همدان","یزد"};


    String[] codhaye_ostan =new String[]{"کد استان","041","044","045","031","026","084",
            "077","021","038","056","051","058","061","024","023",
            "054","071","028","025","087","034","083","074","017","013",
            "066","011","086","076","081","035"};

    String[] categories = new String[]{"انتخاب صنف:","موبایل و تبلت","لپ تاپ","دوربین","کامپیوتر و تجهیزات جانبی","ماشین های اداری","کنسول های بازی و لوازم جانبی","لوازم جانبی کالاهای دیجیتال",
            "آرایشی ، بهداشتی و سلامت","لوازم جانبی و مصرفی خودرو و موتور سیکلت","ابزار آلات (برقی و غیر برقی ، ساختمانی ، باغبانی و ...)",
            "پوشاک مردانه","پوشاک زنانه","پوشاک نوزاد و بچه گانه","اکسسوری و لوازم شخصی","کیف ، کفش و کتانی" , "لوازم و دکوراسیون اداری","لوازم خانگی برقی ، صوتی و تصویری","آشپزخانه و پخت و پز ، ظروف پذیرایی","مبلمان و دکراتیو",
    "نور و روشنایی","فرش ، گلیم و تابلو فرش","شستشو و نظافت","کیف و لوازم التحریر","صنایع دستی و نوآوری","آلات و لوازم موسیقی","پوشاک و کفش ورزشی",
    "لوازم ورزشی ، کمپ و کوهنوردی","دوچرخه و لوازم جانبی","اسباب بازی و سرگرمی","کالاهای اساسی و خواروبار","تنقلات ، نوشیدنی و ...","متفرقه"};






    ApiInterface apis;
    RetrofitSetting retrofit;
    ProgressDialog dialog;


    TextInputEditText name_collection , manager , address;
    Intent GalIntent;
    ImageView img;
    String mobile;


    JSONObject jsonObjectCities;
    ArrayList<String> cities;
    Spinner shahrha_spinner;
    Spinner ostanha_spinner;
    Spinner categories_spinner;


    TextView pish_shomare;
    TextView telephone_sabet;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name_collection = (TextInputEditText) findViewById(R.id.name_collec_sign_up);
        manager = (TextInputEditText) findViewById(R.id.manager_sign_up);
        address = (TextInputEditText) findViewById(R.id.adress_sign_up);
        Button btn_pickPic = (Button) findViewById(R.id.upload_pic_sign_up);
        Button btn_sabt = (Button) findViewById(R.id.btn_sabt_sign_up);
        img = (ImageView) findViewById(R.id.imageview_Signup);
        pish_shomare = (TextView)findViewById(R.id.txt_pish_shomare_signup);
        telephone_sabet = (TextView)findViewById(R.id.edittext_telephone_signup);




        cities = new ArrayList<String>();
        cities.add("انتخاب شهر:");


        shahrha_spinner = findViewById(R.id.spinner_shahr);
        ostanha_spinner = findViewById(R.id.spinner_ostan);
        categories_spinner = (Spinner)findViewById(R.id.spinner_categories);


        ArrayAdapter<String> adapter_ostan = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ostanha);
        adapter_ostan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ostanha_spinner.setAdapter(adapter_ostan);


        ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(SignUpActivity.this,android.R.layout.simple_spinner_item, cities);
        adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shahrha_spinner.setAdapter(adapter_city);

//        ArrayAdapter<String> adapter_categories = new ArrayAdapter<String>(SignUpActivity.this,android.R.layout.simple_spinner_item, categories);
        SenfSpinnerAdapter mySpinnerArrayAdapter = new SenfSpinnerAdapter(SignUpActivity.this ,android.R.layout.simple_spinner_item,categories);
//        mySpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories_spinner.setAdapter(mySpinnerArrayAdapter);

        categories_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 1 && position <= 7) {
                    categories_spinner.setBackgroundColor(0Xffcccccc);
                }else if (position == 8  ) {
                    categories_spinner.setBackgroundColor(0Xffeeeeee);
                }else if (position == 9  ) {
                    categories_spinner.setBackgroundColor(0Xffcccccc);
                }else if (position == 10 ) {
                    categories_spinner.setBackgroundColor(0Xffeeeeee);
                }else if (position >= 11 && position <= 15 ) {
                    categories_spinner.setBackgroundColor(0Xffcccccc);
                }else if (position == 16  ) {
                    categories_spinner.setBackgroundColor(0Xffeeeeee);
                }else if (position >= 17 && position <= 22 ) {
                    categories_spinner.setBackgroundColor(0Xffcccccc);
                }else if (position >= 23 && position <= 25 ) {
                    categories_spinner.setBackgroundColor(0Xffeeeeee);
                }else if (position >= 26 && position <= 28 ) {
                    categories_spinner.setBackgroundColor(0Xffcccccc);
                }else if (position == 29  ) {
                    categories_spinner.setBackgroundColor(0Xffeeeeee);
                }else if (position >= 30 && position <= 31 ) {
                    categories_spinner.setBackgroundColor(0Xffcccccc);
                }else if (position == 32 ) {
                    categories_spinner.setBackgroundColor(0Xffeeeeee);
                }





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



//
//
//        senf_txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SignUpActivity.this);
//                LayoutInflater inflater =getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.alert_senf, null);
//                dialogBuilder.setView(dialogView);
//                final AlertDialog alertDialog = dialogBuilder.create();
//                final RecyclerView recycler_senf = (RecyclerView) dialogView.findViewById(R.id.recycler_alert_senf);
//                adapter_senf = new SenfAdapter(SignUpActivity.this ,  new SenfAdapter.OnClickInterface() {
//                    @Override
//                    public void onClick(int position, String cate) {
////                        Toast.makeText(SignUpActivity.this, String.valueOf(position)+"/"+cate, Toast.LENGTH_SHORT).show();
////                        adapter_senf.sub_cate(position);
//                    }
//                });
//                recycler_senf.setLayoutManager(new LinearLayoutManager(SignUpActivity.this));
//                recycler_senf.setAdapter(adapter_senf);
//                alertDialog.show();
//            }
//        });




        ostanha_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){

                    pish_shomare.setText(codhaye_ostan[position]);
                    cities.clear();
                    jsonObjectCities = null;
//                    cities = null;
                    try {
                        jsonObjectCities = new JSONObject(Constants.cities);
                        String result = jsonObjectCities.getString(ostanha[position]);
                        JSONArray ja = new JSONArray(result);

                        for (int i = 0 ; i < ja.length() ; i++){
                            JSONObject jo = ja.getJSONObject(i);
                            cities.add(jo.getString("name"));
                        }

                        if (cities.size() == 0){
                            Toast.makeText(SignUpActivity.this, "0", Toast.LENGTH_SHORT).show();
                        }else
                            Log.d("cities  is " , cities.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SignUpActivity.this, "333", Toast.LENGTH_SHORT).show();
            }
        });



        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();


        dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");



        SharedPreferences sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        mobile = sh.getString("mobile" , null);



        btn_pickPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(SignUpActivity.this);
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

        btn_sabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });


    }


    void Validate(){
        name_collection.setError(null);
        manager.setError(null);
        address.setError(null);

        String s_name_collection = name_collection.getText().toString();
        String s_manager = manager.getText().toString();
        String s_address = address.getText().toString();
//        senf = checkedRadioButton.getText().toString().trim();
        String state = ostanha_spinner.getSelectedItem().toString();
        String city = shahrha_spinner.getSelectedItem().toString();
        String cate_result = categories_spinner.getSelectedItem().toString();
        String pish_tell = pish_shomare.getText().toString();
        String phone_sabet = telephone_sabet.getText().toString();

        boolean cancel = false;
        View focusView = null;




//        if (TextUtils.isEmpty(senf)){
//
//            Toast.makeText(SignUpActivity.this, "صنف را انتخاب کنید!", Toast.LENGTH_SHORT).show();
//            cancel = true;
//        }


        if (TextUtils.isEmpty(s_address)) {
            address.setError(getString(R.string.emptyEditText));
            focusView = address;
            cancel = true;
        }else if(s_address.length() < 11 ){
            address.setError(getString(R.string.tooShortAddress));
            focusView = address;
            cancel = true;
        }


        if (TextUtils.isEmpty(s_manager)) {
            manager.setError(getString(R.string.emptyEditText));
            focusView = manager;
            cancel = true;
        }


        if (TextUtils.isEmpty(s_name_collection)) {
            name_collection.setError(getString(R.string.emptyEditText));
            focusView = name_collection;
            cancel = true;
        }








        if (cancel) {
            focusView.requestFocus();
        } else {
//            Toast.makeText(SignUpActivity.this, "OK", Toast.LENGTH_SHORT).show();
//            Register register = new Register();
//            register.name = nameTxt;
//            register.mobile = mobileTxt;
//            register.plainPassword = passwordTxt;
//            register.presenter = TextUtils.isEmpty(presenterTxt)?null:presenterTxt;
//            register.uniquePhone = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//
//            RegisterTask registerTask  = new RegisterTask(register);
//            registerTask.execute();

            if (state.equals("انتخاب استان:")){
                Toast.makeText(SignUpActivity.this, "استان خود را انتخاب کنید!", Toast.LENGTH_SHORT).show();
            }else if (city.equals("انتخاب شهر")){
                Toast.makeText(SignUpActivity.this, "شهر خود را انتخاب کنید!", Toast.LENGTH_SHORT).show();
            }
//            else if (senf.equals("انتخاب صنف:")){
//                Toast.makeText(SignUpActivity.this, "صنف خود را انتخاب کنید!", Toast.LENGTH_SHORT).show();
//            }
            else if (encoded_image == null){
                Toast.makeText(SignUpActivity.this, "شما هیچ عکسی انتخاب نکرده اید!", Toast.LENGTH_SHORT).show();
            } else if (cate_result.equals("انتخاب صنف:")) {
                Toast.makeText(SignUpActivity.this, "صنف خود را انتخاب کنید!", Toast.LENGTH_SHORT).show();
            }else {
                if (phone_sabet.equals("")){
                    phone_sabet = "no";
                }
                Toast.makeText(SignUpActivity.this, "OK", Toast.LENGTH_SHORT).show();
                new thread_send_mobile(mobile, s_name_collection, s_manager, state, city ,cate_result ,pish_tell , phone_sabet, s_address).execute();
            }

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

                ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.INT_PICK_GALLERY);
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
                Toast.makeText(SignUpActivity.this, "دسترسی داده نشد", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constants.INT_PICK_GALLERY) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),Constants.INT_PICK_GALLERY);


            } else {

                Toast.makeText(SignUpActivity.this, "دسترسی خواندن عکس از حافظه داده نشد!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

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
                img.setImageURI(final_image);
                encoded_image = getEncodedImage(final_image);
                Log.d("photo is  : " , " "+encoded_image);
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
            if (takePictureIntent.resolveActivity(SignUpActivity.this.getPackageManager()) != null) {
                photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (photoFile != null) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        imageUri = FileProvider.getUriForFile(SignUpActivity.this, "ehsanfatahizadehgmail.com.jinni.fileprovider", photoFile);
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








    public class thread_send_mobile extends AsyncTask {




        String mobile;
        String collection;
        String manager;
        String address;
        String state;
        String city;
        String senf;
        String pish_tell;
        String phone_sabet;

        public thread_send_mobile(String mobile , String collection , String manager , String state , String city , String senf , String pish_tell , String phone_sabet, String address ){
            this.mobile = mobile;
            this.collection = collection;
            this.manager = manager;
            this.address = address;
            this.state = state;
            this.city = city;
            this.senf = senf;
            this.pish_tell = pish_tell;
            this.phone_sabet = phone_sabet;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        String result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<String> call = apis.sendInfo(mobile , collection , manager ,state , city , senf , pish_tell , phone_sabet ,address , encoded_image);

            try {
                Response<String> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "errrror", Toast.LENGTH_SHORT).show();
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

            if (result.equals("1")) {
                finish();
                Toast.makeText(SignUpActivity.this, "اطلاعات با موفقیت ارسال شد.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(SignUpActivity.this, "خطا در ارسال اطلاعات \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }













}
