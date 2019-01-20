package com.tylersuehr.sql;
import java.io.Closeable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Stores a count of references to this object.
 *
 * This prevents a thread from actually closing the database while another thread may
 * still be using it. This will close itself after the last reference has been released.
 *
 * {@link #onAllReferencesReleased()} is called when the last reference is released.
 *
 * @author Tyler Suehr
 */
abstract class SQLiteCloseable implements Closeable {
    private final AtomicInteger refs = new AtomicInteger(0);


    @Override
    public final void close() {
        releaseReference();
    }

    /**
     * Called when the last reference to this object is released.
     * This method is to be used for actual database cleanup.
     */
    protected abstract void onAllReferencesReleased();

    /**
     * Acquires a reference to this object.
     */
    protected final void acquireReference() {
        this.refs.getAndIncrement();
    }

    /**
     * Releases a single reference from this object.
     * Determines if this object should invoke {@link #onAllReferencesReleased()}.
     */
    protected final void releaseReference() {
        final int count = refs.decrementAndGet();
        if (count < 0) {
            throw new IllegalStateException("Cannot have less than 0 references!");
        } else if (count == 0) {
            onAllReferencesReleased();
        }
    }

    /**
     * Determines if there is at least 1 reference to this object.
     * @return true if at least 1 reference exists, otherwise false
     */
    protected final boolean hasReference() {
        return refs.get() > 0;
    }
}