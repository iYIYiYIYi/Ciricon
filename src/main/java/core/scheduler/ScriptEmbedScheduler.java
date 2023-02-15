package core.scheduler;

import core.manager.CoreManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.script.ScriptEngine;

/***
 *  TODO 暂时无法实现脚本拓展定时任务
 */
public class ScriptEmbedScheduler implements Job {

    private final ScriptEngine engine = CoreManager.getScriptEngineManager().getEngineByName("javascript");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    }
}
