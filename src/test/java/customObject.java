import java.io.Serializable;

public class customObject implements Serializable {
    int a;
    int b;
    customObject(int a, int b) {
        this.a = a;
        this.b = b;
    }
}