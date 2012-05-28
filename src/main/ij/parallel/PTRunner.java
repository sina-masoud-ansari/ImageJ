package ij.parallel;//####[1]####
//####[1]####
import java.util.concurrent.ConcurrentLinkedQueue;//####[3]####
import paratask.runtime.TaskIDGroup;//####[4]####
//####[4]####
//-- ParaTask related imports//####[4]####
import paratask.runtime.*;//####[4]####
import java.util.concurrent.ExecutionException;//####[4]####
import java.util.concurrent.locks.*;//####[4]####
import java.lang.reflect.*;//####[4]####
import javax.swing.SwingUtilities;//####[4]####
//####[4]####
public class PTRunner {//####[6]####
    static{ParaTask.init();}//####[6]####
    /*  ParaTask helper method to access private/protected slots *///####[6]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[6]####
        if (m.getParameterTypes().length == 0)//####[6]####
            m.invoke(instance);//####[6]####
        else if ((m.getParameterTypes().length == 1))//####[6]####
            m.invoke(instance, arg);//####[6]####
        else //####[6]####
            m.invoke(instance, arg, interResult);//####[6]####
    }//####[6]####
//####[8]####
    ConcurrentLinkedQueue<Runnable> tasks;//####[8]####
//####[10]####
    public PTRunner(ConcurrentLinkedQueue<Runnable> tasks) {//####[10]####
        this.tasks = tasks;//####[11]####
    }//####[12]####
//####[14]####
    public void run() {//####[14]####
        TaskIDGroup group = processTasks();//####[15]####
        try {//####[16]####
            group.waitTillFinished();//####[17]####
        } catch (InterruptedException e) {//####[18]####
            e.printStackTrace();//####[19]####
        } catch (Exception e) {//####[20]####
            e.printStackTrace();//####[21]####
        }//####[22]####
    }//####[24]####
//####[26]####
    private Method __pt__processTasks_method = null;//####[26]####
    private Lock __pt__processTasks_lock = new ReentrantLock();//####[26]####
    private TaskIDGroup<Void> processTasks()  {//####[26]####
//####[26]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[26]####
        return processTasks(new TaskInfo());//####[26]####
    }//####[26]####
    private TaskIDGroup<Void> processTasks(TaskInfo taskinfo)  {//####[26]####
        if (__pt__processTasks_method == null) {//####[26]####
            try {//####[26]####
                __pt__processTasks_lock.lock();//####[26]####
                if (__pt__processTasks_method == null) //####[26]####
                    __pt__processTasks_method = ParaTaskHelper.getDeclaredMethod(getClass(), "__pt__processTasks", new Class[] {});//####[26]####
            } catch (Exception e) {//####[26]####
                e.printStackTrace();//####[26]####
            } finally {//####[26]####
                __pt__processTasks_lock.unlock();//####[26]####
            }//####[26]####
        }//####[26]####
//####[26]####
        taskinfo.setMethod(__pt__processTasks_method);//####[26]####
        taskinfo.setInstance(this);//####[26]####
//####[26]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[26]####
    }//####[26]####
    public void __pt__processTasks() {//####[26]####
        Runnable t = null;//####[28]####
        while ((t = tasks.poll()) != null) //####[29]####
        {//####[29]####
            t.run();//####[30]####
        }//####[31]####
    }//####[32]####
//####[32]####
}//####[32]####
