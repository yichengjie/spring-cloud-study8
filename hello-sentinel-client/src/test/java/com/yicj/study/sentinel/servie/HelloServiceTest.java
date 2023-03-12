package com.yicj.study.sentinel.servie;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.yicj.study.sentinel.SentinelClientApplication;
import com.yicj.study.sentinel.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SentinelClientApplication.class, properties = {})
public class HelloServiceTest {

    @Autowired
    private HelloService helloService ;

    @Test
    public void doSomething(){
        this.initFlowRules();
        while (true){
            helloService.doSomething();
        }
    }

    public void initFlowRules(){
        List<FlowRule> ruleList = new ArrayList<>() ;
        FlowRule rule = new FlowRule() ;
        rule.setResource("doSomething") ;
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS) ;
        rule.setCount(20) ;
        ruleList.add(rule);
        FlowRuleManager.loadRules(ruleList);
    }
}
