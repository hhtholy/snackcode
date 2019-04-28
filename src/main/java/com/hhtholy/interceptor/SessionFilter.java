package com.hhtholy.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/***
 * 过滤器的配置   
 * @author hhtholy
 *
 */
public class SessionFilter implements Filter {
	// 日志
	protected Logger log = LoggerFactory.getLogger(SessionFilter.class);
	//需要登录 才可以访问的资源
	private static Set<String> blackUrls = new HashSet<String>();
	//  filter 销毁的时候  调用这个方法  
	@Override
	public void destroy() {
	}
	
	//执行 过滤的时候可以用上 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 转换为  httpServletRequest
		HttpServletRequest httprequest  =  (HttpServletRequest) request;
		//获取请求的  uri
		String uri = httprequest.getRequestURI();
		response.setCharacterEncoding("UTF-8");//设置响应编码格式
		response.setContentType("text/html;charset=UTF-8");//设置响应编码格式
		if (uri.endsWith(".js")
		                || uri.endsWith(".css")
		                || uri.endsWith(".jpg")
		                || uri.endsWith(".gif")
		                || uri.endsWith(".png")
		                || uri.endsWith(".ico")) {
			log.debug("security filter, pass, " + httprequest.getRequestURI());
			chain.doFilter(request, response);
			return;
		}
		if(blackUrls.contains(uri)){
			//获取当前用户
			Subject subject = SecurityUtils.getSubject();
			//如果说用户没有登录的话
			if(!subject.isAuthenticated()){
				HttpServletResponse re = (HttpServletResponse) response;
				re.sendRedirect("toLogin");
			} else{  //需要处理的 uri
				chain.doFilter(request, response);
			}

		  }else{    //如果是白名单的话  那么直接通过
			chain.doFilter(request, response);
		}
	}

	//  初始化参数，在创建Filter时自动调用。当我们需要设置初始化参数的时候，可以写到该方法中。
	//只会调用一次
	@Override
	public void init(FilterConfig arg0) throws ServletException {

		 String url = "/miniTrade/";
		//这些是登录才可以访问的资源
		/**
		 * toOrderSetAccount
		 * toCart
		 * alipay
		 * pay
		 * foreaddCart
		 * buyitNow
		 * forebuy
		 * forecart
		 * foreCreateOrder
		 */
		blackUrls.add(url+"toOrderSetAccount");
		blackUrls.add(url+"toCart");
		blackUrls.add(url+"alipay");
		blackUrls.add(url+"pay");
		blackUrls.add(url+"foreaddCart");
		blackUrls.add(url+"buyitNow");
		blackUrls.add(url+"forebuy");
		blackUrls.add(url+"forecart");
		blackUrls.add(url+"foreCreateOrder");
	}

}
