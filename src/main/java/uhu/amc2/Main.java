/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uhu.amc2;

/**
 *
 * @author diego
 */
public class Main {

    public static void main(String[] args) {

        IProceso afd1 = Data.parsearSM("afd1.sm");
        System.out.println(afd1);
        IProceso afnd1 = Data.parsearSM("afnd1.sm");
        System.out.println(afnd1);

        System.out.println("reconoce 0:" + afd1.reconocer("0"));
        System.out.println("reconoce 1:" + afd1.reconocer("1"));
        System.out.println("reconoce 00:" + afd1.reconocer("00"));
        System.out.println("reconoce 01:" + afd1.reconocer("01"));
        System.out.println("reconoce 10:" + afd1.reconocer("10"));
        System.out.println("reconoce 11:" + afd1.reconocer("11"));
        System.out.println("reconoce 000:" + afd1.reconocer("000"));
        System.out.println("reconoce 001:" + afd1.reconocer("001"));
        System.out.println("reconoce 010:" + afd1.reconocer("010"));
        System.out.println("reconoce 011:" + afd1.reconocer("011"));
        System.out.println("reconoce 100:" + afd1.reconocer("100"));
        System.out.println("reconoce 101:" + afd1.reconocer("101"));
        System.out.println("reconoce 110:" + afd1.reconocer("110"));
        System.out.println("reconoce 10101:" + afd1.reconocer("10101"));

        System.out.println("reconoce 01:" + afnd1.reconocer("01"));

    }

}
