import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
    private int id;
    private String name;
    private double salary;

    public void display() {
        System.out.printf("ID: %s - Name: %s - Salary: %s%n", id, name, salary);
    }
}
