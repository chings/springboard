package springboard.web;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.net.URL;

public class HistoryRouterCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        URL indexPage = ClassLoader.getSystemClassLoader().getResource("/static/index.html");
        return indexPage != null;
    }

}