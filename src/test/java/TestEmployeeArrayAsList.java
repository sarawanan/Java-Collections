import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestEmployeeArrayAsList {
    List<Employee> employeeList = Arrays.asList(
            Employee.builder().id(1).name("Sara").salary(10000).build(),
            Employee.builder().id(2).name("Mammu").salary(5000).build()
    );

    @Test
    public void testDisplay() {
        assertEquals(2, employeeList.size());
        employeeList.forEach(Employee::display);
    }

    @Test
    public void testMutability() {
        employeeList.set(1, Employee.builder().id(3).name("Sahana").salary(100000).build());
        assertEquals(2, employeeList.size());
        employeeList.forEach(Employee::display);
        assertFalse(employeeList.contains(null));
    }

    @Test
    public void testNull() {
        employeeList.set(1, null);
        assertEquals(2, employeeList.size());
        employeeList.forEach(employee -> {
            if (employee != null) employee.display();
        });
    }

    @Test
    public void testArrayToListModification() {
        Employee[] empArray = {
                Employee.builder().id(1).name("Sara").salary(1000).build(),
                Employee.builder().id(2).name("Mammu").salary(2000).build()
        };
        List<Employee> list = Arrays.asList(empArray);
        list.forEach(Employee::display);
        empArray[1] = Employee.builder().id(3).name("Sahana").salary(100000).build();
        list.forEach(Employee::display);
        assertEquals("Sahana", list.get(1).getName());
    }
}
