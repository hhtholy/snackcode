package com.hhtholy.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author hht
 * @create 2019-04-28 16:40
 *
 */
public class CustomShiroFilter extends AccessControlFilter {
    private static Logger log = LoggerFactory.getLogger(CustomShiroFilter.class);
    //判断是否拦截，false为拦截，true为允许
    @Override
    public boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object object) throws Exception {
        Subject subject = getSubject(req,resp);
        String url = getPathWithinApplication(req);
        log.info("当前用户正在访问的url为 " + url);
        log.info("subject.isPermitted(url);"+subject.isPermitted(url));
        //可自行根据需要判断是否拦截，可以获得subject判断用户权限，也可以使用req获得请求头请求体信息
     //return true;
        return false;

    }

    //上面的方法返回false后(被拦截)，会进入这个方法；这个方法返回false表示处理完毕(不放行)；返回true表示需要继续处理(放行)
    @Override
    public boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //从req中获得的值，也可以自己使用其它判断是否放行的方法
        String username = request.getParameter("name");
        String password = request.getParameter("password");
      //创建token对象
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
         //使用subject对象的login方法验证该token能否登录(使用方法2中的shiroRealm.java中的方法验证)
            subject.login(usernamePasswordToken);
        } catch (Exception e) {
            //log.info("登陆失败");
            //log.info(e.getMessage());
            return false;
        }
        //log.info("登陆成功");
        return true;
    }
}
