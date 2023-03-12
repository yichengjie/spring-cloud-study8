#### FeignContext
1. FeignAutoConfiguration创建FeignContext实例，并加载 FeignClientsConfiguration
2. FeignClientFactoryBean#getTarget -> feign(FeignContext context)
3. FeignContext根据contextId获取ApplicationContext对象，如果不存在则新建，并放入FeignContext中缓存起来, 其中key为contextId
4. 根据FeignClient构建的Application中传入FeignClientsConfiguration, PropertyPlaceholderAutoConfiguration配置类，并设置partner为外部容器
5. HystrixTargeter
6. ReflectiveFeign.ParseHandlersByName#apply
7. SpringMvcContract#parseAndValidateMetadata
8. SynchronousMethodHandler$Factory#create => SynchronousMethodHandler
9. FieldQueryMapEncoder.
10. LoadBalancerFeignClient.execute
11. CachingSpringLoadBalancerFactory#create => FeignLoadBalancer: ZoneAwareLoadBalancer


#### RibbonAutoConfiguration
1. 实例化SpringClientFactory

2. 实例化LoadBalancerClient: RibbonLoadBalancerClient
s
3. 实例化LoadBalancedRetryFactory: RibbonLoadBalancedRetryFactory

1. FeignRibbonClientAutoConfiguration 生成 
2. CachingSpringLoadBalancerFactory#create -> SpringClientFactory#getLoadBalancer => ILoadBalancer
3. RibbonAutoConfiguration创建 SpringClientFactory实例对象，并加载 RibbonClientConfiguration 配置，生成ZoneAwareLoadBalancer负载均衡

#### FeignRibbonClientAutoConfiguration
1. 实例化CachingSpringLoadBalancerFactory
