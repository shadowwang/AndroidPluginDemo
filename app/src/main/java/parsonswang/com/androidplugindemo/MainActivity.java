package parsonswang.com.androidplugindemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import utils.HookHelper;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        //方案一 hook ActivityManager
        HookHelper.hookActivityManager();

        //方案二 hook Instrumentation
        HookHelper.hookInstrumention(this);

        //方案三 hook ActivityThread的Handler的callback
        HookHelper.hookHandlerCallback();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondActivity.open(MainActivity.this);
            }
        });
    }
}
