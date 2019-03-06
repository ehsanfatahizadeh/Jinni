package ehsanfatahizadehgmail.com.jinni;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.libs.BaseActivity;
import ehsanfatahizadehgmail.com.jinni.libs.FileUtil;
import ehsanfatahizadehgmail.com.jinni.libs.ImageOrientation;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class SignUpActivity extends BaseActivity {

    Intent CamIntent,GalIntent,CropIntent;
    ImageView img;


    String mobile;

    Random random;
    String ALLOWED_CHARACTERS;
    StringBuilder random_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group_sign_up);
        RadioButton checkedRadioButton = (RadioButton)rg.findViewById(rg.getCheckedRadioButtonId());
        Button btn_pickPic = (Button) findViewById(R.id.upload_pic_sign_up);
        img = (ImageView) findViewById(R.id.imageview_Signup);
//        senf = checkedRadioButton.getText().toString().trim();

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


    }



//    public void PickGallery(){
//
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED){
//
//                Crop.pickImage(SignUpActivity.this);
//
//            } else {
//
//                ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//            }
//        }
//        else {
//
//            Crop.pickImage(SignUpActivity.this);
//        }
//
//    }
//
//

//
//
//
//
//
//
//
//
//
//
//    private static final int TAKE_PICTURE = 1;
//    private Uri imageUri;
//
//    public void takePhoto() {
//
//
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(Manifest.permission.CAMERA)
//                    == PackageManager.PERMISSION_GRANTED){
//
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(photo));
//                imageUri = Uri.fromFile(photo);
//                startActivityForResult(intent, TAKE_PICTURE);
//
//            } else {
//
//                ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.CAMERA}, 2);
//            }
//        }
//        else {
//
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
//            intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                    Uri.fromFile(photo));
//            imageUri = Uri.fromFile(photo);
//            startActivityForResult(intent, TAKE_PICTURE);
//        }
//
//
//
//
//    }
//
//
//
//    private void CropImage(Uri uri) {
//
//        try{
//            CropIntent = new Intent("com.android.camera.action.CROP");
//            CropIntent.setDataAndType(uri,"image/*");
//
//            CropIntent.putExtra("crop","true");
//            CropIntent.putExtra("outputX",180);
//            CropIntent.putExtra("outputY",180);
//            CropIntent.putExtra("aspectX",4);
//            CropIntent.putExtra("aspectY",4);
//            CropIntent.putExtra("scaleUpIfNeeded",true);
//            CropIntent.putExtra("return-data",true);
//
//            startActivityForResult(CropIntent,3);
//        }
//        catch (ActivityNotFoundException ex)
//        {
//
//        }
//
//    }
//
//
//
//



//=============================



    Uri imageUri;
    File file;
    private static final int TAKE_PICTURE = 1;

    private void CameraOpen() {





        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {



//                EasyImage.openCamera(SignUpActivity.this, 0);


//
////                CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//////                File file = new File(Environment.getExternalStorageDirectory(), "image.jpg");
//////                imageUri = Uri.fromFile(file);
////////        uri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".com.ehsanfatahizadehgmail.malir.provider", file);
//////                CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
////                startActivityForResult(CamIntent,0);
//
//
////                String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/p1.jpg";
////                File imageFile = new File(imageFilePath);
////                imageUri = Uri.fromFile(imageFile);
//
//////========================================
////                CamIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//////                File file = new File(Environment.getExternalStorageDirectory(),"p1.jpg");
//////                imageUri = Uri.fromFile(file);
////////        uri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".com.ehsanfatahizadehgmail.malir.provider", file);
//////                CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
////                startActivityForResult(CamIntent,0);
////                //=====================================
                dispatchTakePictureIntent(1);
//
//
//
//
//
////                Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                file = new File(Environment.getExternalStorageDirectory(), "p1.jpg");
////                imageUri = FileProvider.getUriForFile(SignUpActivity.this, getApplicationContext().getApplicationContext().getPackageName() + ".provider", file);
////                m_intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
////                startActivityForResult(m_intent, 0);
//
//
//
//
////                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
////                intent.putExtra(MediaStore.EXTRA_OUTPUT,
////                        Uri.fromFile(photo));
////                imageUri = Uri.fromFile(photo);
////                startActivityForResult(intent, TAKE_PICTURE);
//
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.INT_CAMERA);
            }
        }
        else {

//            EasyImage.openCamera(SignUpActivity.this, 0);


//
//
            dispatchTakePictureIntent(1);
////            CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//////            File file = new File(Environment.getExternalStorageDirectory(),"p1.jpg");
//////            imageUri = Uri.fromFile(file);
////////        uri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".com.ehsanfatahizadehgmail.malir.provider", file);
//////            CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
////            startActivityForResult(CamIntent,0);
//
//
//
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





    private void CropImage() {

//        try{
//
//            CropIntent = new Intent("com.android.camera.action.CROP");
//            CropIntent.setDataAndType(selectedImage,"image/*");
//            CropIntent.putExtra("crop","true");
//            CropIntent.putExtra("outputX",180);
//            CropIntent.putExtra("outputY",180);
//            CropIntent.putExtra("aspectX",4);
//            CropIntent.putExtra("aspectY",4);
//            CropIntent.putExtra("scaleUpIfNeeded",true);
//            CropIntent.putExtra("return-data",true);
//            startActivityForResult(CropIntent,4);
//
//        }
//        catch (ActivityNotFoundException ex)
//        {
//
//        }


        //====================================================


        try{
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(selectedImage,"image/*");

            CropIntent.putExtra("crop","true");
            CropIntent.putExtra("outputX",180);
            CropIntent.putExtra("outputY",180);
            CropIntent.putExtra("aspectX",4);
            CropIntent.putExtra("aspectY",4);
            CropIntent.putExtra("scaleUpIfNeeded",true);
            CropIntent.putExtra("return-data",true);

            startActivityForResult(CropIntent,Constants.INT_CROP);
        }
        catch (ActivityNotFoundException ex)
        {

        }

//        Crop.of(selectedImage, selectedImage).asSquare().start(SignUpActivity.this);



        //====================================================


//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setType("image/*");
//        List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );
//        int size = list.size();
//        if (size == 0) {
//            Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
//
//            return;
//        } else {
//            intent.setData(selectedImage);
//            intent.putExtra("outputX", 300);
//            intent.putExtra("outputY", 300);
//            intent.putExtra("aspectX", 1);
//            intent.putExtra("aspectY", 1);
//            intent.putExtra("scaleUpIfNeeded",true);
//            intent.putExtra("return-data", true);
//            if (size == 1) {
//                Intent i        = new Intent(intent);
//                ResolveInfo res = list.get(0);
//
//                i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//
//                startActivityForResult(i, 4);
//            } else {
//
//            }
//
//        }


    }






    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);




        if (requestCode == Constants.INT_CAMERA) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


