package com.imooc.controller;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountVo;
import com.imooc.pojo.vo.ItemInfoVo;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PageGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.swing.StringUIClientPropertyKey;

import java.util.List;

@RestController
@Api(value = "商品接口",tags = {"商品信息展示的相关接口"})
@RequestMapping("items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/info/{itemId}")
    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    public IMOOCJSONResult info(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @PathVariable String itemId
    ){
        if(StringUtils.isEmpty(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }

        Items item  = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs =  itemService.queryItemImgList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);

        ItemInfoVo itemInfoVo = new ItemInfoVo();
        itemInfoVo.setItem(item);
        itemInfoVo.setItemImgList(itemsImgs);
        itemInfoVo.setItemParams(itemsParam);
        itemInfoVo.setItemSpecList(itemsSpecs);

        return IMOOCJSONResult.ok(itemInfoVo);
    }
    @GetMapping("/commentLevel")
   public IMOOCJSONResult commentLevel(
           @ApiParam(name = "itemId",value = "商品id",required = true)
           @RequestParam String itemId
   ){
    if(StringUtils.isEmpty(itemId)){
        return IMOOCJSONResult.errorMsg(null);
    }
        CommentLevelCountVo commentLevelCountVo = itemService.queryCommentCounts(itemId);
        return  IMOOCJSONResult.ok(commentLevelCountVo);
   }
   @ApiOperation(value = "查询商品评论",notes = "查询商品评论",httpMethod = "GET")
   @GetMapping("/comments")
    public IMOOCJSONResult comments(

           @ApiParam(name = "itemId", value = "商品id", required = true)
           @RequestParam String itemId,
           @ApiParam(name = "level", value = "评价等级", required = false)
           @RequestParam Integer level,
           @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
           @RequestParam Integer page,
           @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
           @RequestParam Integer pageSize) {
        if(StringUtils.isEmpty(itemId)){
            return IMOOCJSONResult.errorMsg(null);

        }

        if(page == null ){
            page = 1;
        }
        if (pageSize == null ){
            pageSize = 20;
        }

        PageGridResult grid = itemService.queryPagedComments(itemId,level,page,pageSize);
        return IMOOCJSONResult.ok(grid);
    }
}
