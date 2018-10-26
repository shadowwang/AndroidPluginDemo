package utils;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Build;

import java.lang.reflect.Proxy;

import parsonswang.com.androidplugindemo.HookHandler;
import parsonswang.com.androidplugindemo.HookInstrumention;

public class HookHelper {

    public static void hookActivityManager() {
        //获取AMN的gDefault
        try {
            Object singletonFiledInActivityManager;
            if (Build.VERSION.SDK_INT > 25 || (Build.VERSION.SDK_INT==25 && Build.VERSION.PREVIEW_SDK_INT > 0)) {
                singletonFiledInActivityManager = RefInvoke.getStaticFiledObject("android.app.ActivityManager", "IActivityManagerSingleton");

            } else {
                singletonFiledInActivityManager = RefInvoke.getStaticFiledObject("android.app.ActivityManagerNative", "gDefault");
            }

            if (singletonFiledInActivityManager == null) {
                throw new ClassNotFoundException("can't found class " + singletonFiledInActivityManager.getClass());
            }

            //Singleton的mInstance字段类型即为上面的ams
            Object rawActivityManager = RefInvoke.getFiledObject("android.util.Singleton", "mInstance", singletonFiledInActivityManager);
            Class<?> IActivityManagerClazz = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[] {IActivityManagerClazz},
                    new HookHandler(rawActivityManager));

            //替换Singleton中的mInstance字段，值为proxy
            RefInvoke.setFieldObject("android.util.Singleton", "mInstance", singletonFiledInActivityManager, proxy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void hookInstrumention(Activity activity) {
        Instrumentation instrumentation = (Instrumentation) RefInvoke.getFiledObject("mInstrumentation", activity);
        HookInstrumention hookInstrumention = new HookInstrumention(instrumentation);

        RefInvoke.setFieldObject("mInstrumentation", activity, hookInstrumention);

    }
}
