package springboard.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnExpression("${springboard.swagger.enabled:true}")
@EnableSwagger2
public class SwaggerDocumentationConfig {

}
