package parsonswang.com.androidplugindemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import parsonswang.com.mypluginlibrary.IBean;
import utils.PluginUtils;

/**
 * 没有在manifest注册的Activity
 */
public class TargetActivity extends Activity {

    private static final String TAG = "TargetActivity";
    private Button btn1;

    private String name = "plugin1-debug.apk";

    private String dexPath, optimizedDirectory;

    public static void open(Context context) {
        Intent intent = new Intent(context, TargetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        PluginUtils.extraAssets(newBase, name);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassLoader dexClassLoader = new DexClassLoader(dexPath, optimizedDirectory, null, getClassLoader());

                Class clazz = null;
                try {
                    clazz = dexClassLoader.loadClass("parsonswang.com.plugin1.Bean");
                    Object beanObj = clazz.newInstance();
                    Method method = clazz.getDeclaredMethod("getName");
                    method.setAccessible(true);

                    IBean iBean = (IBean) beanObj;
                    iBean.setName("1234parsons");
                    String name = (String) method.invoke(beanObj);
                    Log.i(TAG, "getName: " + name);
                    btn1.setText(name);
                } catch (ClassNotFoundException   e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        //data/data/pkgname/files/plugin1-debug.apk
        File pluginFile = getFileStreamPath(name);
        dexPath = pluginFile.getPath();

        //data/data/pkgname/app_dex
        optimizedDirectory = getDir("dex", 0).getAbsolutePath();


    }
}