//                EasyImage.openCamera(SignUpActivity.this, 0);

//                Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
////                imageUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
////                m_intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(m_intent, 0);

//                //=======================================================
//                CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                File file = new File(Environment.getExternalStorageDirectory(),"p1.jpg");
////                imageUri = Uri.fromFile(file);
//////        uri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".com.ehsanfatahizadehgmail.malir.provider", file);
////                CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//                startActivityForResult(CamIntent,0);
//                //==========================================================
                dispatchTakePictureIntent(1);


//                Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                file = new File(Environment.getExternalStorageDirectory(), "p1.jpg");
//                imageUri = FileProvider.getUriForFile(SignUpActivity.this, getApplicationContext().getApplicationContext().getPackageName() + ".provider", file);
//                m_intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(m_intent, 0);




//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,0);

//                CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File file = new File(Environment.getExternalStorageDirectory(),"image.jpg");
//                imageUri = Uri.fromFile(file);
////        uri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".com.ehsanfatahizadehgmail.malir.provider", file);
//                CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//                startActivityForResult(CamIntent,0);

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




    Uri final_image;
    Uri selectedImage;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Constants.INT_CAMERA && resultCode == RESULT_OK){



//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            selectedImage = getImageUri(thumbnail);
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
//                        CropIntent.putExtra("return-data",true);
//                        dispatchTakePictureIntent(2);
//                        CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage);


                        String rndm = getSaltString();

                        File f = new File(Environment.getExternalStorageDirectory()+"/"+rndm+"/");

//                        if (f.exists()) {
//                            f.delete();
//                        }else
//                            f.mkdir();

//                        if (photoFile != null) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                final_image = FileProvider.getUriForFile(SignUpActivity.this, "ehsanfatahizadehgmail.com.jinni.fileprovider", f);
//                            } else {
                                final_image = Uri.fromFile(f);
//                            }
//                        }else
//                            Toast.makeText(SignUpActivity.this, "null", Toast.LENGTH_SHORT).show();

                        CropIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, final_image);

                        startActivityForResult(CropIntent, Constants.INT_CROP);

                    } //respond to users whose devices do not support the crop action
                    catch (ActivityNotFoundException anfe) {
                        //display an error message
                        String errorMessage = "Your device doesn't support the crop action!";
                        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                        toast.show();
                    }


        }
        else if (requestCode == Constants.INT_CROP && resultCode == RESULT_OK){
//            img.setImageURI(selectedImage);
//            Bundle bundle = data.getExtras();
//            Bitmap bitmap2 = bundle.getParcelable("data");
////            bitmap2.setDensity(100);
//
//
////            Uri filePath = data.getExtras().getParcelable("data");
//            try {
//                //..First convert the Image to the allowable size so app do not throw Memory_Out_Bound Exception
////                BitmapFactory.Options options = new BitmapFactory.Options();
////                options.inJustDecodeBounds = true;
//////                BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath), null, options);
////                int resolution = 500;
////                options.inSampleSize = calculateInSampleSize(options, resolution, resolution);
////                options.inJustDecodeBounds = false;
////                Bitmap bitmap1 = null;
////                bitmap1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath), null, options);
//                Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver() , getImageUri(bitmap2));
//                bitmap = ImageOrientation.modifyOrientation(getApplicationContext(), bitmap1, getImageUri(bitmap2));
                img.setImageURI(final_image);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        else if (requestCode == Constants.INT_PICK_GALLERY && resultCode == RESULT_OK){

//            rotatePic(data.getData() , Constants.INT_PICK_GALLERY);
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
//                    CropIntent.putExtra("return-data",false);


                    String rndm = getSaltString();

                    File f = new File(Environment.getExternalStorageDirectory()+"/"+rndm+"/");

//                        if (f.exists()) {
//                            f.delete();
//                        }else
//                            f.mkdir();

//                        if (photoFile != null) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                final_image = FileProvider.getUriForFile(SignUpActivity.this, "ehsanfatahizadehgmail.com.jinni.fileprovider", f);
//                            } else {
                    final_image = Uri.fromFile(f);
//                            }
//                        }else
//                            Toast.makeText(SignUpActivity.this, "null", Toast.LENGTH_SHORT).show();

                    CropIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, final_image);

                    startActivityForResult(CropIntent, Constants.INT_CROP);



//                    dispatchTakePictureIntent(2);
//                    CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage);
//                    startActivityForResult(CropIntent, Constants.INT_CROP);
                }catch (ActivityNotFoundException ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                Log.d("Image_exception",e.toString());
            }
        }


