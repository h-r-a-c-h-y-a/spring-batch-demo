package am.polixis.spring_batch_demo.service;

import am.polixis.spring_batch_demo.dto.EmployeeDto;
import am.polixis.spring_batch_demo.entity.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmployeeProcessor implements ItemProcessor<EmployeeDto, Employee> {

    private static final String DATE_ORDINAL_SUFFIX = "(?<=\\d)(st|nd|rd|th)";
    private static final String DATE_ORDINAL_SUFFIX_MATCHER = "(.*th.*|.*st.*|.*rd.*|.*nd.*)";

    private final String dmyFormat;
    private final String mdyFormat;

    public EmployeeProcessor(@Value("${dmy}") String dmy,
                             @Value("${mdy}") String mdy) {
        this.dmyFormat = dmy;
        this.mdyFormat = mdy;
    }

    @Override
    public Employee process(@NonNull EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setDate(parseDate(employeeDto.getDate()));
        return employee;
    }

    private LocalDate parseDate(String date) {
        if (date.matches(DATE_ORDINAL_SUFFIX_MATCHER)) {
            String correctDate = date.replaceAll(DATE_ORDINAL_SUFFIX, "");
            return LocalDate.parse(correctDate, DateTimeFormatter.ofPattern(mdyFormat));
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(dmyFormat));
    }
}
