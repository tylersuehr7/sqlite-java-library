import mappers.UserMapper;
import repositories.DatabaseClient;
import repositories.users.LocalUserRepository;
import repositories.users.UserRepository;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public final class Injector {
    public static UserRepository provideUserRepo() {
        return UserRepository.getInstance(new LocalUserRepository(
                DatabaseClient.getInstance(), new UserMapper()));
    }
}