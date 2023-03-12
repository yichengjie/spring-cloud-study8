1. filter链
    ```text
    RemoveCachedBodyFilter@e47637c}, order = -2147483648]"
    AdaptCachedBodyGlobalFilter@5afba80c}, order = -2147482648]"
    NettyWriteResponseFilter@46c475ba}, order = -1]"
    ForwardPathFilter@7fdf7359}, order = 0]"
    [[RewritePath /client-a/(?<remaining>.*) = '/${remaining}'], order = 1]"
    RouteToRequestUrlFilter@606f0f70}, order = 10000]"
    LoadBalancerClientFilter@2059c3ff}, order = 10100]"
    WebsocketRoutingFilter@4a36a35d}, order = 2147483646]"
    NettyRoutingFilter@2f8ab988}, order = 2147483647]"
    ForwardRoutingFilter@1a53ac0c}, order = 2147483647]"
    ```