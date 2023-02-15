package core.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerManager {

    public void init() throws SchedulerException {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        Trigger trigger_500ms = TriggerBuilder.newTrigger().withIdentity("trigger", "triggerGroup")
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMilliseconds(500)//每隔500ms执行一次
                        .repeatForever()).build();//一直执行

        JobDetail heartbeat_job = JobBuilder.newJob(HeartbeatScheduler.class).withIdentity("heartbeat_job").build();
        scheduler.scheduleJob(heartbeat_job, trigger_500ms);
        scheduler.start();
    }

}
