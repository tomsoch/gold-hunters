package org.tomsoch;

class MultiExperiment extends Thread {
    public void run()
    {
        try {
            // Displaying the thread that is running
            System.out.println(
                    "Thread " + Thread.currentThread().getName()
                            + " is running");
        }
        catch (Exception e) {
            // Throwing an exception
            System.out.println("Exception is caught");
        }
    }

    public void runFewThreads(int x){
        for (int i = 0; i < x; i++) {
            MultiExperiment object = new MultiExperiment();
            object.start();
        }
    }

}