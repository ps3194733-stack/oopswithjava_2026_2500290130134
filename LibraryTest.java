public class LibraryTest {
    public static void main(String[] args) {
        Book b1 = new Book("Java Basics", "J. Author", 350.0, "ISBN001");
        Book b2 = new Book("OOP Concepts", "K. Writer", 420.0, "ISBN002");
 
        System.out.println(b1.getTitle() + " by " + b1.getAuthor());
        System.out.println("Price: " + b2.getPrice());
        // Fixed: Matches the exact casing defined in the Book class
        System.out.println("Library: " + Book.libraryName);
        System.out.println("Total books added: " + Book.bookCount);
    }
}

class Book {
    private String Title;
    private String author;
    private double price;
    private String isbn;
    
    // Fixed: Changed 'libraryname' to 'libraryName' to match the main method
    final static String libraryName = "Sunrise";
    static int bookCount = 0;
    
    Book(String t, String a, double p, String i) {
        this.Title = t;
        this.author = a;
        this.price = p;
        this.isbn = i;
        ++bookCount;
    }
    
    public String getTitle() {
        return Title;
    }
    
    // Fixed: Changed return type from String to void
    public void setTitle(String t) {
        this.Title = t;
    }
    
    public String getAuthor() {
        return author;
    }
    
    // Fixed: Changed return type from String to void
    public void setAuthor(String a) {
        this.author = a;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double p) {
        this.price = p;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String i) {
        this.isbn = i;
    }
}