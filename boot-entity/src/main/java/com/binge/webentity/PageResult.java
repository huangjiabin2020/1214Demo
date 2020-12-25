package com.binge.webentity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResult {

    private Object records;
    private long total;


    public static PageResult instance(Object records, long total) {
        return new PageResult(records, total);
    }

    public static PageResult instance(Page page) {
        return new PageResult(page.getRecords(), page.getTotal());
    }


}
