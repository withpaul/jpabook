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
            Period period1 = new Period(LocalDateTime.now(), LocalDateTime.now());
            Member member1 = new Member();
            member1.setName("member1");
            member1.setPeriod(period1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("member2");
            member2.setPeriod(period1);
            em.persist(member2);

            Period period2 = new Period(period1.getStartDateTime().withYear(2000), period1.getEndDateTime());
            member2.setPeriod(period2);
            tx.commit();
        } catch (Exception e) {
            System.out.println("e = " + e);
            tx.rollback();
        } finally {
            em.close(); // 항상 닫아줘야한다
        }
    }
}