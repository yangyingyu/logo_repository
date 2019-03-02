package cn.itsource.logo.controller;

import cn.itsource.logo.client.PageStaticClient;
import cn.itsource.logo.constants.GlobelConstants;
import cn.itsource.logo.util.VelocityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/common")
public class PageController implements PageStaticClient {

    @RequestMapping(value = "/page", method= RequestMethod.POST)
    public void getPageStatic(@RequestBody Map<String, Object> map) {

        Object model = map.get(GlobelConstants.PAGE_MODE);
        String templateFilePathAndName =(String) map.get(GlobelConstants.PAGE_TEMPLATE);
        String targetFilePathAndName = (String)map.get(GlobelConstants.PAGE_TEMPLATE_HTML);

        System.out.println("model==="+model);
        System.out.println("templateFilePathAndName==="+templateFilePathAndName);
        System.out.println("targetFilePathAndName==="+targetFilePathAndName);
        VelocityUtils.staticByTemplate(model, templateFilePathAndName, targetFilePathAndName);
    }
}
