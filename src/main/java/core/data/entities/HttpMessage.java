package core.data.entities;

public class HttpMessage {

    String status;
    String data;

    public HttpMessage(String status, String data) {
        this.status = status;
        this.data = data;
    }

    public HttpMessage() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
