package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountVo;
import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.service.ItemService;
import com.imooc.utils.PageGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.xml.stream.events.Comment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example =new Example(ItemsSpec.class);
        Example.Criteria criteria =example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example =new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommentLevelCountVo queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId,CommentLevel.BAD.type);
        Integer allCounts = goodCounts + normalCounts + badCounts ;

        CommentLevelCountVo commentLevelCountVo = new CommentLevelCountVo();
        commentLevelCountVo.setBadCounts(badCounts);
        commentLevelCountVo.setGoodCounts(goodCounts);
        commentLevelCountVo.setNormalCounts(normalCounts);
        commentLevelCountVo.setTotalCounts(allCounts);

        return commentLevelCountVo;
    }

    /**
     * 根据商品id 查询商品  分页
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("itemId",itemId);
        map.put("level",level);
        
        // Mybatis-pagehelper
        /**
         * page 第几页
         * pageSize 每页显示的条数
         */
        PageHelper.startPage(page,pageSize);
        List<ItemCommentVo> itemCommentVos = itemsMapperCustom.queryItemComments(map);
        
        

        return setterPageGrid(itemCommentVos,page);
    }

    private PageGridResult setterPageGrid(List<ItemCommentVo> itemCommentVos, Integer page) {
    PageInfo<?> pageList = new PageInfo<>(itemCommentVos);
    PageGridResult pageResult = new PageGridResult();
    pageResult.setPage(page);
    pageResult.setRows(itemCommentVos);
    pageResult.setTotal(pageList.getPageSize());
    pageResult.setRecords(pageList.getTotal());
    return pageResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
     Integer getCommentCounts(String itemId,Integer level){
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if(level != null){
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }
}
