package com.hhtholy.service.impl;

import org.thymeleaf.spring5.context.SpringContextUtils;

/**
 * @author hht
 * @create 2019-05-08 15:46
 */
public class Test {

    public static void main(String[] args) {
        String demo = "11_kk";
        int i = demo.indexOf("_");

        String s = demo.substring(0, i);
        System.out.println(s);

        System.out.println(2.0 /1.0);
    }
}
