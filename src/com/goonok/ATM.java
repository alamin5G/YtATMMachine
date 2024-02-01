package com.goonok;

import java.util.Scanner;

public class ATM {
    private  double balance;
    private int PIN = 1234;

    public void checkPin(){
        System.out.print("Enter your PIN");
        Scanner input = new Scanner(System.in);
        int checkPin = input.nextInt();
        if (checkPin==PIN){
            menu();
        }
    }

    private void menu() {
    }

}
