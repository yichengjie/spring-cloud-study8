package com.yicj.study.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;
import org.springframework.core.io.FileSystemResource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Slf4j
@Configuration
@EnableConfigurationProperties(ConfigSupportProperties.class)
public class ConfigSupportConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    private final Integer orderNum = Ordered.HIGHEST_PRECEDENCE + 11;

    @Autowired(required = false)
    private List<PropertySourceLoader> propertySourceLoaders = Collections.EMPTY_LIST ;

    @Autowired
    private ConfigSupportProperties configSupportProperties ;


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!isHasCloudConfigLocator(this.propertySourceLoaders)){
            log.info("未启用Config Server 管理配置");
            return;
        }
        log.info("检查Config Service 配置资源");
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        log.info("加载PropertySource源: " + propertySources.size() + "个");
        if (!configSupportProperties.isEnable()){
            log.warn("未启用配置备份功能，可以使用{}.enable打开", ConfigSupportProperties.CONFIG_PREFIX);
            return;
        }
        if (isCloudConfigLoaded(propertySources)){
            PropertySource<?> cloudPropertySource = getLoadedCloudPropertySource(propertySources);
            log.info("成功获取ConfigService配置资源！");
            // 备份
            Map<String, Object> backupPropertyMap = makeBackupPropertyMap(cloudPropertySource);
            doBackup(backupPropertyMap, configSupportProperties.getFallbackLocation());
        }else {
            log.error("获取ConfigService配置资源失败!");
            Properties backupProperty = loadBackupProperty(configSupportProperties.getFallbackLocation()) ;
            if (backupProperty != null){
                HashMap backupSourceMap = new HashMap(backupProperty) ;
                MapPropertySource backSource = new MapPropertySource("backupSource", backupSourceMap);
                propertySources.addFirst(backSource);
                log.warn("使用备份的配置启动：{}", configSupportProperties.getFallbackLocation());
            }
        }
    }

    // 是否启用了Spring Cloud Config获取配置资源
    private boolean isHasCloudConfigLocator(List<PropertySourceLoader> loaders){
        for (PropertySourceLoader loader: loaders){
            if (loader instanceof ConfigServicePropertySourceLocator){
                return true ;
            }
        }
        return false ;
    }

    // 是否启用Spring Config
    private boolean isCloudConfigLoaded(MutablePropertySources propertySources){
        if (getLoadedCloudPropertySource(propertySources) == null){
            return false ;
        }
        return true ;
    }

    private PropertySource<?> getLoadedCloudPropertySource(MutablePropertySources propertySources){
        String bootstrapPropertySourceName = PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME;
        if (!propertySources.contains(bootstrapPropertySourceName)){
            return null ;
        }
        PropertySource<?> propertySource = propertySources.get(bootstrapPropertySourceName);
        if (propertySource instanceof CompositePropertySource){
            CompositePropertySource compositePropertySource = (CompositePropertySource) propertySource ;
            for (PropertySource<?> source: compositePropertySource.getPropertySources()){
                if (source.getName().equals("configService")){
                    return source ;
                }
            }
        }
        return null ;
    }


    private Map<String,Object> makeBackupPropertyMap(PropertySource<?> propertySource){
        Map<String, Object> backupSourceMap = new HashMap<>() ;
        if (!(propertySource instanceof CompositePropertySource)){
            return backupSourceMap ;
        }
        CompositePropertySource composite = (CompositePropertySource)propertySource ;
        for (PropertySource<?> source: composite.getPropertySources()){
            if (source instanceof MapPropertySource){
                MapPropertySource mapPropertySource = (MapPropertySource) source ;
                for (String propertyName: mapPropertySource.getPropertyNames()){
                    // 前面的配置覆盖后面的配置
                    if (!backupSourceMap.containsKey(propertyName)){
                        backupSourceMap.put(propertyName, mapPropertySource.getProperty(propertyName)) ;
                    }
                }
            }
        }
        return backupSourceMap;
    }

    private void doBackup(Map<String, Object> backupPropertyMap, String filePath){
        FileSystemResource fileSystemResource = new FileSystemResource(filePath) ;
        File backupFile = fileSystemResource.getFile() ;
        try {
            if (!backupFile.exists()){
                backupFile.createNewFile() ;
            }
            if (!backupFile.canWrite()){
                log.error("无法读写文件：{}", fileSystemResource.getPath());
            }
            Properties properties = new Properties() ;
            Iterator<String> keyIterator = backupPropertyMap.keySet().iterator() ;
            while (keyIterator.hasNext()){
                String key = keyIterator.next();
                properties.setProperty(key, String.valueOf(backupPropertyMap.get(key))) ;
            }
            FileOutputStream fos = new FileOutputStream(fileSystemResource.getFile());
            properties.store(fos, "Backup Cloud Config");
        }catch (IOException e){
            log.error("文件操作失败: {}", fileSystemResource.getPath(), e);
        }
    }

    private Properties loadBackupProperty(String filePath){
        PropertiesFactoryBean propertiesFactory = new PropertiesFactoryBean() ;
        try {
            FileSystemResource fileSystemResource = new FileSystemResource(filePath) ;
            propertiesFactory.setLocation(fileSystemResource);
            propertiesFactory.afterPropertiesSet();
            return propertiesFactory.getObject();
        }catch (IOException e){
            log.error("加载配置文件出错：{}", filePath, e);
            return null ;
        }
    }

    @Override
    public int getOrder() {
        return orderNum;
    }
}
