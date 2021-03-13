package am.polixis.task1.config;

import am.polixis.task1.dto.EmployeeDto;
import am.polixis.task1.entity.Employee;
import am.polixis.task1.service.ArchiveResourceItemReader;
import am.polixis.task1.service.EmployeeProcessor;
import am.polixis.task1.service.EmployeeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.Comparator;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final EmployeeWriter writer;
    private final EmployeeProcessor processor;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job readCSVFilesJob() {
        return jobBuilderFactory
                .get("readCSVFilesFromZipAndStoreToDB")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("final step")
                .<EmployeeDto, Employee>chunk(100)
                .reader(multiResourceItemReader())
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public MultiResourceItemReader<EmployeeDto> multiResourceItemReader() {
        MultiResourceItemReader<EmployeeDto> resourceItemReader = new ArchiveResourceItemReader<>();
        resourceItemReader.setDelegate(reader());
        resourceItemReader.setComparator(Comparator.comparing(Resource::getDescription));
        return resourceItemReader;
    }

    @Bean
    public FlatFileItemReader<EmployeeDto> reader() {
        FlatFileItemReader<EmployeeDto> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<>() {{
                setLineTokenizer(new DelimitedLineTokenizer() {{
                        setNames("firstName", "lastName", "date");
                    }});
                setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                        setTargetType(EmployeeDto.class);
                    }});
            }});
        return reader;
    }
}
