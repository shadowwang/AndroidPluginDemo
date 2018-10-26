package parsonswang.com.androidplugindemo;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import utils.RefInvoke;

public class HookInstrumention extends Instrumentation {

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

        return (ActivityResult) RefInvoke.invokeInstanceMethod(instrumentation, "execStartActivity", params, values);
    }
}
