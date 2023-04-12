package org.example;

import org.example.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // persistence.xml에 있는 name='hello'를 넣어줌
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // db당 1나임
        EntityManager em = emf.createEntityManager(); // 고객의 요청이 올때마다 생성되고 사라짐(스레드간 절대 공유하면안됨)

        // jpa의 모든 변경은 transaction 안에서 돌아가야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setName("jongjin");
            em.persist(member);

            Delivery delivery1 = new Delivery();
            delivery1.setCity("경기도");
            delivery1.setStreet("백현동");
            delivery1.setZipcode("123");
            delivery1.setStatus(DeliveryStatus.Start);
            em.persist(delivery1);


            Delivery delivery2 = new Delivery();
            delivery2.setCity("서울");
            delivery2.setStreet("가락동");
            delivery2.setZipcode("456");
            delivery2.setStatus(DeliveryStatus.End);
            em.persist(delivery2);


            Order order1 = new Order();
            order1.setMember(member);
            order1.setDelivery(delivery1);
            order1.setStatus(OrderStatus.Order);
            order1.setOrderDate(LocalDateTime.now());
            em.persist(order1);

            Order order2 = new Order();
            order2.setMember(member);
            order2.setDelivery(delivery1);
            order2.setStatus(OrderStatus.Cancel);
            order2.setOrderDate(LocalDateTime.now());
            em.persist(order2);

            Order findOrder = em.find(Order.class, order1.getId());
            List<Order> orders = findOrder.getMember().getOrders();
            System.out.println("===================");
            for (Order order : orders) {
                System.out.println("order.getStatus() = " + order.getStatus());
            }


//            Member member = new Member();
//            member.setName("jongjin");
//            member.getOrders().add(order); // 여기서 getOrders는 읽기전용이라 jpa서 inser,update와 같은 걸로 활용안함
//            em.persist(member);
//
////            Order order = new Order(); // 주인
////            order.setMember(member);
////            order.setStatus(OrderStatus.Order);
////            order.setOrderDate(LocalDateTime.now());
////            em.persist(order);
////
////            member.getOrders().add(order); // 이걸 안넣어주면 최초 member persist할때 정보 기준으로 orders 리스트를 갖고 온다.(즉빈값)
////            List<Order> orders = member.getOrders(); // 주인아님(only read)
////            System.out.println(" ==============================");
////            for (Order tmp : orders) {
////                System.out.println("tmp.getId = " + tmp.getId());
////            }


//            Item item = new Item();
//            item.setName("아이스크림");
//            item.setPrice(1000);
//            item.setStockQuantity(100);
//            em.persist(item);
//
//            Integer orderPrice = 3000;
//            Integer orderItemCount = 3;
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            orderItem.setItem(item);
//            orderItem.setOrderPrice(orderPrice);
//            orderItem.setCount(orderItemCount);
//            em.persist(orderItem);
//
//            Item item1 = em.find(Item.class, item.getId());
//            item1.setStockQuantity(item1.getStockQuantity()-orderItemCount);
            tx.commit();
        } catch (Exception e) {
            System.out.println("e = " + e);
            tx.rollback();
        } finally {
            em.close(); // 항상 닫아줘야한다
        }
        emf.close(); // 항상 닫아줘야한다.
    }
}