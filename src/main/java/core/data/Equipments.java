package core.data;

import core.data.entities.Equipment;
import core.manager.CoreManager;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.mapdb.serializer.SerializerJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class Equipments {

    private DB database;
    private HTreeMap<Integer, Equipment> equipments;
    private Atomic.Integer id_giver;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void init() {
        database = CoreManager.getDB();
        equipments = database
                .hashMap("equipments", Serializer.INTEGER, Serializer.JAVA)
                .createOrOpen();
        logger.info("Equipments initiating...");
        id_giver = database.atomicInteger("id_giver").createOrOpen();
    }

    public int addElement(Equipment e) {
        int id = 0;
        if (e.getId() != 0) id = e.getId();
        else {
            for (Integer integer : equipments.keySet()) {
                var equip = equipments.get(integer);
                if (equip.getAddress().equals(e.getAddress())) {
                    id = integer;
                }
            }
            if (id == 0)
                id = id_giver.addAndGet(1);
        }

        e.setAlive(true);
        e.setRegister_time(System.currentTimeMillis());
        e.setId(id);
        equipments.put(id, e);
        logger.info("New Equipment "+id+" added");
        return id;
    }

    public void removeElement(int id) {
        equipments.remove(id);
        logger.info("Equipment"+id+" has been removed");
    }

    public HTreeMap<Integer, Equipment> getMap() {
        return equipments;
    }

    public boolean containsEquipment(int equipmentID) {
        return equipments.containsKey(equipmentID);
    }

    public  Equipment getEquipment(int id) {
        return equipments.get(id);
    }

}
