import java.util.List;
import java.util.UUID;
import models.User;
import repositories.ListCallback;
import repositories.users.IUserRepository;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public final class Main {
    public static void main(String[] args) throws Exception {
        addUserToDatabase("David", "Clarke");
        addUserToDatabase("Boomhower", "Smith");
        addUserToDatabase("Sandy", "Higgins");
        addUserToDatabase("Wanda", "Dempsey");
        loadAllUsers();
    }

    private static void addUserToDatabase(String first, String last) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName(first);
        user.setLastName(last);
        user.setUsername(first.toLowerCase() + last.toLowerCase());

        IUserRepository userRepo = Injector.provideUserRepo();
        userRepo.saveUser(user);
    }

    private static void loadAllUsers() {
        IUserRepository userRepo = Injector.provideUserRepo();
        userRepo.findAllUsers(new ListCallback<User>() {
            @Override
            public void onAvailable(List<User> values) {
                for (User user : values) {
                    System.out.println(user.toString());
                }
            }

            @Override
            public void onNotAvailable(Exception ex) {
                System.err.println("Couldn't load all users!");
                ex.printStackTrace();
            }
        });
    }
}