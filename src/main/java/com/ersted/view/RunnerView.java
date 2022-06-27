package com.ersted.view;

import com.ersted.controller.DeveloperController;
import com.ersted.controller.SkillController;
import com.ersted.controller.SpecialtyController;
import com.ersted.controller.impl.DeveloperControllerImpl;
import com.ersted.controller.impl.SkillControllerImpl;
import com.ersted.controller.impl.SpecialtyControllerImpl;
import com.ersted.repository.DeveloperRepository;
import com.ersted.repository.SkillRepository;
import com.ersted.repository.SpecialtyRepository;
import com.ersted.repository.impl.DeveloperRepositoryImpl;
import com.ersted.repository.impl.SkillRepositoryImpl;
import com.ersted.repository.impl.SpecialtyRepositoryImpl;
import com.ersted.service.DeveloperService;
import com.ersted.service.SkillService;
import com.ersted.service.SpecialtyService;
import com.ersted.service.impl.DeveloperServiceImpl;
import com.ersted.service.impl.SkillServiceImpl;
import com.ersted.service.impl.SpecialtyServiceImpl;

import java.util.Scanner;

public class RunnerView {
    private DeveloperView developerView;
    private SkillView skillView;
    private SpecialtyView specialtyView;

    private Scanner scanner = new Scanner(System.in);

    public RunnerView() {
        DeveloperRepository developerRepository = new DeveloperRepositoryImpl();
        SkillRepository skillRepository = new SkillRepositoryImpl();
        SpecialtyRepository specialtyRepository = new SpecialtyRepositoryImpl();

        DeveloperService developerService = new DeveloperServiceImpl(developerRepository);
        SkillService skillService = new SkillServiceImpl(skillRepository);
        SpecialtyService specialtyService = new SpecialtyServiceImpl(specialtyRepository);


        DeveloperController developerController = new DeveloperControllerImpl(developerService);
        SkillController skillController = new SkillControllerImpl(skillService);
        SpecialtyController specialtyController = new SpecialtyControllerImpl(specialtyService);

        developerView = new DeveloperView(
                developerController,
                specialtyController,
                skillController,
                scanner);

        skillView = new SkillView(
                skillController,
                scanner);

        specialtyView = new SpecialtyView(
                specialtyController,
                scanner);
    }

    public void showMenu() {
        boolean isExit = false;

        while (!isExit) {
            System.out.print("\nВведите категорию (help - справочник): ");
            String command = scanner.nextLine();

            switch (command) {
                case "skill":
                    skillView.showMenu();
                    break;
                case "specialty":
                    specialtyView.showMenu();
                    break;
                case "developer":
                    developerView.showMenu();
                    break;
                case "exit":
                    isExit = true;
                    break;
                case "help":
                    System.out.print(
                        "\nskill\t\tменю создания навыков\n" +
                                "specialty\t\tменю создания специальностей\n" +
                                "developer\t\tменю создания разработчиков\n" +
                                "exit\t\tвыйти\n");
            }
        }
    }
}
