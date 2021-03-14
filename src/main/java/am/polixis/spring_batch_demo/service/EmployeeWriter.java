package am.polixis.spring_batch_demo.service;

import am.polixis.spring_batch_demo.entity.Employee;
import am.polixis.spring_batch_demo.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class EmployeeWriter implements ItemWriter<Employee> {

    private final EmployeeRepository repository;
    private short counter;

    public EmployeeWriter(EmployeeRepository repository) {
        this.repository = repository;
    }

    //There are two ways to store data, first in json file, second in db
    @Override
    public void write(@NonNull List<? extends Employee> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees" + (++counter) + ".json"))) {
            objectMapper.writeValue(writer, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        repository.saveAll(list);
    }
}
