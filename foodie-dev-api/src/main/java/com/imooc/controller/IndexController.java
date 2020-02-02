package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsVo;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("index")
@Api(value = "首页",tags = "首页展示相关接口")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel(){
        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);
        return IMOOCJSONResult.ok(carousels);
    }
    @ApiOperation(value = "获取商品分类（一级分类）",notes="获取商品分类 一级分类",httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats(){
        List<Category> categoryList = categoryService.queryAllRootLevelCat();
        return IMOOCJSONResult.ok(categoryList);
    }
    @GetMapping("/subCat/{rootCatId}")
    @ApiOperation(value = "获取商品分类",notes = "获取商品分类",httpMethod = "GET")
    public IMOOCJSONResult subCate(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId){
        if(rootCatId == null){
            return IMOOCJSONResult.errorMsg("分类不存在");
        }
        List<CategoryVo> subCatList = categoryService.getSubCatList(rootCatId);
        return IMOOCJSONResult.ok(subCatList);

    }
    @ApiOperation(value = "查询每个一级分类下的最新六条商品数据",notes = "查询每个一级分类下的最新六条数据",httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name = "rootCatId",value = "一级分类ID",required = true)
            @PathVariable Integer rootCatId
    ){
         if (rootCatId == null){
             return IMOOCJSONResult.errorMsg("分类不存在");
         }
        List<NewItemsVo> newItemsVoList = categoryService.getSixNewItemsLazy(rootCatId);
         return  IMOOCJSONResult.ok(newItemsVoList);
    }


}
