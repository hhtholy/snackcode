package com.hhtholy.snacktrade;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hht
 * @create 2019-05-03 20:29
 */
public class TestSubList {



    @Test
    public void f(){
        List<String> money=new ArrayList<String>();
        money.add("rmb");
        money.add("doller");
        money.add("ker");
        money.add("jpy");
        money.add("thb");
        System.out.println("money.size()---- "+money.size());

        List<String> other=money.subList(1, 4);
        System.out.println(money);
        System.out.println("money.size()-----"+money.size());
        System.out.println(other);
        System.out.println("other.size()----- "+other.size());
        other.clear();//子集合删除之后  原集合照样受影响，反过来修改原集合子集合也受影响。
        System.out.println(money);
        System.out.println("money.size()-----"+money.size());
        System.out.println(other);
        System.out.println("other.size()----- "+other.size());




    }
}
