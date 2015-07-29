package com.zohaltech.app.mobiledatamonitor.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.AlarmHandler;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.WebService;

public class MainActivity extends EnhancedActivity {

    TextView txtTransferRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTransferRate = (TextView) findViewById(R.id.txtTransferRate);

        new TransferTask().execute();

        findViewById(R.id.btnStartMonitoring).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmHandler.start(App.context);
                Toast.makeText(App.currentActivity, "Monitoring has been started!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnStopMonitoring).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmHandler.cancel();
                Toast.makeText(App.currentActivity, "Monitoring has been stopped!", Toast.LENGTH_SHORT).show();
            }
        });

        //Intent service = new Intent(App.context, DataUsageUpdateService.class);
        //startService(service);
    }

    private class TransferTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            //while (true) {
            //    try {
            //        Thread.sleep(1000);
            //        //return "Transfer Rate : " + TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes() + " Bytes";
            //
            //        //NotificationHandler.displayNotification(App.context,"Transfer Rate",TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes() + " Bytes","");
            //
            //        Intent intent = new Intent(App.context, DataUsageUpdateService.class);
            //        App.context.startService(intent);
            //
            //    } catch (InterruptedException e) {
            //        e.printStackTrace();
            //    }
            //}

            // AlarmHandler.start(App.context);
           return WebService.verify();

            //return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtTransferRate.setText(s);
        }
    }
}
