package org.example;

import org.example.domain.Address;

import java.util.ArrayList;
import java.util.HashSet;

public class ValueMain {
    public static void main(String[] args) {
        int a = 10;
        int b = 10;

        System.out.println("a == b: " + (a==b));

        Address address1 = new Address("city", "street", "zipcode");
        Address address2 = new Address("city", "street", "zipcode");

        System.out.println("address1 == address2: " + (address1 == address2));
        System.out.println("address1.equals(address2): " + (address1.equals(address2)));

        ArrayList<Address> addresses1 = new ArrayList<>();
        addresses1.add(address1);
        addresses1.add(address2);
        System.out.println("addresses1.size() = " + addresses1.size());

        HashSet<Address> addresses2 = new HashSet<>();
        addresses2.add(address1);
        addresses2.add(address2);
        System.out.println("addresses2.size() = " + addresses2.size());
    }
}
