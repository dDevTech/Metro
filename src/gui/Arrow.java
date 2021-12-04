package gui;

import graph.Connection;
import tools.Vector2;

import java.awt.*;

public class Arrow {
    private Connection con;
    private Vector2 start;
    private Vector2 end;
    private float anchorArrow;
    private float heightArrow;
    public Arrow(Connection con, Vector2 init, Vector2 end,float anchorArrow,float heightArrow) {
        this.con = con;
        this.start = init;
        this.end = end;
        this.anchorArrow = anchorArrow;
        this.heightArrow = heightArrow;
    }

    public float getAnchorArrow() {
        return anchorArrow;
    }

    public void setAnchorArrow(float anchorArrow) {
        this.anchorArrow = anchorArrow;
    }

    public float getHeightArrow() {
        return heightArrow;
    }

    public void setHeightArrow(float heightArrow) {
        this.heightArrow = heightArrow;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Vector2 getStart() {
        return start;
    }

    public void setStart(Vector2 start) {
        this.start = start;
    }

    public Vector2 getEnd() {
        return end;
    }

    public void setEnd(Vector2 end) {
        this.end = end;
    }
    /**
     * FROM STACK OVERFLOW
     */
    public void drawArrowLine(Graphics g) {
        float x1 = start.getX();
        float y1 = start.getY();
        float x2 = end.getX();
        float y2 = end.getY();
        float d = anchorArrow;
        float h = heightArrow;
        float dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {(int)x2, (int) xm, (int) xn};
        int[] ypoints = {(int)y2, (int) ym, (int) yn};

        g.drawLine((int)x1, (int)y1, (int)((xm+xn)/2f), (int)((ym+yn)/2f));
        g.fillPolygon(xpoints, ypoints, 3);
    }
}
