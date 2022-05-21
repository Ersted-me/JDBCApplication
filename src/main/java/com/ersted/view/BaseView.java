package com.ersted.view;

import java.util.Scanner;

public abstract class BaseView {
    protected final Scanner scanner;
    protected final String MENU = "\n" +
            "create\t\tadd a record to list\n" +
            "delete\t\tdelete the record by ID\n" +
            "update\t\tupdate the record by ID\n" +
            "getAll\t\tshow all record\n" +
            "getById\t\tfind the record by ID\n" +
            "exit\t\texit the menu\n";

    public BaseView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.print("\nEnter the command:\t");
            String command = scanner.nextLine();

            switch (command) {
                case "create":
                    create();
                    break;
                case "delete":
                    delete();
                    break;
                case "update":
                    update();
                    break;
                case "getAll":
                    getAll();
                    break;
                case "getById":
                    getById();
                    break;
                case "exit":
                    return;
                case "help":
                    System.out.print(MENU);
                    break;
                default:
                    System.out.println("Enter 'help' for open list of command.");
            }
        }
    }

    protected Long getIdFromUser(){
        System.out.print("Enter the skill ID:\t");
        while(true){
            try{
                Long id = Long.parseLong(scanner.nextLine());
                if(id < 0)
                    throw new IllegalArgumentException("ID must be >= 0.");
                return id;
            }catch (NumberFormatException e){
                System.out.println("ID must be a number.");
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());;
            }
        }
    }

    public abstract void create();

    public abstract void delete();

    public abstract void update();

    public abstract void getById();

    public abstract void getAll();
}
