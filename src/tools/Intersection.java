package tools;

public class Intersection {
    public static Vector2 lineWithCircle(Vector2 init,float radius,Vector2 posCircle){
        double angle = posCircle.sub(init).angle()+Math.PI;
        float x = posCircle.getX()+(float)Math.cos(angle)*radius;
        float y = posCircle.getY()+(float)Math.sin(angle)*radius;

        return new Vector2(x,y);
    }
}
