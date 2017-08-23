package com.tylersuehr.sql;
import java.io.Closeable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 *
 * This allows us to acquire and release references, stored as an integer value.
 *
 * We use these references for concurrency reasons, to prevent a Thread from closing this object
 * while any other Threads may be using it.
 *
 * {@link #onAllReferencesReleased()} is called when there are no more references to this object
 * and should be used to clean-up or recycle stuff.
 */
abstract class SQLiteCloseable implements Closeable {
    final AtomicInteger refs = new AtomicInteger(0);


    @Override
    public void close() {
        releaseReference();
    }

    /**
     * Called when the last reference to this object is released.
     */
    protected abstract void onAllReferencesReleased();

    /**
     * Acquires a reference by incrementing {@link #refs}.
     */
    protected void acquireReference() {
        this.refs.getAndIncrement();
    }

    /**
     * Releases a reference by decrementing {@link #refs}. Also checks references and
     * will call {@link #onAllReferencesReleased()} when there are no more references.
     */
    protected void releaseReference() {
        int count = refs.decrementAndGet();
        if (count < 0) {
            throw new IllegalStateException("Cannot have less than 0 references!");
        } else if (count == 0) {
            onAllReferencesReleased();
        }
    }

    /**
     * Checks if this object has any references.
     * @return True if this object has references
     */
    protected boolean hasReference() {
        return refs.get() > 0;
    }
}