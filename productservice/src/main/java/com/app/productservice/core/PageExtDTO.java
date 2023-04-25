package com.app.productservice.core;


import lombok.Data;

import java.util.List;

@Data
public class PageExtDTO<T> extends BaseObj {
    private Integer totalElements;
    private Integer totalPages;
    private List<T> content;

    public PageExtDTO() {
    }

    /**
     * @param totalElements
     * @param totalPages
     * @param content
     */
    public PageExtDTO(Integer totalElements, Integer totalPages, List<T> content) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.content = content;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
