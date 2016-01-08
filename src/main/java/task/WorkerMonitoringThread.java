package task;

import com.tnova.cplb.data.TempData;

public class WorkerMonitoringThread implements Runnable{

    private String threadName = null;

    public WorkerMonitoringThread(String threadName){
        this.threadName = threadName;
    }

    public void run(){
        TempData.LOGGER.info("WorkerMonitoringThread "+threadName+" execution...");
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        TempData.LOGGER.info("WorkerMonitoringThread "+threadName+" result: XYZ");
    }

}
