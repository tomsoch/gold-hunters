package org.tomsoch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting the gold rush!");

        PropertiesProvider properties = new PropertiesProvider();

        int startingWorkers = properties.getStartingWorkers();
        int resourceNodes = properties.getResourceNodes();
        int initialResources = properties.getInitialResources();
        int workerTrainingCost = properties.getWorkerTrainingCost();
        int workerProductionTime = properties.getWorkerProductionTime();
        int workerTravelTime = properties.getWorkerTravelTime();
        int resourceCollectingTime = properties.getResourceCollectingTime();
        int targetNumberOfWorkers = properties.getTargetNumberOfWorkers();
        int workerCapacity = properties.getWorkerCapacity();
        int regenerationTime = properties.getRegenerationTime();
        int regeneratedResources = properties.getRegeneratedResources();

        ResourceNode[] nodes = new ResourceNode[resourceNodes];
        for (int i = 0; i < resourceNodes; i++) {
            nodes[i] = new ResourceNode(i, 100);
        }

        Facility facility = new Facility(initialResources, workerTrainingCost, workerProductionTime,
                workerTravelTime, resourceCollectingTime, workerCapacity, nodes,
                regenerationTime, regeneratedResources);

        facility.startOperation(startingWorkers, targetNumberOfWorkers);
    }
}