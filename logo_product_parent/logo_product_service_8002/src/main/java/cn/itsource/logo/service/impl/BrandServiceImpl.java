package cn.itsource.logo.service.impl;

import cn.itsource.logo.domain.Brand;
import cn.itsource.logo.mapper.BrandMapper;
import cn.itsource.logo.query.BrandQuery;
import cn.itsource.logo.service.IBrandService;
import cn.itsource.logo.util.PageList;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 *
 * @author wbtest
 * @since 2019-02-28
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {
    @Autowired
    private  BrandMapper brandMapper;
    @Override
    public PageList<Brand> queryPage(BrandQuery query) {
        //分页查询: 以前在分页查询的时候:需要两个请求:总的条数和当前分页的数据
        //1:设置总的页数
        PageList<Brand> pageList = new PageList<>();
        long totalcount = brandMapper.queryPageCount(query);
        if (totalcount > 0) {
            pageList.setTotal(totalcount);
            //2:设置当前分页数据:
            // Mapper.xml中查询的是分页的数据:rows
            List<Brand> brands = brandMapper.queryPage(query);
            pageList.setRows(brands);
        }

        return pageList;
    }
}
