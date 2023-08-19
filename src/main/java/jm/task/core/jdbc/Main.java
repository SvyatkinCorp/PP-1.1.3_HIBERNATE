package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
//         реализуйте алгоритм здесь
        userService.createUsersTable();
        userService.saveUser("Сергей", "Батькович", (byte) 25);
        userService.saveUser("Алексей", "Биушкин", (byte) 45);
        userService.saveUser("Андрей", "Гаврилюк", (byte) 23);
        userService.saveUser("Иван", "Иванов", (byte) 67);
        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
