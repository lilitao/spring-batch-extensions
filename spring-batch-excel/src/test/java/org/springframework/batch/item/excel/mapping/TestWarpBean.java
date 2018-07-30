package org.springframework.batch.item.excel.mapping;

import java.util.List;

public class TestWarpBean {
    private String id;
    List<SubTestBean> subBean;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SubTestBean> getSubBean() {
        return subBean;
    }

    public void setSubBean(List<SubTestBean> subBean) {
        this.subBean = subBean;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "id='" + id + '\'' +
                ", subBean=" + subBean +
                '}';
    }
}