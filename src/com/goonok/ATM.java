package com.goonok;

import java.io.*;
import java.util.Scanner;

public class ATM {
    private  float balance;
    private int PIN = 1234;
    private int transaction;
    private File savingsFile = new File("C:\\BasicATM\\account");
    private File transactionFile = new File("C:\\BasicATM\\transaction");

    public ATM(){
        File folder = new File("C:\\BasicATM");
        try{
            if (!folder.exists()){
                folder.mkdirs();
            }

            if (!savingsFile.exists()){
                savingsFile.createNewFile();
            }

            if (!transactionFile.exists()){
                transactionFile.createNewFile();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.balance = parseBalance();
    }

    public void checkPin(){
        while (true){
            System.out.print("Enter your PIN");
            Scanner input = new Scanner(System.in);
            int checkPin = input.nextInt();
            if (checkPin==PIN){
                menu();
            }else {
                System.out.println("Enter a valid PIN!");
            }
        }
    }

    private void menu() {
        System.out.println("Enter your Choice: ");
        System.out.println("1. Check A/C Balance");
        System.out.println("2. Withdraw Balance");
        System.out.println("3. Deposit Money");
        System.out.println("4. View Transactions");
        System.out.println("5. Exit");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        switch (choice){
            case 1:
                checkBalance();
                break;
            case 2:
                withdrawMoney();
                break;
            case 3:
                depositMoney();
                break;
            case 4:
                viewTransaction();
                break;
            case 5:
                break;
            default:
                System.out.println("Enter a valid choice");
                menu();
                break;
        }
    }



    private void depositMoney() {
        System.out.println("\nEnter deposit money: ");
        Scanner input = new Scanner(System.in);
        float amount = input.nextFloat();
        balance = balance + amount;
        saveLastBalance(balance);
        transaction = 1;
        saveTransaction(amount, transaction);
        System.out.println("=========================================");
        System.out.println("Your " + amount + " deposit is success!");
        System.out.println("=========================================");
        menu();
    }

    private void withdrawMoney() {
        System.out.print("Enter amount to Withdraw: ");
        Scanner input = new Scanner(System.in);
        float amount = input.nextFloat();
        if (amount>balance){
            System.out.println("=========================================");
            System.out.println("Insufficient Balance!");
            System.out.println("=========================================");
        }else {
            balance = balance - amount;
            saveLastBalance(balance);
            transaction = -1;
            saveTransaction(amount, transaction);
            System.out.println("=========================================");
            System.out.println("Withdrawal for " + amount + " is success!");
            System.out.println("=========================================");
        }
        menu();
    }

    private void checkBalance() {
        System.out.println("=========================================");
        System.out.print("Balance : " );
        viewBalance();
        System.out.println("\n=========================================");
        menu();
    }


    private void viewTransaction() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
            String s;
            System.out.println("=========================================");
            while ((s = reader.readLine()) != null){

                System.out.println(s);

            }
            System.out.println("=========================================");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        menu();
    }


    /*
    * From here all these methods used here to save/view/update data in local computer
    * */
    private void saveLastBalance(float balance){
        String data = String.valueOf(balance);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(savingsFile));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void viewBalance(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(savingsFile));
            String  data;
            while ((data = reader.readLine()) != null){
                System.out.print(data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private float parseBalance(){
        float lastBalance = 0.0f;
        String s;
        try(BufferedReader reader = new BufferedReader(new FileReader(savingsFile))){
            while ((s = reader.readLine()) != null){
                lastBalance = Float.parseFloat(s);
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return lastBalance;
    }


    private void saveTransaction(float amount, int transaction){
        String s = "";
        String lastBalance = "Available Balance = " + parseBalance();
        if (transaction==1){
            s = "Deposit amount = " + amount + "\n";
            s = s + lastBalance;
            saveDataForTransaction(s);
        }else if (transaction==-1){
            s = "Withdraw amount = " + amount+ "\n";
            s = s + lastBalance;
            saveDataForTransaction(s);
        }
    }

    private void saveDataForTransaction(String s){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile,true));
            writer.write(s);
            writer.newLine();
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
