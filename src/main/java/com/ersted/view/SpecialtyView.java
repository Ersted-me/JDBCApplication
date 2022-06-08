package com.ersted.view;

import com.ersted.controller.SpecialtyController;
import com.ersted.exception.AlreadyExistException;
import com.ersted.exception.NotFoundException;
import com.ersted.model.Specialty;

import java.util.List;
import java.util.Scanner;

public class SpecialtyView extends BaseView{
    private final SpecialtyController controller;

    public SpecialtyView(SpecialtyController controller, Scanner scanner) {
        super(scanner);
        this.controller = controller;
    }

    @Override
    public void create() {
        System.out.print("Enter specialty: ");
        String specialtyName = scanner.nextLine();

        Long specialtyId = controller.create(new Specialty(specialtyName)).getId();

        System.out.printf("The specialty was successfully created. ID: %d.%n", specialtyId);
    }

    @Override
    public void delete() {
        Long id = getIdFromUser();
        controller.deleteById(id);

        System.out.println("The specialty was successfully deleted.");
    }

    @Override
    public void update() {
        Long id = getIdFromUser();
        System.out.println("Enter the new name of specialty.");
        String newName = scanner.nextLine();

        try {
            controller.update(new Specialty(id, newName));
            System.out.println("The specialty was successfully updated.");
        } catch (AlreadyExistException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getById() {
        Long id = getIdFromUser();
        try {
            Specialty specialty = controller.getById(id);
            System.out.printf("ID:\t %d\t\tName:\t %s%n",specialty.getId(),specialty.getSpecialty());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getAll() {
        List<Specialty> specialties = controller.getAll();
        if (specialties == null) {
            System.out.println("List is empty.");
            return;
        }

        for (Specialty specialty : specialties) {
            System.out.printf("ID:\t%d\t\tName:\t%s%n",specialty.getId(), specialty.getSpecialty());
        }
    }

}
