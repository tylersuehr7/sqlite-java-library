package models;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public abstract class Entity {
    private Object id;


    public Entity() {}

    @Override
    public boolean equals(Object obj) {
        return (this == obj || (obj instanceof Entity && ((Entity) obj).id.equals(id)));
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }
}