package com.binge.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author binge
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Good implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格，单位：分
     */
    private Long price;

    /**
     * 商品库存
     */
    private Long stock;

    /**
     * 商品计量单位，比如kg，算冗余字段
     */
    private String unit;

    /**
     * 商品状态：0:下架;1:上架;2:售罄
     */
    private Integer status;

    /**
     * 商品折扣，单位0.01，比如90代表0.9，九折
     */
    private Integer discount;

    /**
     * 商品创建时间
     */
    private LocalDateTime createTime;

    /**
     * 商品更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 商品修改人id
     */
    private Long updateUid;


}
