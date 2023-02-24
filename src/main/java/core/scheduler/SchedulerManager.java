package core.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

public class SchedulerManager {
    private SchedulerFactory factory;
    private Scheduler scheduler;
    private Trigger trigger_500ms;

    public void init() throws SchedulerException {
        Properties prop = new Properties();
        // 线程池配置
        prop.put("org.quartz.threadPool.threadCount", "3");
        factory = new StdSchedulerFactory(prop);
        scheduler = factory.getScheduler();

        trigger_500ms = TriggerBuilder.newTrigger().withIdentity("trigger_500", "triggerGroup")
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMilliseconds(500)//每隔500ms执行一次
                        .repeatForever()).build();//一直执行

        JobDetail heartbeat_job = JobBuilder.newJob(HeartbeatScheduler.class).withIdentity("heartbeat_job").build();
        scheduler.scheduleJob(heartbeat_job, trigger_500ms);
        scheduler.start();
    }

    public void addJob_500ms(Class<Job> jobClass) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity("embed_job").build();
        scheduler.scheduleJob(jobDetail, trigger_500ms);
    }

}
