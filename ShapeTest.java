public class ShapeTest {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[2];
        shapes[0] = new Circle(5.0);
        shapes[1] = new Rectangle(4.0, 6.0);

        for (Shape s : shapes) {
            s.displayArea(); // must print correct area for each shape
        }
    }
}

// Fixed: Capitalized 'Shape' to match standard conventions and the main method
abstract class Shape {
    abstract double area();

    void displayArea() {
        System.out.println("Area: " + area());
    }
}

// Fixed: Changed 'extends shape' to 'extends Shape'
class Circle extends Shape {
    double radius;

    Circle(double r) {
        this.radius = r;
    }

    double area() {
        // Fixed: Changed 'r' to 'radius' since 'r' is out of scope here
        return 22.0 / 7.0 * radius * radius;
    }
}

// Fixed: Capitalized 'Shape'
class Rectangle extends Shape {
    double length;
    double width;

    Rectangle(double l, double w) {
        this.length = l;
        this.width = w;
    }

    double area() {
        return length * width;
    }
}