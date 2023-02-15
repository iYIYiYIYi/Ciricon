package core.data;

import org.mapdb.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataCenter {

    private static Equipments equipments;
    private static Operations operations;
    private static DB equips;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DataCenter() {
        equips = DatabaseConstruction.constructFileDatabase("equipment");
        equipments = new Equipments();
        operations = new Operations();
    }

    public void init() {
        logger.info("DataCenter initiating...");
        equipments.init();
        operations.init();
    }

    public Equipments getEquipments() {
        return equipments;
    }

    public Operations getOperations() {
        return operations;
    }

    public DB getDB() {
        return equips;
    }

}
