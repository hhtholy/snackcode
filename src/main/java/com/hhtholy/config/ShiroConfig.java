package com.hhtholy.config;

import com.hhtholy.Realm.CustomeRealm;
import com.hhtholy.interceptor.CustomShiroFilter;
import com.hhtholy.interceptor.SessionFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;


import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hht
 * @create 2019-04-18 20:32
 * Shiro框架相关的配置
 */
@Configuration
public class ShiroConfig {
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /**
     * 拦截 信息等
     * @param securityManager
     * @return
     */

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /*重要，设置自定义拦截器，当访问某些自定义url时，使用这个filter进行验证*/
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        // 如果map里面key值为authc,表示所有名为authc的过滤条件使用这个自定义的filter
        // map里面key值为myFilter,表示所有名为myFilter的过滤条件使用这个自定义的filter，具体见下方
        // 比如  filterChainDefinitionMap.put("/targetUrl", "myFilter");
        filters.put("myFilter", new CustomShiroFilter());
        shiroFilterFactoryBean.setFilters(filters);
        /*---------------------------------------------------*/

        //拦截器
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //　　anon:所有url都都可以匿名访问;
        //　　authc: 需要认证才能进行访问;
        //　　user:配置记住我或认证通过可以访问；
        //放开静态资源的过滤
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("http://62.234.17.235:8080/snacktrade/notifyUrl", "anon");
        //放开登录等url的过滤
        filterChainDefinitionMap.put("/toLogin", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        //对于指定的url，可以使用自定义filter进行验证
      //  filterChainDefinitionMap.put("/targetUrl", "myFilter");
        // 可以配置多个filter，用逗号分隔，按顺序过滤，下方表示先通过自定义filter的验证，再通过shiro默认过滤器的验证
        // filterChainDefinitionMap.put("/targetUrl", "myFilter,authc");
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        //  url从上向下匹配，当条件匹配成功时，就会进入指定filter并return(不会判断后续的条件)，因此这句需要在最下边
         filterChainDefinitionMap.put("/**", "authc");

        //如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/home");
        // 未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/toLogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
   @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(getCustomRealm());
        return securityManager;
    }

    /**
     * 拿用户信息的域
     * @return
     */
    @Bean
    public CustomeRealm getCustomRealm(){
        CustomeRealm myShiroRealm = new CustomeRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }
    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
