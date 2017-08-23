package repositories;
import java.util.List;
import models.Entity;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public interface ListCallback<T extends Entity> {
    void onAvailable(List<T> values);
    void onNotAvailable(Exception ex);
}