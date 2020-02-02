package com.imooc.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 最新商品 vo
 */
@Data
public class NewItemsVo {

    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVo> simpleItemList;

}
