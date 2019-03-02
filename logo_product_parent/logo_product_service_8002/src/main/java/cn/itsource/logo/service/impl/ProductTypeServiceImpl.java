package cn.itsource.logo.service.impl;

import cn.itsource.logo.client.PageStaticClient;
import cn.itsource.logo.client.RedisClient;
import cn.itsource.logo.constants.GlobelConstants;
import cn.itsource.logo.domain.ProductType;
import cn.itsource.logo.mapper.ProductTypeMapper;
import cn.itsource.logo.service.IProductTypeService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author wbtest
 * @since 2019-02-28
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private PageStaticClient pageStaticClient;
    /**
     * @return
     */
    @Override
   /* public List<ProductType> treeData() {
        // 要得到name和儿子

        //return treeDataRecursion(0L);
        return treeDataLoop();
    }*/
    public List<ProductType> treeData() {
        // 根据key,从redis中获取数据 producttype是服务的提供者，我要调公共服务的redis，相对来说就是服务的消费者
        //判断是否有结果，如果有，就从缓存中拿，如果没有，就从数据库中去查，然后设置到我redis中去
        //1.根据key去redis中拿
        String jsonArrStr = redisClient.get(GlobelConstants.REDIS_PRODUCTTYPE_KEY);
        //先判断有没有
        if(StringUtils.isEmpty(jsonArrStr)){
            //没有就从数据库获取，
            List<ProductType> productTypes = treeDataLoop();
            //得到一个list，但在redis对象是一个json字符串，所以需要转换一下
             jsonArrStr = JSONArray.toJSONString(productTypes);
             //存入redis
            redisClient.set(GlobelConstants.REDIS_PRODUCTTYPE_KEY, jsonArrStr);
            System.out.println("没有从数据库拿");
            //已经设置到redis中，把这个数据返回到页面上
            return productTypes;

        }else {
            //如果查询producttypes有的话就返回到页面上，需要把json字符串转换成Json字符数组

            List<ProductType> productTypes = JSONArray.parseArray(jsonArrStr, ProductType.class);

            //返回到页面上
            return productTypes;
        }

    }


    /**
     * 使用循环方式:
     *   我们期望发送一条sql,把所有的子子孙孙的结构搞出来,但是搞不出来的;
     *   但是我们可以发送一条sql:把所有的数据拿回来,存在内存中,我可以写代码组装他的结构(在内存中完成的).
     * @return
     */
    private List<ProductType> treeDataLoop() {
        //1:获取所有的数据:
        List<ProductType> allProductType = productTypeMapper.selectList(null);

        //2:用于存在每一个对象和他的一个标识的 Long:id
        Map<Long,ProductType> map=new HashMap<>();
        for (ProductType productType : allProductType) {
            map.put(productType.getId(), productType);
        }

        //最终想要的结果:
        List<ProductType> result = new ArrayList<>();
        //3:遍历
        for (ProductType productType : allProductType) {
            //组装结构: productType:每一个对象:
            Long pid = productType.getPid();
            if(pid==0){
                result.add(productType);
            }else{
                // 找自己的老子,把自己添加到老子的儿子中
                ProductType parent=map.get(pid);// where id =pid
               /* //我老子的儿子
                List<ProductType> children = parent.getChildren();
                //把我自己放入老子的儿子中
                children.add(productType);*/
                parent.getChildren().add(productType);
            }
        }
        return result;
    }

    /**
     *
     * 查询无限极的树装数据:
     select * from t_product_type where pid= ?????

     先得到一级目录:
     得到0的儿子;
     遍历这个目录:
     分别的他的儿子:
     遍历这个儿子目录的儿子
     ....
     递归的遍历下去,只到没有儿子就返回.

     treeDataRecursion:就是获取儿子:谁的儿子?

     递归:性能很差的,每次都要发送sql,会发送多条sql:怎么优化??????
     ====>问题是发了很多条sql才导致性能差,我发一条把所有的数据都拿回就好了
     * @return
     */
    private List<ProductType> treeDataRecursion(Long pid) {
        //treeDataRecursion:获取传入参数的儿子
        //获取第一级目录
        List<ProductType> children =  getAllChildren(pid);// [1,100]

        //没有儿子
        if(children==null||children.size()==0)
        {
            //没有而自己就返回自己
            return children;
        }
        //有儿子
        for (ProductType child : children) {
            // child: 1
            //查询1的儿子
            List<ProductType> allChildren = treeDataRecursion(child.getId());// 1的儿子:
            // 把1的儿子给1
            child.setChildren(allChildren);

        }
        return children;
    }

    /**
     * 查询指定pid的儿子
     * @param pid
     * @return
     */
    private List<ProductType> getAllChildren(long pid) {
        // select * from t_product_type where pid= ?????
        Wrapper<ProductType> wrapper = new EntityWrapper<>();
        wrapper.eq("pid", pid); //select * from t_product_type where pid = #{pid}
        return  productTypeMapper.selectList(wrapper);
    }


    @Override
    public boolean updateById(ProductType entity) {
        //1.数据修改
        boolean b = super.updateById(entity);
        //2.模板的生成，此时模板实现功能是服务的消费者，消费模板提供者提供的服务
        //java后台内部的消费用feign
        //feign注入模板的接口 调用

        //3.先生成改变数据的html页面：producttype
       Map<String,Object> mapProductType =new HashMap<>();
       //  去数据库拿数据
        List<ProductType> productTypes = treeDataLoop();
        //将数据放入模型中
        mapProductType.put(GlobelConstants.PAGE_MODE, productTypes);//这个页面模型需要所有产品数据
        //哪个模板
        mapProductType.put(GlobelConstants.PAGE_TEMPLATE, "E:\\logo_parent\\logo_common_parent\\logo_common_interface\\src\\main\\resources\\template\\product.type.vm");
        //根据模板生成的地址
        mapProductType.put(GlobelConstants.PAGE_TEMPLATE_HTML, "E:\\logo_parent\\logo_common_parent\\logo_common_interface\\src\\main\\resources\\template\\product.type.vm.html");

        pageStaticClient.getPageStatic(mapProductType);

        //生成home的html页面
        Map<String,Object> mapHome=new HashMap<>();

        //数据:$model.staticRoot
        Map<String,String> staticRootMap=new HashMap<>();
        staticRootMap.put("staticRoot", "E:\\logo_parent\\logo_common_parent\\logo_common_interface\\src\\main\\resources\\");

        //这里页面需要目录的根路径
        mapHome.put(GlobelConstants.PAGE_MODE, staticRootMap);
        mapHome.put(GlobelConstants.PAGE_TEMPLATE, "E:\\logo_parent\\logo_common_parent\\logo_common_interface\\src\\main\\resources\\template\\home.vm");
        mapHome.put(GlobelConstants.PAGE_TEMPLATE_HTML, "E:\\logo_parent\\logo_common_parent\\logo_common_interface\\src\\main\\resources\\template\\home.vm.html");
        pageStaticClient.getPageStatic(mapHome);
        return b;
    }
}
























