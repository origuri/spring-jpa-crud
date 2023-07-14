package board.springjpacrud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // view에서 접근할 경로
    private String resourcePath = "/upload/**";
    // 실제 파일 저장 경로
    private String savePath = "file:///C:/spring-study/spring-jpa-crud/src/main/resources/static/img/";

    // savePath를 resourcePath로 변경시켜준다.
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry){
       registry.addResourceHandler(resourcePath)
               .addResourceLocations(savePath);
   }
}
