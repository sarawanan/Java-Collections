import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestCollectionFunctions {
    List<Employee> list = List.of(
            Employee.builder().id(1).name("Sara").salary(10000).build(),
            Employee.builder().id(2).name("Mammu").salary(5000).build(),
            Employee.builder().id(3).name("Shreeya").salary(4000).build(),
            Employee.builder().id(4).name("Sahana").salary(3000).build()
    );

    @Test
    public void testMap() {
        List<String> employeeIds = list.stream().map(Employee::getName).toList();
        assertEquals(4, employeeIds.size());
        employeeIds.forEach(System.out::println);
    }

    @Test
    public void testFilter() {
        List<Employee> employees = list.stream().filter(employee -> employee.getSalary() <= 5000).toList();
        assertEquals(3, employees.size());
        employees.forEach(Employee::display);
    }

    @Test
    public void testFindFirst() {
        Optional<Employee> optionalEmployee =
                list.stream().filter(emp -> emp.getSalary() <= 5000).findFirst();
        assertNotNull(optionalEmployee);
    }

    @Test
    public void testFindFirstOrNull() {
        Employee employee =
                list.stream().filter(emp -> emp.getSalary() > 20000).findFirst().orElse(null);
        assertNull(employee);
    }

    @Test
    public void testListToArray() {
        Employee[] empArray = list.toArray(Employee[]::new);
        assertEquals("Sara", empArray[0].getName());
    }

    @Test
    public void testFlatMap() {
        List<List<String>> nestedList =
                Arrays.asList(Arrays.asList("Sara", "Mammu"), Arrays.asList("Shreeya", "Sahana"));
        List<String> flatList = nestedList.stream().flatMap(Collection::parallelStream).toList();
        assertTrue(flatList.contains("Sara"));
        assertTrue(flatList.contains("Sahana"));
    }

    @Test
    public void testPeek() {
        List<Employee> finalList = list.stream()
                .peek(employee -> employee.setSalary(employee.getSalary() + employee.getSalary() * 0.1))
                .peek(Employee::display)
                .toList();
        assertEquals(11000, finalList.get(0).getSalary());
    }

    @Test
    public void testMethodTypesAndPipelines() {
        long count = list.stream().filter(employee -> employee.getSalary() < 5000).count();
        assertEquals(2, count);
        List<Employee> filterList = list.stream().skip(1).limit(2).toList();
        filterList.forEach(Employee::display);
        assertEquals(2, filterList.size());

        Employee minSalary = list.stream().min(Comparator.comparing(Employee::getSalary)).orElseThrow();
        assertEquals(3000, minSalary.getSalary());
        assertEquals(3000, list.stream().mapToDouble(Employee::getSalary).min().orElseThrow());

        Employee maxSalary = list.stream().max(Comparator.comparing(Employee::getSalary)).orElseThrow();
        assertEquals(10000, maxSalary.getSalary());
        assertEquals(10000, list.stream().mapToDouble(Employee::getSalary).max().orElseThrow());

        assertEquals(5500, list.stream().mapToDouble(Employee::getSalary).average().orElseThrow());

        assertTrue(list.stream().anyMatch(employee -> employee.getSalary() > 5000));
        assertFalse(list.stream().allMatch(employee -> employee.getSalary() > 5000));
        assertTrue(list.stream().noneMatch(employee -> employee.getSalary() > 20000));

        assertEquals(22000, list.stream().map(Employee::getSalary)
                .reduce(0.0, Double::sum));

        DoubleSummaryStatistics summaryStatistics =
                list.stream().mapToDouble(Employee::getSalary).summaryStatistics();
        assertEquals(5500, summaryStatistics.getAverage());
        assertEquals(22000, summaryStatistics.getSum());
        assertEquals(3000, summaryStatistics.getMin());
        assertEquals(10000, summaryStatistics.getMax());

        System.out.println("Partitioning");
        Map<Boolean, List<Employee>> partition =
                list.stream().collect(Collectors.partitioningBy(employee -> employee.getSalary() < 5000));
        partition.forEach((key, value) -> {
            System.out.println(key);
            value.forEach(Employee::display);
        });

        System.out.println("Grouping");
        Map<Boolean, List<Employee>> group =
                list.stream().collect(Collectors.groupingBy(employee -> employee.getSalary() > 5000));
        group.forEach((key, value) -> {
            System.out.println(key);
            value.forEach(Employee::display);
        });

        List<Integer> numbers = List.of(2, 5, 6, 8, 2, 9, 8);
        List<Integer> distinctList = numbers.stream().distinct().toList();
        assertEquals(5, distinctList.size());

        Set<Integer> distinctSet = new HashSet<>(numbers);
        assertEquals(5, distinctSet.size());

        IntStream range = IntStream.range(10, 20);
        assertEquals(10, range.count());

        assertEquals("Sara, Mammu",
                list.stream().limit(2)
                        .map(Employee::getName)
                        .collect(Collectors.joining(", ")));

        Stream.generate(Math::random).limit(5).forEach(System.out::println);

        Stream.iterate(1, i -> i + 1).takeWhile(i -> i < 10).map(x -> x * x).forEach(System.out::println);
    }

    @Test
    public void testSortedList() {
        List<Employee> sortedList = list.stream().sorted(Comparator.comparing(Employee::getName)).toList();
        sortedList.forEach(Employee::display);
        assertEquals("Mammu", sortedList.get(0).getName());
    }
}
