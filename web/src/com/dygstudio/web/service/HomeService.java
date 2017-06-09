package com.dygstudio.web.service;

import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by diyaguang on 2017/6/9.
 */
@Service("homeService")
@Transactional
public interface HomeService {
    public String getCacheData(String id);
    public void setCacheData(String id,String value);
}
