package demo.com.apkupdate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import demo.com.apkupdate.ExternalStorageUtils;
import demo.com.apkupdate.R;
import demo.com.apkupdate.update.UpdateOwner;
import demo.com.apkupdate.update.UpdateService;
import demo.com.apkupdate.view.CommonDialog;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button updateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateBtn = (Button) this.findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateOwner.getInstance().checkVersion(MainActivity.this,"http://121.42.53.175:8080/hello_project/resources/upload/TianQiBao201605231.apk");

            }
        });
    }
}
