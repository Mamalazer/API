package io.dummyapi.pojo_classes;

import java.util.List;

public class UserPreview {

    private List<User> data;
    private Integer total;
    private Integer page;
    private Integer limit;

    public UserPreview(List<User> data, Integer total, Integer page, Integer limit) {
        this.data = data;
        this.total = total;
        this.page = page;
        this.limit = limit;
    }

    public UserPreview() {
    }

    public List<User> getData() {
        return data;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getLimit() {
        return limit;
    }

}
