package ua.vatva.containercontroliotsensor.domain;

public class UltrasonicData {
    private String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "UltrasonicData{" +
                "distance='" + distance + '\'' +
                '}';
    }
}
