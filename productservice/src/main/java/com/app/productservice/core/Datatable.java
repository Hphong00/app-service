package com.app.productservice.core;


import java.util.List;

public class Datatable extends BaseDto{

    private int totalRecords;
    private int totalPages;
    private List<?> data;

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Datatable() {
    }

    public Datatable(String errorCode, String errorMessage) {
        super.setErrorCode(errorCode);
        super.setErrorMessage(errorMessage);
    }

    public Datatable(int totalRecords, int totalPages, List<?> data) {
        this.totalRecords = totalRecords;
        this.totalPages = totalPages;
        this.data = data;
    }
}
