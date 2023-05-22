package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

// order : 오름차순으로 적용
@Slf4j
@Aspect
public class AspectV6Advice {

    @Before("hello.aop.order.aop.Pointcuts.allOrder() && hello.aop.order.aop.Pointcuts.allService()")
    public void doBefore(JoinPoint joinpoint) {
        log.info("[before] = {}", joinpoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allOrder() && hello.aop.order.aop.Pointcuts.allService()",
    returning = "result")
    public void doAfterReturning(JoinPoint joinpoint, Object result) {
        log.info("[afterReturning] = {}", joinpoint.getSignature());
        log.info("[return] = {}", result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.allOrder() && hello.aop.order.aop.Pointcuts.allService()",
            throwing = "ex")
    public void doAfterThrowing(JoinPoint joinpoint, Exception ex) {
        log.info("[doAfterThrowing] = {}", joinpoint.getSignature());
        log.info("[ex] = {}, [message] = {}", ex, ex.getMessage());
    }

    @After(value = "hello.aop.order.aop.Pointcuts.allOrder() && hello.aop.order.aop.Pointcuts.allService()")
    public void doAfter(JoinPoint joinpoint) {
        log.info("[after] = {}", joinpoint.getSignature());
    }

}
