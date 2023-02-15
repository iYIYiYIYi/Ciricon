package core.data;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class DatabaseConstruction {

    public static DB constructFileDatabase(String name) {
        return DBMaker
                .fileDB(name+".db")
                .fileMmapEnableIfSupported()
                .closeOnJvmShutdown()
                .make();
    }

    public static DB constructMemoryDatabase() {
        return DBMaker
                .memoryDB()
                .make();
    }

}
