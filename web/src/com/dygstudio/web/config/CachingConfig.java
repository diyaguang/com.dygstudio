package com.dygstudio.web.config;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by diyaguang on 2017/6/9.
 *  妈的，这里真是一个坑啊，有些东西必须要自己实验才能知道。好吧，来说正题：
 * 1.使用 ehCache 的话，配置 Cacheing，其jar版本应该使用 低版本的，2.10.3 ，最新的 3.3.1 版本不能这样配置，否则会报错
 * 2.在配置 EhCacheManagerFacotryBean 时，需要设置 setShared(true) 属性，否则会报错，因为 Hibernate默认也是使用EhCache
 * 作为缓存底层，这里要是配置的话，就会产生 cacheManager 的重复定义错误。
 * eg. 要吐槽下 Java 这个体系的版本问题，实在是让我这个 .net 出身的人实在的抓狂啊。版本的不兼容让人实在受不了，哪怕只是差一个
 * 最小的版本号，尼玛比如 ehCache，换个大版本，居然命名空间都换了，真是头疼。
 */
@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cacheManager){
        return new EhCacheCacheManager(cacheManager);
    }

    @Bean
    public EhCacheManagerFactoryBean ehcache(){
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }
}
