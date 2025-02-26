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
            while (resourceNode != null) {
                System.out.println("Worker " + id + " is going to resource node " + resourceNode.getId());
                Thread.sleep(facility.getWorkerTravelTime());

                int collected = 0;
                boolean needNewAssignment = false;

                synchronized (resourceNode) {
                    if (resourceNode.getResources() > 0) {
                        System.out.println("Worker " + id + " is collecting from node " + resourceNode.getId());
                        Thread.sleep(facility.getResourceCollectingTime());
                        collected = resourceNode.collectResources(Math.min(workerCapacity, resourceNode.getResources()));
                        System.out.println("Worker " + id + " collected " + collected + " resources");
                    }
                }

                System.out.println("Worker " + id + " is going back to facility");
                Thread.sleep(facility.getWorkerTravelTime());

                if (resourceNode.getResources() == 0) {
                    facility.reportEmptyNode(resourceNode.getId(), id);
                    needNewAssignment = true;
                }

                if (collected > 0) {
                    facility.storeResources(collected);
                }

                if (needNewAssignment) {
                    resourceNode = facility.assignResourceNode();
                    if (resourceNode == null) {
                        System.out.println("Worker " + id + " is stopping - no more resources available");
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }



}