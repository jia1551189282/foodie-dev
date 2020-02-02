package com.imooc.mapper;

import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 自定义 商品分裂mapper
 */
public interface CategoryMapperCustom {
    public List<CategoryVo> getSubCateList(Integer rootId);

    public List<NewItemsVo> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}
