package core.data.entities;

import com.alibaba.fastjson.annotation.JSONType;
import core.data.entities.values.ValueI;

import java.io.Serializable;
import java.util.List;

@JSONType
public class Operation implements Serializable {
    private int equipmentID;
    private String name;
    private String description;
    private List<ValueI> valueIS;
    public List<ValueI> getValueIS() {
        return valueIS;
    }
    public void setValueIS(List<ValueI> valueIS) {
        this.valueIS = valueIS;
    }
    public int getEquipmentID() {
        return equipmentID;
    }
    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
