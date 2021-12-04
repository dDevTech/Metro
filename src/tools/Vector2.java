package tools;

public class Vector2 implements Cloneable{
    private float x;
    private float y;
    public Vector2(float x,float y){
        this.x = x;
        this.y = y;
    }
    public Vector2 add(Vector2 v2){
        return new Vector2(x+v2.x,y+v2.y);
    }
    public Vector2 mult(Vector2 v2){
        return new Vector2(x*v2.x,y*v2.y);
    }
    public Vector2 sub(Vector2 v2){
        return new Vector2(x-v2.x,y-v2.y);
    }
    public Vector2 div(Vector2 v2){
        return new Vector2(x/v2.x,y/v2.y);
    }
    public Vector2 mult(float k){
        return new Vector2(k*x,k*y);
    }
    public float squaredMaginute(){
        return x*x+y*y;
    }
    public float magnitude(){
        return (float)Math.sqrt(squaredMaginute());
    }
    public float distance(Vector2 v2){
        return sub(v2).magnitude();
    }
    public Vector2 normalize(){
        return new Vector2(x/magnitude(),y/magnitude());
    }
    public float getScope(){
        return y/x;
    }
    public float angle(){
        return (float)Math.atan2(y,x);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    protected Vector2 clone(){
        return new Vector2(x,y);
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public Vector2 middlePoint(Vector2 pos2){
        return new Vector2((pos2.getX()+getX())/2f,(pos2.getY()+getY())/2f);
    }
}
