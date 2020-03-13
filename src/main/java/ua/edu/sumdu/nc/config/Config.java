package ua.edu.sumdu.nc.config;


import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ua.edu.sumdu.nc.beans.Operation;
import ua.edu.sumdu.nc.beans.OperationRecord;
import ua.edu.sumdu.nc.dao.DAO;


@PropertySource(value = "classpath:db.properties")
@ComponentScan(basePackages = "ua.edu.sumdu.nc")
@EnableWebMvc
@Configuration
public class Config implements WebMvcConfigurer {

    @Bean
    public ViewResolver configureViewResolver() {
        InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
        viewResolve.setPrefix("/WEB-INF/view/");
        viewResolve.setSuffix(".jsp");
        return viewResolve;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean()
    public Operation operation() {
        return new Operation();
    }

    @Bean
    @Scope("prototype")
    public OperationRecord operationRecord() {
        return new OperationRecord();
    }

    @Bean
    public DAO dao() {
        return new DAO();
    }
}
