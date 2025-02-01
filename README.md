Java Multithreading Practice Project

The purpose of this application is to simulate parts of a traditional Real Time Strategy Game.
In this simulation, workers collect some resources (gold, minerals etc.) and store them in some facility.
Each resource node can be exploited by a single worker at a time and it takes some time 
to collect some resources (this is some static number) from the resource node.
However, because of the additional time it takes for a worker to get to the resource node 
and come back with collected resources (simulated by some waiting time), 
it makes sense to assign multiple workers to the same resource node.
New workers can be trained (built) in the facility that stores the resources, 
but it costs some resources to do so.
Training new workers takes some time and only one worker can be trained at a time.

The application should stop after a specific number of workers is reached.
The number of resource nodes and starting workers can be parameterized.
Example values:
- startingWorkers = 4
- resourceNodes = 4
- amountCollected = 8 amount of how much the worker can hold at the same time
- workerTrainingCost = 50
- workerProductionTime = 500 ms
- workerTravelTime = 100 ms
- resourceCollectingTime = 200 ms
- targetNumberOfWorkers = 12