//        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
//            @Override
//            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
//                //Some error handling
//            }
//
//            @Override
//            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
//                String filePath = imageFile.getPath();
//                img.setImageBitmap(BitmapFactory.decodeFile(filePath));
////                img.setImageBitmap(imageFile.);
//            }
//
//        });



//        if (requestCode == Constants.INT_CAMERA && resultCode == RESULT_OK){
////            Bundle bundle = data.getExtras();
////            Bitmap bitmap = bundle.getParcelable("data");
////            img.setImageBitmap(bitmap);
//            //File object of camera image
////            File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
////            //Uri of camera image
////            Uri uri = FileProvider.getUriForFile(SignUpActivity.this, getApplicationContext().getPackageName() + ".provider", file);
//
//
//////            selectedImage = imageUri;
////            Bundle bundle = data.getExtras();
////            Bitmap bitmap = bundle.getParcelable("data");
////            selectedImage = getImageUri(bitmap);
////            Toast.makeText(SignUpActivity.this, "yess", Toast.LENGTH_SHORT).show();
//
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                selectedImage = Uri.parse(new File(selectedImage.getPath()).toString());
////            } else {
////                selectedImage = data.getData();
////            }
//
////            img.setImageURI(selectedImage);
////            Log.d("path is   " , selectedImage.toString());
////
////            BitmapFactory.Options bounds = new BitmapFactory.Options();
////            bounds.inJustDecodeBounds = true;
////            BitmapFactory.decodeFile(photoFile, bounds);
////
////            BitmapFactory.Options opts = new BitmapFactory.Options();
////            Bitmap bm = BitmapFactory.decodeFile(photoFile, opts);
////            ExifInterface exif = new ExifInterface(photoFile);
////            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
////            int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;
////
////            int rotationAngle = 0;
////            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
////            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
////            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
////
////            Matrix matrix = new Matrix();
////            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
////            Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
////
//            rotatePic(imageUri , Constants.INT_CAMERA);
////            img.setImageURI(imageUri);
//
//
//
//
//        }

