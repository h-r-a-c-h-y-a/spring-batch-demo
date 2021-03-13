package am.polixis.task1.service;

import am.polixis.task1.dto.EmployeeDto;
import am.polixis.task1.entity.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmployeeProcessor implements ItemProcessor<EmployeeDto, Employee> {

    @Value("${dmy}")
    private String dmy;
    @Value("${mdy}")
    private String mdy;

    @Override
    public Employee process(@NonNull EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        if (employeeDto.getDate().indexOf('/') > -1) {
            employee.setDate(LocalDate.parse(employeeDto.getDate(), DateTimeFormatter.ofPattern(dmy)));
        } else {
            String correctDate = employeeDto.getDate().replaceAll("[a-z]{2},", ",");
            employee.setDate(LocalDate.parse(correctDate, DateTimeFormatter.ofPattern(mdy)));
        }
        return employee;
    }
}
