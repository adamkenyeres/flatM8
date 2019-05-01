package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class NullCheckAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(NullCheckAspect.class);

    @Around("@annotation(annotation.ImplicitNullCheck)")
    public Object checkNullParams(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

        if (Arrays.stream(args).anyMatch(Objects::isNull)) {
            LOGGER.error("Received null as paramater, method: {}/{}",
                    pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
            throw new IllegalArgumentException("ImplicitNullCheck annotation received null param.");
        } else {
            return pjp.proceed();
        }
    }
}