// else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK){
////            Bundle bundle = data.getExtras();
////            Bitmap bitmap = bundle.getParcelable("data");
////            if (bitmap != null) {
////            rotatePic((Uri)data.getParcelableExtra("output"));
//
//
//
////            rotatePic((Uri)data.getParcelableExtra("output") , Constants.INT_CROP);
//            img.setImageURI((Uri)data.getParcelableExtra("output"));
//
//
//
////            }else {
////                Toast.makeText(SignUpActivity.this, "empty", Toast.LENGTH_SHORT).show();
////            }
////            Log.d("result  " , data.getExtras().getParcelable("data").toString());
//        }
//
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

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }





    static final int REQUEST_TAKE_PHOTO = 0;
    File photoFile;
    private void dispatchTakePictureIntent(int i) {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                    imageUri = FileProvider.getUriForFile(this,
//                            BuildConfig.APPLICATION_ID +".fileprovider",
//                            photoFile);
//                }else{
//                    imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),currentPhotoPath));
//                }
//
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }

//        if (i == 1) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(SignUpActivity.this.getPackageManager()) != null) {
                // Create the File where the photo should go
                photoFile = null;
                try {
                    //make a file
                    photoFile = createImageFile();
//                setSavePath(photoFile.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
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
//        } else if (i == 2) {
//
//            photoFile = null;
//            try {
//                //make a file
//                photoFile = createImageFile();
////                setSavePath(photoFile.getAbsolutePath());
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    imageUri = FileProvider.getUriForFile(SignUpActivity.this, "ehsanfatahizadehgmail.com.jinni.fileprovider", photoFile);
//                    selectedImage = imageUri;
//                } else {
//                    imageUri = Uri.fromFile(photoFile);
//                    selectedImage = imageUri;
//                }
//
//
//            }
//
//
//        }
    }








    Bitmap bitmap;
    void rotatePic(Uri photoURI , int action){

        Uri selectedImage = photoURI;
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



//        switch(orientation) {
//
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                bitmap = rotateImage(bitmap, 90);
//                break;
//
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                bitmap = rotateImage(bitmap, 180);
//                break;
//
//            case ExifInterface.ORIENTATION_ROTATE_270:
//                bitmap = rotateImage(bitmap, 270);
//                break;
//
//            case ExifInterface.ORIENTATION_NORMAL:
//            default:
//                bitmap = bitmap;
//        }


        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                bitmap = rotateImage(bitmap, 90);
//                if (action == Constants.INT_CAMERA){
//                    this.selectedImage = photoURI;

//                }else if (action == Constants.INT_PICK_GALLERY){
                    this.selectedImage = photoURI;
//                CropImage();
//                    CropImage();
//                }else if (action == Constants.INT_CROP){
//                    rotatePic(selectedImage , Constants.INT_AFTER_CROP);
//                }else if (action == Constants.INT_AFTER_CROP){
                    img.setImageBitmap(bitmap);
//                }

                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                bitmap = rotateImage(bitmap, 180);
//                if (action == Constants.INT_CAMERA){
                    this.selectedImage = photoURI;
//                    CropImage();
//                }else if (action == Constants.INT_PICK_GALLERY){
//                    this.selectedImage = photoURI;
//                    CropImage();
//                }else if (action == Constants.INT_CROP){
//                    rotatePic(selectedImage , Constants.INT_AFTER_CROP);
//                }else if (action == Constants.INT_AFTER_CROP){
                    img.setImageBitmap(bitmap);
//                }
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                bitmap = rotateImage(bitmap, 270);
//                if (action == Constants.INT_CAMERA){
                    this.selectedImage = photoURI;
//                    CropImage();
//                }else if (action == Constants.INT_PICK_GALLERY){
//                    this.selectedImage = photoURI;
//                    CropImage();
//                }else if (action == Constants.INT_CROP){
//                    rotatePic(selectedImage , Constants.INT_AFTER_CROP);
//                }else if (action == Constants.INT_AFTER_CROP){
                    img.setImageBitmap(bitmap);
//                }
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                bitmap = bitmap;
//                if (action == Constants.INT_CAMERA){
                    this.selectedImage = photoURI;
//                    CropImage();
//                }else if (action == Constants.INT_PICK_GALLERY){
//                    this.selectedImage = photoURI;
//                    CropImage();
//                }else if (action == Constants.INT_CROP){
//                    rotatePic(selectedImage , Constants.INT_AFTER_CROP);
//                }else if (action == Constants.INT_AFTER_CROP){
                    img.setImageBitmap(bitmap);
//                }
        }



    }

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












//    Uri new_uri;
//    /** Create a file Uri for saving an image or video */
//    private Uri getOutputMediaFileUri(){
//
//
//        File file2 = getOutputMediaFile();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            new_uri = FileProvider.getUriForFile(SignUpActivity.this, "ehsanfatahizadehgmail.com.jinni.fileprovider", file2);
//            selectedImage = imageUri;
//        } else {
//            new_uri = Uri.fromFile(file2);
//        }
//
//
//
//        return new_uri;
//    }
//
//    /** Create a File for saving an image or video */
//    private File getOutputMediaFile(){
//        // To be safe, you should check that the SDCard is mounted
//        // using Environment.getExternalStorageState() before doing this.
//
//        File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        // This location works best if you want the created images to be shared
//        // between applications and persist after your app has been uninstalled.
//
//        // Create the storage directory if it does not exist
//        if (! mediaStorageDir.exists()){
//
//            if (! mediaStorageDir.mkdirs()){
//                Log.d("MyCameraApp", "failed to create directory");
//                return null;
//            }
//        }
//
//        // Create a media file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//                "IMG_"+ timeStamp + ".jpg");
//
//
//
//
//
//
//        return mediaFile;
//    }





    protected String getSaltString() {
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


}
