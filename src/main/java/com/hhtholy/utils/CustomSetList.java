package com.hhtholy.utils;

import com.hhtholy.entity.Product;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hht
 * @create 2019-05-22 15:21
 */
public class CustomSetList<T> extends ArrayList<T> {
    @Override
    public boolean add(T object) {
        if (size() == 0) {
            return super.add(object);
        } else {
            int count = 0;
            for (T t : this) {
                if (t.equals(object)) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                return super.add(object);
            } else {
                return false;
            }
        }
    }

    public static void main(String[] args) {

        CustomSetList customSetList = new CustomSetList();

        customSetList.add(new Product("demo","2"));
        customSetList.add(new Product("demoq","2"));
        customSetList.add(new Product("demo","2"));


        System.out.println(customSetList.toArray().toString());

    }

}
