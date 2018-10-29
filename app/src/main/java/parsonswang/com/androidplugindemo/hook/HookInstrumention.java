package parsonswang.com.androidplugindemo.hook;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import utils.HookHelper;
import utils.RefInvoke;

public class HookInstrumention extends Instrumentation {

    private static final String TAG = HookInstrumention.class.getSimpleName();

    private Instrumentation instrumentation;

    public HookInstrumention(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }


    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        Class[] params = new Class[]{
                Context.class,  IBinder.class, IBinder.class,
                Activity.class, Intent.class, int.class, Bundle.class
        };

        Object[] values = new Object[]{
                who, contextThread, token, target,
                intent, requestCode, options
        };

        Log.i(TAG, "hook Instrumentation 跳转Activity");

        return (ActivityResult) RefInvoke.invokeInstanceMethod(instrumentation, "execStartActivity", params, values);
    }

    public Activity newActivity(ClassLoader cl, String className,
                                Intent intent)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        Intent rawIntent = intent.getParcelableExtra(HookHelper.EXTRA_TARGET_INTENT);
        //没有hook，走正常流程
        if (rawIntent == null) {
            Log.i(TAG, "hook Instrumentation newActivity 正常逻辑");
            return instrumentation.newActivity(cl, className, intent);
        }

        Log.i(TAG, "hook Instrumentation newActivity hook逻辑");
        return instrumentation.newActivity(cl, rawIntent.getComponent().getClassName(), rawIntent);
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        Class[] clazzs = new Class[] {
                Activity.class,
                Bundle.class
        };

        Object[] objects = new Object[] {
                activity,
                icicle
        };
        Log.i(TAG, "hook Instrumentation callActivityOnCreate");
        RefInvoke.invokeInstanceMethod(instrumentation, "callActivityOnCreate", clazzs, objects);
    }
}
