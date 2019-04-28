package com.hhtholy.interceptor;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;


/****
 * 
 *  数据配置    相当于 Spring中的xml配置 bean
 *   
 * @author hhtholy
 *
 */


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	   public static final String LONGIN_USER = "user";

	   //创建一个 Filter  Bean
	  /* @Bean(name="sessionFilter")
	   public  Filter sessionFilter(){
		   return new SessionFilter();
	   }*/

	   //创建一个interceptor  Bean
	  @Bean
	   public InitInterceptor getInterceptor(){
	     	return new InitInterceptor();
	   }
	   
	   //注册  filter   如果需要注册多个  filter的话   那么需要书写多个这个方法  
	/*   @Bean
	    public FilterRegistrationBean toFilterRegistration() {
	        FilterRegistrationBean registration = new FilterRegistrationBean();
	        registration.setFilter(sessionFilter());
	        registration.addUrlPatterns("/*");
	        registration.addInitParameter("paramName", "paramValue");
	        registration.setName("sessionFilter");
	        registration.setOrder(1);
	        return registration;
	    }
*/
	    @Override
    	public void addInterceptors(InterceptorRegistry registry) {
	      registry.addInterceptor(getInterceptor()).addPathPatterns("/**");
	    }
}
