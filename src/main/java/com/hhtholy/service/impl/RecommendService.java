package com.hhtholy.service.impl;

import com.hhtholy.controller.fore.ForeOrderController;
import com.hhtholy.entity.*;
import com.hhtholy.service.CategoryService;
import com.hhtholy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.*;

/**
 * @author hht
 * @create 2019-05-08 14:36
 */
@Service
public class RecommendService {

    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;

    /*
     UserCF的核心思想即为根据用户数据模拟向量相似度，
     我们根据这个相似度，来找出指定用户的相似用户，
     然后将相似用户买过的而指定用户没有买的东西推荐给指定用户，
     推荐度的计算也是结合了相似用户与指定用户的相似度累加。
     注意这里我们默认是用户的隐反馈行为，所以每一个物品的影响因子默认为1。

     协同过滤算法的核心思想是根据用户间的相似度，来进行推荐。
     */

    /**
     * 推荐产品·
     * @param user
     * @param orders
     * @return
     */
    public List<Product> recommedProducts(User user,List<Order_> orders){
         int max = productService.getMaxId();
         int min = productService.getMinId();
        Random random = new Random();
        List<Product> products = new ArrayList<>(); //推荐的产品
        if(user == null){ //如果用户没有登录的话  不需要推荐  返回随机的4个产品即可
               int flag = 0;
               while(flag <= 3){
                   int index = random.nextInt(max-min + 1) + min;
                   Product product = productService.getProduct(index);
                   if(product != null){
                       products.add(product);
                       flag ++;
                   }
               }
               return products;
        }
        /*
          A 1 2 3 4 5
          B a 2 2 2
          C 2 1 3 4
         */

        List<List<String>> arrList = new ArrayList<>();//临时集合

        for (Order_ order : orders) {
            boolean flag1 = false;
            User userResult = order.getUser(); //获取到对应的用户
            Integer id = userResult.getId(); //因为一个用户可能会有多个订单 用户id

            if(arrList.size() >= 1){ //添加第二列的时候 判断是否为同一个用户
                for (List<String> list : arrList) { //
                    String userId = list.get(0); //用户id
                    if(String.valueOf(id).equals(userId)){ // 如果是的话
                        /////////////////////
                        List<OrderItem> orderItems = order.getOrderItems();//获取订单对应的订单项
                        for (OrderItem orderItem : orderItems) { //遍历每一个订单项
                            Product product = orderItem.getProduct(); //获取产品
                            list.add(product.getName());
                        }
                        flag1 = true;
                        break;
                        ///////////////
                    }// if
                }//for(List<String> list : arrList)

            }//if arrList.size() >= 1
            if(!flag1){ //如果不是同一个用户
                List<String> list = new ArrayList<>();//temp
                list.add(String.valueOf(userResult.getId())); //每个子集合 第一个添加元素为 用户的id

                List<OrderItem> orderItems = order.getOrderItems();//获取订单对应的订单项
                for (OrderItem orderItem : orderItems) { //遍历每一个订单项
                    Product product = orderItem.getProduct(); //获取产品
                    list.add(product.getName());
                }
                arrList.add(list); //放到大集合中
            }

        }//for

        int N = arrList.size();
        int[][] sparseMatrix = new int[N][N];//建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        Map<String, Integer> userItemLength = new HashMap<>();//存储每一个用户对应的不同物品总数  eg: A 3
        Map<String, Set<String>> itemUserCollection = new HashMap<>();//建立物品到用户的倒排表 eg: a A B
        Set<String> items = new HashSet<>();//辅助存储物品集合
        ///userID  idUser
        Map<String, Integer> userID = new HashMap<>();//辅助存储每一个用户的用户ID映射
        Map<Integer, String> idUser = new HashMap<>();//辅助存储每一个ID对应的用户映射

        //遍历大集合
        for (int i = 0; i < arrList.size(); i++) {
            List<String> littleList = arrList.get(i);//获取集合元素
            userItemLength.put(littleList.get(0),littleList.size() -1); // 用户id 和 产品数量
            userID.put(littleList.get(0),i);  //用户id与稀疏矩阵建立对应关系
            idUser.put(i,littleList.get(0));
            for (int j = 1; j < littleList.size(); j++) {
                if(items.contains(littleList.get(j))){
                    itemUserCollection.get(littleList.get(j)).add(littleList.get(0));
                }else{
                    items.add(littleList.get(j));
                    itemUserCollection.put(littleList.get(j),new HashSet<String>());
                    itemUserCollection.get(littleList.get(j)).add(littleList.get(0));
                }
            }
        } //for

        System.out.println(itemUserCollection.toString());
        //计算相似度矩阵【稀疏】
        Set<Map.Entry<String, Set<String>>> entrySet = itemUserCollection.entrySet();
        Iterator<Map.Entry<String, Set<String>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<String> commonUsers = iterator.next().getValue();
            for (String user_u : commonUsers) {  //  遍历集合
                for (String user_v : commonUsers) {
                    if(user_u.equals(user_v)){ //遍历两次
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;//计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        //如果说用户没有买过东西的话  那么userID是获取不到值的 因为这个映射是根据订单来的
        String recommendUser = String.valueOf(user.getId()); //需要 被智能推荐的人 （id）
        if(userID.get(recommendUser) == null){ //没有买过东西的话  随机生成4个产品
            int flag = 0;
            while(flag <= 3){
                int index = random.nextInt(max-min + 1) + min;
                Product product = productService.getProduct(index);
                if(product != null){
                    products.add(product);
                    flag ++;
                }
            }
            return products;
        }

        //计算用户之间的相似度【余弦相似性】
        int recommendUserId = userID.get(recommendUser);
        for (int j = 0;j < sparseMatrix.length; j++) {
            if(j != recommendUserId){
                double result;
                double temp =sparseMatrix[recommendUserId][j] * 1.0;
                double sqrt = Math.sqrt(userItemLength.get(idUser.get(recommendUserId))*userItemLength.get(idUser.get(j))) *1.0;
                if(sqrt == 0.0){
                    result = 0.0;
                }else {
                    result = temp /sqrt;
                }
                System.out.println(idUser.get(recommendUserId)+"--"+idUser.get(j)+"相似度:"+result);
            }
        }
        boolean flag = false;
        //计算指定用户recommendUser的物品推荐度
        for(String item: items){//遍历每一件物品
            Set<String> users = itemUserCollection.get(item);//得到购买当前物品的所有用户集合
            if(!users.contains(recommendUser)){//如果被推荐用户没有购买当前物品，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for(String userTemp: users){
                    double result = 0.0;
                    double temp = sparseMatrix[userID.get(recommendUser)][userID.get(userTemp)] * 1.0;
                    double sqrt = Math.sqrt(userItemLength.get(recommendUser) * userItemLength.get(userTemp)) *1.0;
                    if(sqrt == 0.0){
                        result = 0.0;
                    }else {
                        result = temp /sqrt;
                    }
                    itemRecommendDegree += result;//推荐度计算
                }//for (!users.contains)
                System.out.println("The item "+item+" for  用户"+recommendUser +"'s recommended degree:"+itemRecommendDegree);
                if(itemRecommendDegree >= 0.3 && products.size() <=4){
                    flag = true;
                    List<Product> productByName = productService.getProductByName(item);
                    for (Product product : productByName) {//同名的产品 可能有多个
                        products.add(product);
                    }
                }
            }///if(!users.contains(recommendUser))
        }//for(String item: items)
        if(flag && products.size() <= 4){ //推荐了 但是产品不足的话
            for (int i = 0; i < 4 - products.size(); i++) {
                int index = random.nextInt(max-min + 1) + min; //目前随机前20
                Product product = productService.getProduct(index);
                products.add(product);
            }
        }
        if(!flag){ //没有推荐产品的话
            int flagInt = 0;
            while(flagInt <= 3){
                int index = random.nextInt(max-min + 1) + min; //目前随机前20
                Product product = productService.getProduct(index);
                if(product != null){
                    products.add(product);
                    flagInt ++;
                }
            }
        }
        return products;
    }  // 方法


    /**
     * 推荐分类
     * @param user
     * @param orders
     * @return
     */
    public List<Category> recommedCategories(User user,List<Order_> orders){
        Random random = new Random();
        List<Category> categories = new ArrayList<>(); //推荐的分类
        if(user == null){ //如果用户没有登录的话  不需要推荐  返回随机的3个分类即可
            int flag = 0;
            while(flag <= 2){
                int index = random.nextInt(20);
                Category category = categoryService.getCategory(index);
                if(category != null){
                    categories.add(category);
                    flag ++;
                }
            }
            return categories;
        }
        /*
          A 1 2 3 4 5
          B a 2 2 2
          C 2 1 3 4
         */

        List<List<String>> arrList = new ArrayList<>(); //临时集合
        for (Order_ order : orders) {
            boolean flag1 = false;
            User userResult = order.getUser(); //获取到对应的用户
            Integer id = userResult.getId(); //因为一个用户可能会有多个订单 用户id
            if(arrList.size() >= 1){ //添加第二列的时候 判断是否为同一个用户
                for (List<String> list : arrList) { //
                    String userId = list.get(0); //用户id
                    if(String.valueOf(id).equals(userId)){ // 如果是的话
                        /////////////////////
                        List<OrderItem> orderItems = order.getOrderItems();//获取订单对应的订单项
                        for (OrderItem orderItem : orderItems) { //遍历每一个订单项
                            Category category = orderItem.getProduct().getCategory();//获取分类
                            list.add(category.getName());
                        }
                        flag1 = true;
                        break;
                        ///////////////
                    }// if
                }//for(List<String> list : arrList)

            }//if arrList.size() >= 1
            if(!flag1){ //如果不是同一个用户
                List<String> list = new ArrayList<>();//temp
                list.add(String.valueOf(userResult.getId())); //每个子集合 第一个添加元素为 用户的id

                List<OrderItem> orderItems = order.getOrderItems();//获取订单对应的订单项
                for (OrderItem orderItem : orderItems) { //遍历每一个订单项
                    Category category = orderItem.getProduct().getCategory();//获取产品

                    list.add(category.getName());
                }
                arrList.add(list); //放到大集合中
            }

        }//for

        int N = arrList.size();
        int[][] sparseMatrix = new int[N][N];//建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        Map<String, Integer> userItemLength = new HashMap<>();//存储每一个用户对应的不同物品总数  eg: A 3
        Map<String, Set<String>> itemUserCollection = new HashMap<>();//建立物品到用户的倒排表 eg: a A B
        Set<String> items = new HashSet<>();//辅助存储物品集合
        ///userID  idUser
        Map<String, Integer> userID = new HashMap<>();//辅助存储每一个用户的用户ID映射
        Map<Integer, String> idUser = new HashMap<>();//辅助存储每一个ID对应的用户映射

        //遍历大集合
        for (int i = 0; i < arrList.size(); i++) {
            List<String> littleList = arrList.get(i);//获取集合元素
            userItemLength.put(littleList.get(0),littleList.size() -1); // 用户id 和 产品数量
            userID.put(littleList.get(0),i);  //用户id与稀疏矩阵建立对应关系
            idUser.put(i,littleList.get(0));
            for (int j = 1; j < littleList.size(); j++) {
                if(items.contains(littleList.get(j))){
                    itemUserCollection.get(littleList.get(j)).add(littleList.get(0));
                }else{
                    items.add(littleList.get(j));
                    itemUserCollection.put(littleList.get(j),new HashSet<String>());
                    itemUserCollection.get(littleList.get(j)).add(littleList.get(0));
                }
            }
        } //for

        System.out.println(itemUserCollection.toString());
        //计算相似度矩阵【稀疏】
        Set<Map.Entry<String, Set<String>>> entrySet = itemUserCollection.entrySet();
        Iterator<Map.Entry<String, Set<String>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<String> commonUsers = iterator.next().getValue();
            for (String user_u : commonUsers) {  //  遍历集合
                for (String user_v : commonUsers) {
                    if(user_u.equals(user_v)){ //遍历两次
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;//计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        //如果说用户没有买过东西的话  那么userID是获取不到值的 因为这个映射是根据订单来的
        String recommendUser = String.valueOf(user.getId()); //需要 被智能推荐的人 （id）
        if(userID.get(recommendUser) == null){ //没有买过东西的话  随机生成4个产品
            int flag = 0;
            while(flag <= 2){
                int index = random.nextInt(20);
                Category category = categoryService.getCategory(index);
                if(category != null){
                    categories.add(category);
                    flag ++;
                }
            }
            return categories;
        }

        //计算用户之间的相似度【余弦相似性】
        int recommendUserId = userID.get(recommendUser);
        for (int j = 0;j < sparseMatrix.length; j++) {
            if(j != recommendUserId){
                double result;
                double temp =sparseMatrix[recommendUserId][j] * 1.0;
                double sqrt = Math.sqrt(userItemLength.get(idUser.get(recommendUserId))*userItemLength.get(idUser.get(j))) *1.0;
                if(sqrt == 0.0){
                    result = 0.0;
                }else {
                    result = temp /sqrt;
                }
                System.out.println(idUser.get(recommendUserId)+"--"+idUser.get(j)+"相似度:"+result);
            }
        }
        boolean flag = false;
        //计算指定用户recommendUser的物品推荐度
        for(String item: items){//遍历每一件物品
            Set<String> users = itemUserCollection.get(item);//得到购买当前物品的所有用户集合
            if(!users.contains(recommendUser)){//如果被推荐用户没有购买当前物品，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for(String userTemp: users){
                    double result = 0.0;
                    double temp = sparseMatrix[userID.get(recommendUser)][userID.get(userTemp)] * 1.0;
                    double sqrt = Math.sqrt(userItemLength.get(recommendUser) * userItemLength.get(userTemp)) *1.0;
                    if(sqrt == 0.0){
                        result = 0.0;
                    }else {
                        result = temp /sqrt;
                    }
                    itemRecommendDegree += result;//推荐度计算
                }//for (!users.contains)
                System.out.println("The item "+item+" for  用户"+recommendUser +"'s recommended degree:"+itemRecommendDegree);
                if(itemRecommendDegree >= 0.3 && categories.size() <=4){
                    flag = true;
                    List<Category> categoryByName = categoryService.getCategoryByName(item);
                    for (Category category : categoryByName) {
                        categories.add(category);
                    }
                }
            }///if(!users.contains(recommendUser))
        }//for(String item: items)
        if(flag && categories.size() <= 4){ //推荐了 但是分类不足的话
            for (int i = 0; i < 4 - categories.size(); i++) {
                int index = random.nextInt(20); //目前随机前20
                Category category = categoryService.getCategory(index);
                categories.add(category);

            }
        }
        if(!flag){ //没有推荐分类的话
            int flagInt = 0;
            while(flagInt <= 3){
                int index = random.nextInt(20); //目前随机前20
                Category category = categoryService.getCategory(index);
                if(category != null){
                    categories.add(category);
                    flagInt ++;
                }
            }
        }
        return categories;
    }  // 方法
} //class
