package org.tomsoch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Facility {
    private int resources;
    private int workerTrainingCost;
    private int workerProductionTime;
    private int workerTravelTime;
    private int resourceCollectingTime;
    private int workerCapacity;
    private ExecutorService workerPool;

    public Facility(int initialResources, int workerTrainingCost, int workerProductionTime,
                    int workerTravelTime, int resourceCollectingTime, int workerCapacity) {
        this.resources = initialResources;
        this.workerTrainingCost = workerTrainingCost;
        this.workerProductionTime = workerProductionTime;
        this.workerTravelTime = workerTravelTime;
        this.resourceCollectingTime = resourceCollectingTime;
        this.workerCapacity = workerCapacity;
        this.workerPool = Executors.newCachedThreadPool();
    }
    public void storeResources(int resources) {
        this.resources += resources;
    }
    public int getWorkerTravelTime() {
        return workerTravelTime;
    }
    public int getResourceCollectingTime() {
        return resourceCollectingTime;
    }
    public void trainWorker(int id, ResourceNode[] nodes) throws InterruptedException {
        if (resources >= workerTrainingCost) {
            System.out.println("Now we have "+ resources + " resources and we are going to train new worker for " + workerTrainingCost);
            resources = resources - workerTrainingCost;
            System.out.println("Facility after training has now " + resources + " resources");
            Thread.sleep(workerProductionTime);
            Worker worker = new Worker(id, this, workerCapacity);
            worker.setResourceNode(nodes[id % nodes.length]);
            System.out.println("New worker was trained: " + id);
            workerPool.execute(worker);
        }
    }
}
