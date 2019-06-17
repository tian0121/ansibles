package com.zty;

import com.github.pagehelper.PageHelper;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class PageHelperConfig {


    @Bean
    public PageHelper PageHelper(){
        PageHelper pageHelper=new PageHelper();
        Properties properties=new Properties();
        properties.setProperty("helperDialect","oracle");
        properties.setProperty("reasonable","true");
        properties.setProperty("supportMethodsArguments","true");
        properties.setProperty("params","count=countSql");
        properties.setProperty("offsetPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}