#### FeignAutoConfiguration
1. 实例化FeignContext，并加载 FeignClientsConfiguration配置，
2. 加载HystrixFeignTargeterConfiguration配置，实例化 Targeter: HystrixTargeter

##### FeignClientsConfiguration
1. 实例化 Decoder: OptionalDecoder(ResponseEntityDecoder(SpringDecoder))
2. 实例化 Encoder: SpringEncoder
3. 实例化 Contract: SpringMvcContract
4. 实例化 Feign.Builder: Feign.builder().retryer(retryer)
5. 实例化 FeignLoggerFactory

#### RibbonAutoConfiguration
1. 实例化 SpringClientFactory, 并加载 RibbonClientConfiguration 配置
2. 实例化 RibbonLoadBalancerClient
3. 实例化 LoadBalancedRetryFactory: RibbonLoadBalancedRetryFactory

##### RibbonClientConfiguration
1. 实例化 IClientConfig: DefaultClientConfigImpl
2. 实例化 IRule: ZoneAvoidanceRule
3. 实例化 IPing: DummyPing
4. 实例化 ServerList<Server>: ConfigurationBasedServerList serverList
5. 实例化 ServerListUpdater: PollingServerListUpdater
6. 实例化 ILoadBalancer: ZoneAwareLoadBalancer
7. 实例化 ServerListFilter<Server>: ZonePreferenceServerListFilter
8. 实例化 RibbonLoadBalancerContext
9. 实例化 RetryHandler: DefaultLoadBalancerRetryHandler
10. 实例化 ServerIntrospector: DefaultServerIntrospector

#### FeignRibbonClientAutoConfiguration
1. 实例化 CachingSpringLoadBalancerFactory
2. 实例化 Request.Options
3. Import导入XXXFeignLoadBalancedConfiguration配置类实例话Client feignClients

#### FeignClientFactoryBean
2. FeignClientFactoryBean#getTarget -> feign(FeignContext context)
3. FeignContext根据contextId获取ApplicationContext对象，如果不存在则新建，并放入FeignContext中缓存起来, 其中key为contextId
4. 根据FeignClient构建的Application中传入 FeignClientsConfiguration , PropertyPlaceholderAutoConfiguration配置类，并设置partner为外部容器

5. getTarget -> loadBalance -> HystrixTargeter#target  -> feign#target(target) -> build()#newInstance(target) -> ReflectiveFeign.newInstance生成代理对象

10. LoadBalancerFeignClient#execute -> lbClient => FeignLoadBalancer -> FeignLoadBalancer#executeWithLoadBalancer

11. SynchronousMethodHandler#invoke -> executeAndDecode -> LoadBalancerFeignClient#execute

12. CachingSpringLoadBalancerFactory#create(clientName) -> ILoadBalancer: ZoneAwareLoadBalancers => FeignLoadBalancer(lb, config, serverIntrospector)


##### 根据ReflectiveFeign#newInstance生成代理对象

7. ReflectiveFeign.ParseHandlersByName#apply
8. SpringMvcContract#parseAndValidateMetadata
9. SynchronousMethodHandler$Factory#create => SynchronousMethodHandler
10. FieldQueryMapEncoder.
