package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountVo;
import com.imooc.utils.PageGridResult;

import java.util.List;

public interface ItemService {



    public Items queryItemById(String itemId);

    public List<ItemsImg> queryItemImgList(String itemId);

    public List<ItemsSpec> queryItemSpecList(String itemId);

    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id 查询商品的评价等级数量
     * @param itemId
     * @return
     */
    public CommentLevelCountVo queryCommentCounts(String itemId);

    /**
     * 根据商品id 查询商品评论   分页
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return
     */
    public PageGridResult queryPagedComments(String itemId, Integer level,
                                             Integer page, Integer pageSize);
}
