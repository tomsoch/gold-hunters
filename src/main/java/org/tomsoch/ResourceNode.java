package org.tomsoch;

import java.util.concurrent.locks.ReentrantLock;

public class ResourceNode {
    private final int id;
    private int resources;
    private final ReentrantLock lock = new ReentrantLock();


    public ResourceNode(int id, int initialResources) {
        this.id = id;
        this.resources = initialResources;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public int getId() {
        return id;
    }

    public int getResources() {
        return resources;
    }

    public int collectResources(int collected) {
        resources = resources - collected;
        System.out.println("The node " + id + " has " + resources + " resources");
        return collected;
    }

    public void resetResources(int amount) {
        lock.lock();
        try {
            resources = amount;
        } finally {
            lock.unlock();
        }
    }
}
