package com.karthz.giphy.model.data;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("count")
    private int count;

    @SerializedName("offset")
    private int offset;

    @SerializedName("total_count")
    private int total;

    public int getCount() {
        return count;
    }

    public int getOffset() {
        return offset;
    }

    public int getTotal() {
        return total;
    }
}
