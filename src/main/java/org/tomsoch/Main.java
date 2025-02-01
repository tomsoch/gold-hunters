package org.tomsoch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting the gold rush!");

        int startingWorkers = 4;
        int resourceNodes = 4;
        int initialResources = 200;
        int workerTrainingCost = 50;
        int workerProductionTime = 500;
        int workerTravelTime = 100;
        int resourceCollectingTime = 200;
        int targetNumberOfWorkers = 12;
        int workerCapacity = 8;

        ResourceNode[] nodes = new ResourceNode[resourceNodes];
        for (int i = 0; i < resourceNodes; i++) {
            nodes[i] = new ResourceNode(i, 100);
        }

        Facility facility = new Facility(initialResources, workerTrainingCost, workerProductionTime,
                workerTravelTime, resourceCollectingTime, workerCapacity, nodes);

        facility.startOperation(startingWorkers, targetNumberOfWorkers);
    }
}