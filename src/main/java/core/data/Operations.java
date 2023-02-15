package core.data;

import core.data.entities.Operation;
import core.manager.CoreManager;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Operations {

    private DB database;
    private HTreeMap<Integer, ArrayList<Operation>> operations;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void init() {
        database = CoreManager.getDB();
        operations = (HTreeMap<Integer, ArrayList<Operation>>) database
                .hashMap("operations")
                .createOrOpen();
        logger.info("Operations initiating...");
    }

    public void addOperation(Operation o) {
        int equip = o.getEquipmentID();
        if (operations.containsKey(equip)) {
            operations.get(equip).add(o);
        } else {
            operations.put(equip, new ArrayList<>(List.of(o)));
        }
    }

    public void addOperations(List<Operation> operations) {
        for (Operation operation : operations) {
            addOperation(operation);
        }
    }

    public boolean hasOperation(Operation o) {
        if (o == null) return false;
        ArrayList<Operation> operationArrayList = operations.get(o.getEquipmentID());
        if (operationArrayList == null) return false;
        for (Operation operation : operationArrayList) {
            if (operation.getName().equals(o.getName())) {
                return true;
            }
        }
        return false;
    }

    public HTreeMap<Integer, ArrayList<Operation>> getMap() {
        return operations;
    }

    public ArrayList<Operation> getOperations(int id) {
        return operations.get(id);
    }

}
