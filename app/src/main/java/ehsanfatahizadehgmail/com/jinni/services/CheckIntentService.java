package ehsanfatahizadehgmail.com.jinni.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.R;
import ehsanfatahizadehgmail.com.jinni.SplashActivity;
import ehsanfatahizadehgmail.com.jinni.db.CheckGetDbHelper;
import ehsanfatahizadehgmail.com.jinni.db.CheckGiveDbHelper;
import ehsanfatahizadehgmail.com.jinni.models.CheckGet;
import ehsanfatahizadehgmail.com.jinni.models.CheckGive;

public class CheckIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;

    public CheckIntentService() {
        super("CheckIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        Calendar calander = Calendar.getInstance();
        int cDay = calander.get(Calendar.DAY_OF_MONTH);
        int cMonth = calander.get(Calendar.MONTH) + 1;
        int cYear = calander.get(Calendar.YEAR);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String getCurrentDateTime = sdf.format(calander.getTime());
        Date today = new Date(getCurrentDateTime);




        CheckGetDbHelper db_get = new CheckGetDbHelper(getApplicationContext());
        CheckGiveDbHelper db_give = new CheckGiveDbHelper(getApplicationContext());
        List<CheckGet> list_get_db = db_get.getAllCheckGet();
        List<CheckGive> list_give_db = db_give.getAllCheckGet();

        int num_of_checks=0;
        String date="";


        if (list_get_db.size() > 0) {


            for (int i = 0; i < list_get_db.size(); i++) {

                int check_year = Integer.parseInt(list_get_db.get(i).getYear());
                int check_month = Integer.parseInt(list_get_db.get(i).getMonth());
                int check_day = Integer.parseInt(list_get_db.get(i).getDay());


                Calendar calander2 = Calendar.getInstance();
                calander2.set(check_year, check_month - 1, check_day, 0, 0);
                String getCheckDateTime = sdf.format(calander2.getTime());
                Date check_date = new Date(getCheckDateTime);


                int days_left =(int)(check_date.getTime()-today.getTime())/(3600*24*1000);
                if (days_left == 1){
                    num_of_checks++;

                    date=list_get_db.get(i).getYear_shamsi()+"/"+list_get_db.get(i).getMonth_shamsi()+"/"+list_get_db.get(i).getDay_shamsi();
                }

            }

        }

        if (list_give_db.size() > 0) {
            for (int i = 0; i < list_give_db.size(); i++) {

                int check_year = Integer.parseInt(list_give_db.get(i).getYear());
                int check_month = Integer.parseInt(list_give_db.get(i).getMonth());
                int check_day = Integer.parseInt(list_give_db.get(i).getDay());


                Calendar calander2 = Calendar.getInstance();
                calander2.set(check_year, check_month - 1, check_day, 0, 0);
                String getCheckDateTime = sdf.format(calander2.getTime());
                Date check_date = new Date(getCheckDateTime);


                int days_left =(int)(check_date.getTime()-today.getTime())/(3600*24*1000);
                if (days_left == 1){
                    num_of_checks++;

                    date=list_give_db.get(i).getYear_shamsi()+"/"+list_give_db.get(i).getMonth_shamsi()+"/"+list_give_db.get(i).getDay_shamsi();
                }


            }
        }

        if (num_of_checks !=0) {

            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle("جینی");
            builder.setContentText("تعداد "+String.valueOf(num_of_checks)+" چک در تاریخ "+date+" وجود دارد");
            builder.setSmallIcon(R.drawable.close_ic);
            Intent notifyIntent = new Intent(this, SplashActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //to be able to launch your activity from the notification
            builder.setContentIntent(pendingIntent);
            Notification notificationCompat = builder.build();
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(NOTIFICATION_ID, notificationCompat);

        }

    }


}