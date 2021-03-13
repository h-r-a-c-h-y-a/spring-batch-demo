package am.polixis.task1.service;

import am.polixis.task1.entity.Employee;
import am.polixis.task1.repository.EmployeeRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeWriter implements ItemWriter<Employee> {

    private final EmployeeRepository repository;

    public EmployeeWriter(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void write(@NonNull List<? extends Employee> list) {
        repository.saveAll(list);
    }
}
