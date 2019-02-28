package cn.itsource.logo.service;

import cn.itsource.logo.domain.ProductType;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author wbtest
 * @since 2019-02-28
 */
public interface IProductTypeService extends IService<ProductType> {
    /**
     * tree
     * 数据
     * @return
     */
    List<ProductType> treeData();
}
