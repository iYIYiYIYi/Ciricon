package core.data.entities;

import java.io.Serializable;
import java.net.InetAddress;

public class Equipment implements Serializable {

    private int id;

    private long last_connect_time;

    public long getLast_connect_time() {
        return last_connect_time;
    }

    public void setLast_connect_time(long last_connect_time) {
        this.last_connect_time = last_connect_time;
    }

    public long getRegister_time() {
        return register_time;
    }

    public void setRegister_time(long register_time) {
        this.register_time = register_time;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    private int type;
    private long register_time;
    private boolean alive;
    private InetAddress address;
    private String describe;

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
