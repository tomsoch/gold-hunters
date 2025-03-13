package org.tomsoch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesProvider {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = PropertiesProvider.class.getResourceAsStream("/app.properties")) {
            if (input == null) {
                throw new RuntimeException("app.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading application properties", e);
        }
    }

    public int getStartingWorkers() {
        return Integer.parseInt(properties.getProperty("startingWorkers"));
    }

    public int getResourceNodes() {
        return Integer.parseInt(properties.getProperty("resourceNodes"));
    }

    public int getInitialResources() {
        return Integer.parseInt(properties.getProperty("initialResources"));
    }

    public int getWorkerTrainingCost() {
        return Integer.parseInt(properties.getProperty("workerTrainingCost"));
    }

    public int getWorkerProductionTime() {
        return Integer.parseInt(properties.getProperty("workerProductionTime"));
    }

    public int getWorkerTravelTime() {
        return Integer.parseInt(properties.getProperty("workerTravelTime"));
    }

    public int getResourceCollectingTime() {
        return Integer.parseInt(properties.getProperty("resourceCollectingTime"));
    }

    public int getTargetNumberOfWorkers() {
        return Integer.parseInt(properties.getProperty("targetNumberOfWorkers"));
    }

    public int getWorkerCapacity() {
        return Integer.parseInt(properties.getProperty("workerCapacity"));
    }

    public int getRegenerationTime() {
        return Integer.parseInt(properties.getProperty("regenerationTime"));
    }

    public int getRegeneratedResources() {
        return Integer.parseInt(properties.getProperty("regeneratedResources"));
    }
}