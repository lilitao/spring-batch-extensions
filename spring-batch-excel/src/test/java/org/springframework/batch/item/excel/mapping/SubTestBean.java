package org.springframework.batch.item.excel.mapping;

public class SubTestBean {
    private String sid;
    private String name;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SubTestBean{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}