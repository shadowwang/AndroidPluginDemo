package parsonswang.com.androidplugindemo.hook;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
                break;
        }

        mRealHandler.handleMessage(message);
        return true;
    }
}
