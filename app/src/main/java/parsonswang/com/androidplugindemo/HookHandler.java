package parsonswang.com.androidplugindemo;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HookHandler implements InvocationHandler {

    private static final String TAG = "HookHandler";

    private Object target;

    public HookHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Log.d(TAG, "---before invoke-----");
        Object result =  method.invoke(target, objects);
        Log.d(TAG, "---after invoke-----");
        return result;
    }
}
