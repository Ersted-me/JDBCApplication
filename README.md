# Консольное приложение
## Описание
Необходимо реализовать консольное CRUD приложение, которое   
взаимодействует с БД и позволяет выполнять все CRUD операции над сущностями:  
Developer (id, firstName, lastName, List<Skill> skills, Specialty specialty)  
Skill  
Specialty  
Status (enum ACTIVE, DELETED)  

## Требования:
Придерживаться шаблона MVC (пакеты model, repository, service, controller, view).  
Для миграции БД использовать https://www.liquibase.org/.   
Сервисный слой приложения должен быть покрыт юнит тестами (junit + mockito).  
Для импорта библиотек использовать Maven.  

## Технологии 
Java, MySQL, JDBC, Maven, Liquibase, JUnit, Mockito

## Локальный запуск
1. Скачать архив с проектом.
2. Распаковать в удобную папку.
3. Запустить класс Main.
