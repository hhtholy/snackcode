package com.hhtholy.interceptor;


import com.hhtholy.entity.Category;
import com.hhtholy.entity.OrderItem;
import com.hhtholy.entity.User;
import com.hhtholy.service.CategoryService;
import com.hhtholy.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @author hht
 * @create 2019-02-01 10:25
 *
 * 关于 购物车等  一些初始化数据 比如购物车的数量
 */


/**
 * 1. boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handle)方法：
 * 该方法将在请求处理之前进行调用，只有该方法返回true，
 * 才会继续执行后续的Interceptor和Controller，当返回值为true
 * 时就会继续调用下一个Interceptor的preHandle 方法，
 * 如果已经是最后一个Interceptor的时候就会是调用当前请求的Controller方法；
 *
 * 2.void postHandle (HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView)方法：
 * 该方法将在请求处理之后，DispatcherServlet进行视图返回渲染之前进行调用，可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作。
 * 3.void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex)方法：该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行，该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。用于进行资源清理。
 * ---------------------
 * 作者：sunp823
 * 来源：CSDN
 * 原文：https://blog.csdn.net/sunp823/article/details/51694662
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class InitInterceptor implements HandlerInterceptor {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
          HttpSession session =request.getSession();//获取session
          User user =(User)request.getSession().getAttribute("user");  //先获取 session中的user如果有的话 展示购物车中数量  没有的话 数量就是0
          int totalnum = 0;          //定义购物车中 商品的总数量
          if(user != null){
              List<OrderItem> orderItemsByUser = orderItemService.getOrderItemByUser(user);
              for(OrderItem item:orderItemsByUser){
                  totalnum += item.getNumber(); //一个订单项指的是 一种商品的购买
              }
          }
          //获取所有的分类   智能推荐应该写的
        List<Category> categories = categoryService.getCategoryList();
        String contextPath= request.getServletContext().getContextPath();
        request.getServletContext().setAttribute("categories_below_search", categories);
        session.setAttribute("cartTotalItemNumber", totalnum);
        request.getServletContext().setAttribute("contextPath", contextPath);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
