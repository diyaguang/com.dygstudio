package com.dygstudio.web.service;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by diyaguang on 2017/6/9.
 */
@Service("homeService")
@Transactional
public class HomeServiceImpl implements HomeService {

    @Autowired
    private CacheManager cm;

    public String getCacheData(String id){
        return (String)cm.getCache("dygstudio").get(id).getObjectValue();
    }
    public void setCacheData(String id,String value){
        cm.getCache("dygstudio").put(new Element(id,value));
    }
}
