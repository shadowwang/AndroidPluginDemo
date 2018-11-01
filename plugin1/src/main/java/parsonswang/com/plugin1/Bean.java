package parsonswang.com.plugin1;

import parsonswang.com.mypluginlibrary.IBean;
import parsonswang.com.mypluginlibrary.ICallBack;

public class Bean implements IBean {
    private String name = "parsons";

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void registResult(ICallBack iCallBack) {
        iCallBack.setResult(name);
    }

    @Override
    public String getName() {
        return name;
    }
}
