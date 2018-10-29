package parsonswang.com.androidplugindemo.hook;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import parsonswang.com.androidplugindemo.SecondActivity;
import utils.HookHelper;

public class HookHandler implements InvocationHandler {

    private static final String TAG = "HookHandler";

    private Object target;

    public HookHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "--- invoke-----" + method.getName());
        if ("startActivity".equals(method.getName())) {
            //这里为了绕过ams对Activity在manifest注册情况的一个检测
            Intent rawIntent = null;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }

            //先替换成有在manifest注册过的Activity
            rawIntent = (Intent) args[index];
            final String rawPackgName = rawIntent.getComponent().getPackageName();
            ComponentName componentName = new ComponentName(rawPackgName, SecondActivity.class.getName());

            Intent newIntent = new Intent();
            newIntent.setComponent(componentName);
            newIntent.putExtra(HookHelper.EXTRA_TARGET_INTENT, rawIntent);

            args[index] = newIntent;

            return method.invoke(target, args);

        }

        return method.invoke(target, args);
    }
}
