package com.imooc.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用户商品展示评价的vo
 */
@Data
public class ItemCommentVo {

    private Integer commentLevel;
    private String comment;
    private String specName;
    private Date createTime;
    private String userFace;
    private String nickname;


}
