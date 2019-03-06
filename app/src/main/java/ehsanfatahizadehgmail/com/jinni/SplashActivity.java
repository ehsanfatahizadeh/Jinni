package ehsanfatahizadehgmail.com.jinni;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Method;

import ehsanfatahizadehgmail.com.jinni.libs.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        new thread_splash().execute();


    }





    public class thread_splash extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            finish();
            startActivity(new Intent(SplashActivity.this , TellActivity.class));
        }
    }



}
