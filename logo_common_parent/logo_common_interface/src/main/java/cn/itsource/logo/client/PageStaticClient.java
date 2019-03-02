package cn.itsource.logo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "COMMON-PRIVODER",fallbackFactory = PageStaticClientFactory.class) //表示对哪一个服务进行处理
@RequestMapping("/common")
public interface  PageStaticClient {

        /*
        * 根据给定数据跟给定末班最终生成一个html页面
        * */

        @RequestMapping (value = "/page" ,method = RequestMethod.GET)
        void getPageStatic(Map<String ,Object>map);

}
