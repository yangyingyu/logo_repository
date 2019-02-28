package cn.itsource.logo.service.impl;

import cn.itsource.logo.domain.Product;
import cn.itsource.logo.mapper.ProductMapper;
import cn.itsource.logo.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author wbtest
 * @since 2019-02-28
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
