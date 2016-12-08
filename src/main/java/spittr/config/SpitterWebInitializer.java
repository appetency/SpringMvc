package spittr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import spittr.web.WebConfig;

/*配置DispatcherServlet*/
/*
当部署到Servlet 3.0容器中的时候，容器会自动发现 它，并用它来配置Servlet上下文。
*/
public class SpitterWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] { RootConfig.class };
  }
/*
  getRootConfigClasses()方法返回的带有@Configuration注解的类将会 用来配置ContextLoaderListener创建的应用上下文中的bean。
*/
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] { WebConfig.class };
  }
  /*getServletConfigClasses()方法中，我们要求DispatcherServlet加载应用上下文时，使用定义在WebConfig配置类（使用Java配置）中的bean。*/
/*
  GetServlet-ConfigClasses()方法返回的带有@Configuration注解的类将会用来定义DispatcherServlet应用上下文中的bean。
*/

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }  /*它会将一个或多个路径映射 到DispatcherServlet上*/


}