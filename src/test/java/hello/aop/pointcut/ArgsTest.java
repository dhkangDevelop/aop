package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

public class ArgsTest {
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    @Test
    void args() {
        Assertions.assertThat(pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        Assertions.assertThat(pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        Assertions.assertThat(pointcut("args()")
                .matches(helloMethod, MemberServiceImpl.class)).isFalse();

        Assertions.assertThat(pointcut("args(..)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        Assertions.assertThat(pointcut("args(*)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        Assertions.assertThat(pointcut("args(String, ...)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

    }

    /**
     * execution(* *(java.io.Serializable)) : 메서드의 시그니처로 판단 (정적)
     * args(java.io.Serializable) : 런타임에 잘못된 인수로 판단 (동적)
     */
    @Test
    void argsVsExecution() {
        // args : 상위 타입 허용
        Assertions.assertThat(pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        Assertions.assertThat(pointcut("args(java.io.Serializable)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        Assertions.assertThat(pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // execution : 정확한 타입만 허용
        Assertions.assertThat(pointcut("execution(* *(String))")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        Assertions.assertThat(pointcut("execution(* *(java.io.Serializable))")
                .matches(helloMethod, MemberServiceImpl.class)).isFalse(); // 매칭 실패
        Assertions.assertThat(pointcut("execution(* *(Object))")
                .matches(helloMethod, MemberServiceImpl.class)).isFalse(); // 매칭 실패
    }

}
