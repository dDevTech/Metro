package graph;

import tools.Vector2;

public class Connection{
    private float value;
    private Vertex from;
    private Vertex to;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    private boolean used;
    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    private boolean transfer = false;
    public Connection(Vertex from,Vertex to,float value){
        this.value = from.getPosition().distance(to.getPosition())+value;
        this.from = from;
        this.to = to;
        updateConnection(this);

    }
    private void updateConnection(Connection connection){
        to.addToInConnection(connection);
        from.addToOutConnection(connection);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Vertex getFrom() {
        return from;
    }
    public void setFrom(Vertex from) {
        this.from.getOutConnections().remove(this);
        from.getOutConnections().add(this);
        this.from = from;

    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to.getInConnections().remove(this);
        to.getInConnections().add(this);
        this.to = to;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "value=" + value +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
