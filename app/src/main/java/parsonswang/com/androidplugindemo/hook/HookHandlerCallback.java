package parsonswang.com.androidplugindemo.hook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import utils.HookHelper;
import utils.RefInvoke;

public class HookHandlerCallback implements Handler.Callback {

    private static final String TAG = HookHandlerCallback.class.getSimpleName();

    private Handler mRealHandler;

    public HookHandlerCallback(Handler handler) {
        this.mRealHandler = handler;
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 100:
                Log.d(TAG, "hook handler callback when start activity" + message.toString());
                //替换为将要启动的真实Activity（没有在manifest注册过的）
//                Object obj = message.obj;
//                Intent intent = (Intent) RefInvoke.getFiledObject("intent", obj);
//                Intent targetIntent = intent.getParcelableExtra(HookHelper.EXTRA_TARGET_INTENT);
//                intent.setComponent(targetIntent.getComponent());
                break;
        }

        mRealHandler.handleMessage(message);
        return true;
    }
}
