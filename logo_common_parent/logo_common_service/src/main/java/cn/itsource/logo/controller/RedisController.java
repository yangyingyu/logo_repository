package cn.itsource.logo.controller;

import cn.itsource.logo.client.RedisClient;
import cn.itsource.logo.util.RedisUtil;
import org.springframework.web.bind.annotation.*;

/*
* 1.引入redis依赖
* 2.工具类的封装
*3.controller服务的暴露
* */
// 可以不实现接口，但接口的路径和controller的路径一致，起到一个约束作用
@RestController
@RequestMapping("/common")
public class RedisController implements RedisClient  {


    @RequestMapping(value = "/redis",method = RequestMethod.POST) // RequestParam加这个注解目的是使路径中的参数设置道该对应的参数
     public void set(@RequestParam("key") String key, @RequestParam("value") String value){
        RedisUtil.set(key, value);
    }
    @RequestMapping (value = "/redis/{key} " ,method = RequestMethod.GET)
    public String get(@PathVariable("key") String key){
        return RedisUtil.get(key);
    }


}
