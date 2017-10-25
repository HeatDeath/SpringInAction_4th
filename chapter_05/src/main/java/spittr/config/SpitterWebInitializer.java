package spittr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import spittr.web.WebConfig;

/**
 * 扩展了 AbstractAnnotationConfigDispatcherServletInitializer 的任意类都会自动地配置
 * DispatcherServlet 和 Spring 应用上下文
 * Spring 的应用上下文会位于应用程序的 Servlet 上下文中
 *
 * 通过 AbstractAnnotationConfigDispatcherServletInitializer 来配置 DispatcherServlet
 * 是传统 web.xml 方式的替代方案
 */
public class SpitterWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  
  @Override
  protected Class<?>[] getRootConfigClasses() {
    /**
     * 返回带有 @Configuration 注解的类将会用来配置 ContextLoaderListener 创建的应用上下文中的 bean
     */
    return new Class<?>[] { RootConfig.class };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    /**
     * 返回带有 @Configuration 注解的类将会用来定义 DispatcherServlet 应用上下文中的 bean
     * 指定配置类
     */
    return new Class<?>[] { WebConfig.class };
  }

  @Override
  protected String[] getServletMappings() {
    /**
     * 将 DispatcherServlet 映射到 "/"
     */
    return new String[] { "/" };
  }

}