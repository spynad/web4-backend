package app.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double x;
    private double y;
    private double r;
    private boolean hitResult;

    public Point() {}

    public Point(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        hitResult = intersectPoint();
    }

    public long getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isHitResult() {
        return hitResult;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setHitResult(boolean hitResult) {
        this.hitResult = hitResult;
    }

    private boolean intersectPoint() {
        return ((x <= 0 && y <= 0) && (y >= -r/2 && x >= -r)  ||
                ((x*x + y*y) <= (r*r)) && x >= 0 && y >= 0) ||
                ((y >= -r + x) && (x <= r) && (y >= -r) && (x >= 0) && (y <= 0));
    }
}
