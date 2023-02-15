package core.scheduler;

import core.data.entities.Equipment;
import core.manager.CoreManager;
import core.message.implement.PacketMessage;
import core.message.implement.PacketTypeDefinitions;
import org.mapdb.HTreeMap;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;

public class HeartbeatScheduler implements Job {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        HTreeMap<Integer, Equipment> map = CoreManager.getDataCenter().getEquipments().getMap();
        Instant now = Instant.now();
        map.forEach((id, equipment) -> {
            if (now.minusMillis(equipment.getLast_connect_time()).getEpochSecond() > 120) {
                logger.warn("id:"+id+" has been offline over 120secs, it could be dead");
                equipment.setAlive(false);
            }
            PacketMessage message = new PacketMessage(id, PacketTypeDefinitions.HEARTBEAT_PACKET, new byte[]{});
            try {
                CoreManager.getUdpServer().send(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        CoreManager.getDB().commit();
    }
}
