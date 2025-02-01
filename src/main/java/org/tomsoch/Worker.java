package org.tomsoch;

public class Worker implements Runnable {
    private int id;
    private Facility facility;
    private ResourceNode resourceNode;
    private int workerCapacity;
    private boolean isResourceEmpty;

    public Worker (int id, Facility facility, int workerCapacity){
        this.id = id;
        this.facility = facility;
        this.workerCapacity = workerCapacity;
    }

    public void setResourceNode(ResourceNode resourceNode){
        this.resourceNode = resourceNode;
    }

    @Override
    public void run() {
        try {
            while(true){
                System.out.println("Worker " + id + " is going to the resource node "+resourceNode.getId());
                Thread.sleep(facility.getWorkerTravelTime());
                System.out.println("Worker " + id + " is going to collect the resource from the resource node "+resourceNode.getId());
                Thread.sleep(facility.getResourceCollectingTime());
                int collected = resourceNode.collectResources(Math.min(workerCapacity, resourceNode.getResources()));
                if (resourceNode.getResources()==0){
                    isResourceEmpty = true;
                    System.out.println("Resource node "+resourceNode.getId()+" is empty");
                }

                System.out.println("Worker " + id + " has collected " + collected + " resources and going back to facility.");
                Thread.sleep(facility.getWorkerTravelTime());
                facility.storeResources(collected);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

    }
}
