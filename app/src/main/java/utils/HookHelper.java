package utils;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Build;
import android.os.Handler;

import java.lang.reflect.Proxy;

import parsonswang.com.androidplugindemo.hook.HookHandler;
import parsonswang.com.androidplugindemo.hook.HookHandlerCallback;
import parsonswang.com.androidplugindemo.hook.HookInstrumention;

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
            //动态代理，针对接口
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
        Instrumentation instrumentation = (Instrumentation) RefInvoke.getFiledObject(Activity.class, "mInstrumentation", activity);
        //静态代理，针对类
        HookInstrumention hookInstrumention = new HookInstrumention(instrumentation);
        RefInvoke.setFieldObject(Activity.class, "mInstrumentation", activity, hookInstrumention);
    }

    public static void hookHandlerCallback() {
        //获取当前的ActivityThread对象
        Object sCurrentActivityThread = RefInvoke.getStaticFiledObject("android.app.ActivityThread", "sCurrentActivityThread");
        Handler mH = (Handler) RefInvoke.getFiledObject("android.app.ActivityThread", "mH", sCurrentActivityThread);
        //静态代理，针对类
        RefInvoke.setFieldObject("mCallback", mH, new HookHandlerCallback(mH));
    }

    public static void hookActivityThreadInstrumention() {
        Object sCurrentActivityThread = RefInvoke.getStaticFiledObject("android.app.ActivityThread", "sCurrentActivityThread");
        Instrumentation instrumentation = (Instrumentation) RefInvoke.getFiledObject("android.app.ActivityThread", "mInstrumentation", sCurrentActivityThread);
        HookInstrumention hookInstrumention = new HookInstrumention(instrumentation);

        RefInvoke.setFieldObject("mInstrumentation", sCurrentActivityThread, hookInstrumention);
    }
}
