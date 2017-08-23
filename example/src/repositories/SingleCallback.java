package repositories;
import models.Entity;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public interface SingleCallback<T extends Entity> {
    void onAvailable(T value);
    void onNotAvailable(Exception ex);
}