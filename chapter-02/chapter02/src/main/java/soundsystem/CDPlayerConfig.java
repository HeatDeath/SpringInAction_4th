package soundsystem;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 注解类表示这个类可以使用 Spring IoC 容器作为 bean 定义的来源
@ComponentScan // 启用Spring中的组件扫描，寻找带有 @Component 注解的类，并为其创建 Bean
                // 默认扫描与配置类相同的包
public class CDPlayerConfig {

}
