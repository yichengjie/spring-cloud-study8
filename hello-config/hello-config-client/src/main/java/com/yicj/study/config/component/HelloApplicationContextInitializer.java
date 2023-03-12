
package com.yicj.study.config.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class HelloApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Autowired(required = false)
    private List<PropertySourceLocator> propertySourceLocators = Collections.EMPTY_LIST;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        String bootstrapPropertySourceName = PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME;
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        PropertySource loadedCloudPropertySource = this.getLoadedCloudPropertySource(propertySources);

        log.info("propertySources : {}", propertySources);
        Iterator<PropertySource<?>> iterator = propertySources.iterator();
        while (iterator.hasNext()){
            PropertySource<?> source = iterator.next();
            System.err.println("===> sourceName : " +source.getName()+", source : " + source);
        }
    }

    /**
     * 是否启用了Spring Cloud Config获取配置资源
     *
     * @param propertySourceLocators
     * @return
     */
    private boolean isHasCloudConfigLocator(List<PropertySourceLocator> propertySourceLocators) {
        for (PropertySourceLocator sourceLocator : propertySourceLocators) {
            if (sourceLocator instanceof ConfigServicePropertySourceLocator) {
                return true;
            }
        }
        return false;
    }
    /**
     * 获取加载的Cloud Config 配置项
     *
     * @param propertySources
     * @return
     */
    private PropertySource getLoadedCloudPropertySource(MutablePropertySources propertySources) {
        if (!propertySources.contains(PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME)) {
            return null;
        }
        PropertySource propertySource = propertySources.get(PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME);
        if (propertySource instanceof CompositePropertySource) {
            for (PropertySource<?> source : ((CompositePropertySource) propertySource).getPropertySources()) {
                if (source.getName().equals("configService")) {
                    return source;
                }
            }
        }
        return null;
    }

}
