package org.tomsoch;

import java.util.*;
import java.util.concurrent.*;

public class Facility {
    private final int workerTrainingCost;
    private final int workerProductionTime;
    private final int workerTravelTime;
    private final int resourceCollectingTime;
    private final int workerCapacity;
    private final ExecutorService workerPool;
    private final ResourceNode[] resourceNodes;
    private final Set<Integer> emptyNodes;
    private final Random random;
    private int resources;
    private final int regenerationTime;
    private final int regeneratedResources;
    private final ScheduledExecutorService regenerationScheduler;

    public Facility(int initialResources, int workerTrainingCost, int workerProductionTime,
                    int workerTravelTime, int resourceCollectingTime, int workerCapacity,
                    ResourceNode[] nodes, int regenerationTime, int regeneratedResources) {
        this.resources = initialResources;
        this.workerTrainingCost = workerTrainingCost;
        this.workerProductionTime = workerProductionTime;
        this.workerTravelTime = workerTravelTime;
        this.resourceCollectingTime = resourceCollectingTime;
        this.workerCapacity = workerCapacity;
        this.workerPool = Executors.newVirtualThreadPerTaskExecutor();
        this.resourceNodes = nodes;
        this.emptyNodes = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.random = new Random();
        this.regenerationTime = regenerationTime;
        this.regeneratedResources = regeneratedResources;
        this.regenerationScheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void reportEmptyNode(int nodeId, int workerId) {
        emptyNodes.add(nodeId);
        System.out.println("Facility registered that node " + nodeId + " is empty, info from the worker " + workerId);

        regenerationScheduler.schedule(() -> {
            ResourceNode node = resourceNodes[nodeId];
            synchronized(node) {
                node.resetResources(regeneratedResources);
            }
            emptyNodes.remove(nodeId);
            System.out.println("Node " + nodeId + " regenerated with " + regeneratedResources + " resources");
        }, regenerationTime, TimeUnit.MILLISECONDS);
    }

    public ResourceNode assignResourceNode() {
        List<ResourceNode> availableNodes = Arrays.stream(resourceNodes)
                .filter(node -> !emptyNodes.contains(node.getId()))
                .toList();

        return availableNodes.isEmpty() ?
                null :
                availableNodes.get(random.nextInt(availableNodes.size()));
    }

    public void startOperation(int startingWorkers, int targetWorkers) throws InterruptedException {
        for (int i = 0; i < startingWorkers; i++) {
            createAndStartWorker(i);
        }

        int workerId = startingWorkers;
        while (workerId < targetWorkers) {
            trainWorker(workerId++);
        }
    }

    private void createAndStartWorker(int id) {
        ResourceNode assignedNode = assignResourceNode();
        if (assignedNode != null) {
            Worker worker = new Worker(id, this, workerCapacity);
            worker.setResourceNode(assignedNode);
            workerPool.execute(worker);
            System.out.println("Worker " + id + " assigned to node " + assignedNode.getId());
        }
    }

    public void trainWorker(int id) throws InterruptedException {
        if (resources >= workerTrainingCost) {
            System.out.println("Training new worker " + id + ". Resources before: " + resources);
            resources -= workerTrainingCost;
            System.out.println("Resources after training: " + resources);
            Thread.sleep(workerProductionTime);
            createAndStartWorker(id);
        }
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
}