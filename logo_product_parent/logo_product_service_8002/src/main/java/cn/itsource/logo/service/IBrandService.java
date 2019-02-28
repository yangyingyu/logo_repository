package cn.itsource.logo.service;

import cn.itsource.logo.domain.Brand;
import cn.itsource.logo.query.BrandQuery;
import cn.itsource.logo.util.PageList;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author wbtest
 * @since 2019-02-28
 */
public interface IBrandService extends IService<Brand> {
    /**
     * Brand的分页条件查询
     * @param query
     * @return
     */
    PageList<Brand> queryPage(BrandQuery query);
}
