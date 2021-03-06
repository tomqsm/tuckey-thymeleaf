package biz.letsweb.tuckey.thymeleaf.aspects;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.javasimon.Split;

@Aspect
public class TimedAspect {

    @Pointcut("execution(@Timed * *(..))")
    public void timedMethods() {
    }

    @Before("timedMethods()")
    public void testModeOnly(JoinPoint joinPoint) {
//        final Method m = extractMethod(joinPoint);
//        final Timed ti = (Timed) m.getDeclaredAnnotations()[0];
//
//        System.out.println("*before action* signature:" + " message: " + ti.message() + " other: ");
    }

    @Around("timedMethods()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Method method = extractMethod(proceedingJoinPoint);
        System.out.println("*** Aspect before: " + method.getName());
        final Timed timed = (Timed) method.getDeclaredAnnotations()[0];
        Split split = Split.start();
        final Object proceeded = proceedingJoinPoint.proceed();
        final Signature signature = proceedingJoinPoint.getSignature();
        System.out.println(String.format("%s#%s [%s] %s", signature.getDeclaringType().getSimpleName(), signature.getName(), split, timed.message()));
        System.out.println("*** Aspect after: " + method.getName());
        return proceeded;
    }

    @After("timedMethods()")
    public void afterAction(JoinPoint joinPoint) {
//        System.out.println("*after action*");
    }

    @AfterReturning("timedMethods()")
    public void afterTransactionalMethod(JoinPoint joinPoint) {
//        System.out.println("*after return action*");
    }

    /**
     Runs before Around annotation when exception thrown.
     @param joinPoint
     @param e
     */
    @AfterThrowing(pointcut = "timedMethods()", throwing = "e")
    public void afterExcpetion(JoinPoint joinPoint, Exception e) {
        System.out.println("Threw excpetion " + e);
    }

    private Method extractMethod(final JoinPoint joinPoint) {
        final MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        final Method m = ms.getMethod();
        return m;
    }

    private Method extractMethod(final ProceedingJoinPoint joinPoint) {
        final MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        final Method m = ms.getMethod();
        return m;
    }
}
