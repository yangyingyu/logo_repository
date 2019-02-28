package cn.itsource.logo.mapper;

import cn.itsource.logo.domain.Brand;
import cn.itsource.logo.query.BrandQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author wbtest
 * @since 2019-02-28
 */
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 分页条件数据
     * @param query
     * @return
     */
    List<Brand> queryPage( BrandQuery query);


    /**
     * 分页条件查询:总的条数
     * @param query
     * @return
     */
    long queryPageCount( BrandQuery query);
}
