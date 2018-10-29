package parsonswang.com.androidplugindemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 没有在manifest注册的Activity
 */
public class TargetActivity extends Activity {

    public static void open(Context context) {
        Intent intent = new Intent(context, TargetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
    }
}
