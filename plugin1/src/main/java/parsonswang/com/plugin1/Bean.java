package parsonswang.com.plugin1;

import parsonswang.com.mypluginlibrary.IBean;

public class Bean implements IBean {
    private String name = "parsons";

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
