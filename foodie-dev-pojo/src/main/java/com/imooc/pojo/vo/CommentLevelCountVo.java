package com.imooc.pojo.vo;

import lombok.Data;

@Data
public class CommentLevelCountVo {

    public Integer totalCounts;
    public Integer goodCounts;
    public Integer normalCounts;
    public Integer badCounts;


}
