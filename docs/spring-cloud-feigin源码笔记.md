#### FeignContext
1. FeignClientFactoryBean#getTarget -> feign(FeignContext context)
2. FeignContext根据contextId获取ApplicationContext对象，如果不存在则新建，并放入FeignContext中缓存起来, 其中key为contextId
3. 根据FeignClient构建的Application中传入FeignClientsConfiguration, PropertyPlaceholderAutoConfiguration配置类，并设置partner为外部容器
4. HystrixTargeter
5. ReflectiveFeign.ParseHandlersByName#apply
6. SpringMvcContract#parseAndValidateMetadata
7. SynchronousMethodHandler$Factory#create => SynchronousMethodHandler
8. FieldQueryMapEncoder.
9. LoadBalancerFeignClient.execute
10. CachingSpringLoadBalancerFactory#create => FeignLoadBalancer: ZoneAwareLoadBalancer

2. FeignClientsConfiguration
3. FeignRibbonClientAutoConfiguration

#### SpringClientFactory
1. CachingSpringLoadBalancerFactory#create -> SpringClientFactory#getLoadBalancer => ILoadBalancer
2. RibbonAutoConfiguration创建 SpringClientFactory实例对象，并加载 RibbonClientConfiguration 配置，生成ZoneAwareLoadBalancer负载均衡
