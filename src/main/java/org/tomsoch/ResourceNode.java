package org.tomsoch;

public class ResourceNode {
    private final int id;
    private int resources;

    public ResourceNode(int id, int initialResources) {
        this.id = id;
        this.resources = initialResources;
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
}
