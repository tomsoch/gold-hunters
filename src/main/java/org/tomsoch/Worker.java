package org.tomsoch;

public class Worker implements Runnable {
    private final int id;
    private final Facility facility;
    private ResourceNode resourceNode;
    private final int workerCapacity;

    public Worker(int id, Facility facility, int workerCapacity) {
        this.id = id;
        this.facility = facility;
        this.workerCapacity = workerCapacity;
    }

    public void setResourceNode(ResourceNode resourceNode) {
        this.resourceNode = resourceNode;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (resourceNode == null || resourceNode.getResources() == 0) {
                    resourceNode = facility.assignResourceNode();
                    if (resourceNode == null) {
                        System.out.println("Worker " + id + " is now waiting for nodes");
                        Thread.sleep(5000);
                        continue;
                    }
                }
                executeWorkCycle();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void executeWorkCycle() throws InterruptedException {
        travelToNode();
        int collected = collectResources();
        travelToFacility();
        handlePostCollectionActions(collected);
    }

    private void travelToNode() throws InterruptedException {
        System.out.println("Worker " + id + " is going to resource node " + resourceNode.getId());
        simulateTravel(facility.getWorkerTravelTime());
    }

    private void simulateTravel(int travelTime) throws InterruptedException {
        Thread.sleep(travelTime);
    }

    private int collectResources() throws InterruptedException {
        synchronized (resourceNode) {
            if (resourceNode.getResources() == 0) return 0;

            System.out.println("Worker " + id + " is collecting from node " + resourceNode.getId());
            simulateCollection(facility.getResourceCollectingTime());
            return performCollection();
        }
    }

    private void simulateCollection(int collectionTime) throws InterruptedException {
        Thread.sleep(collectionTime);
    }

    private int performCollection() {
        int collected = Math.min(workerCapacity, resourceNode.getResources());
        collected = resourceNode.collectResources(collected);
        System.out.println("Worker " + id + " collected " + collected + " resources");
        return collected;
    }

    private void travelToFacility() throws InterruptedException {
        System.out.println("Worker " + id + " is going back to facility");
        simulateTravel(facility.getWorkerTravelTime());
    }

    private void handlePostCollectionActions(int collected) {
        storeResources(collected);
        checkNodeAndAttemptReassignment();
    }

    private void storeResources(int collected) {
        if (collected > 0) {
            facility.storeResources(collected);
        }
    }

    private void checkNodeAndAttemptReassignment() {
        if (resourceNode.getResources() == 0) {
            facility.reportEmptyNode(resourceNode.getId(), id);
            ResourceNode newNode = facility.assignResourceNode();
            resourceNode = newNode;
            if (newNode != null) {
                System.out.println("Worker " + id + " switched to node " + newNode.getId());
            } else {
                System.out.println("Worker " + id + " has ended his shift");
                resourceNode = null;
            }
        }
    }


}