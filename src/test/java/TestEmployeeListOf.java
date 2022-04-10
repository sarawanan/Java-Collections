import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestEmployeeListOf {
    List<Employee> employeeList = List.of(
            Employee.builder().id(1).name("Sara").salary(10000).build(),
            Employee.builder().id(2).name("Mammu").salary(5000).build()
    );

    @Test
    public void testDisplay() {
        assertEquals(2, employeeList.size());
        employeeList.forEach(Employee::display);
        assertThrows(NullPointerException.class, () -> employeeList.contains(null));
    }

    @Test
    public void testMutability() {
        assertThrows(UnsupportedOperationException.class, () ->
                employeeList.set(1, Employee.builder().id(3).name("Sahana").salary(100000).build()));
    }

    @Test
    public void testNull() {
        assertThrows(UnsupportedOperationException.class, () ->
                employeeList.set(1, null));
        assertThrows(NullPointerException.class, () -> {
            List<Employee> employees = List.of(null);
        });
    }

    @Test
    public void testArrayToListModification() {
        Employee[] empArray = {
                Employee.builder().id(1).name("Sara").salary(1000).build(),
                Employee.builder().id(2).name("Mammu").salary(2000).build()
        };
        List<Employee> list = List.of(empArray);
        list.forEach(Employee::display);
        empArray[1] = Employee.builder().id(3).name("Sahana").salary(100000).build();
        list.forEach(Employee::display);
        assertEquals("Mammu", list.get(1).getName());
    }
}
