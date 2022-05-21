package com.ersted.view;

import com.ersted.controller.SkillController;
import com.ersted.exception.AlreadyExistException;
import com.ersted.exception.NotFoundException;
import com.ersted.model.Skill;

import java.util.List;
import java.util.Scanner;

public class SkillView extends BaseView {
    private final SkillController controller;

    public SkillView(SkillController controller, Scanner scanner) {
        super(scanner);
        this.controller = controller;
    }

    @Override
    public void create() {
        System.out.print("Enter skill: ");
        String skillName = scanner.nextLine();

        Long skillId = controller.create(new Skill(skillName)).getId();

        System.out.printf("The skill was successfully created. ID: %d.%n", skillId);
    }

    @Override
    public void delete() {
        Long id = getIdFromUser();
        controller.deleteById(id);

        System.out.println("The skill was successfully deleted.");
    }

    @Override
    public void update() {
        Long id = getIdFromUser();
        System.out.println("Enter the new name of skill.");
        String newName = scanner.nextLine();

        try {
            controller.update(new Skill(id, newName));
            System.out.println("The skill was successfully updated.");
        } catch (AlreadyExistException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getById() {
        Long id = getIdFromUser();
        try {
            Skill skill = controller.getById(id);
            System.out.printf("ID:\t %d\t\tName:\t %s%n",skill.getId(),skill.getSkill());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getAll() {
        List<Skill> skills = controller.getAll();
        if (skills == null) {
            System.out.println("List is empty.");
            return;
        }

        for (Skill skill : skills) {
            System.out.printf("ID:\t%d\t\tName:\t%s%n",skill.getId(), skill.getSkill());
        }
    }


}
