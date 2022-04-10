import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEmployeeArray {
    @Test
    public void testEmployeeArray() {
        Employee[] empArray = {
                Employee.builder().id(1).name("Sara").salary(1000).build(),
                Employee.builder().id(2).name("Mammu").salary(2000).build()
        };

        Stream.of(empArray).forEach(Employee::display);
        assertEquals(2, empArray.length);
    }
}
