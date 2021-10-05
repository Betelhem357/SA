package com.CS590.sample.batchProcessor;

import com.CS590.sample.model.Student;
import com.CS590.sample.repository.StudentRepository;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.CrudRepository;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    // JOB creation
    public JobBuilderFactory jobBuilderFactory;
    // Step create
    public StepBuilderFactory stepBuilderFactory;
    // Our repository
    private StudentRepository studentRepository;

    // constructor injection
    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory,
                              StepBuilderFactory stepBuilderFactory,
                              StudentRepository studentRepository
                              ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.studentRepository = studentRepository;
    }

    @Bean
    public StudentBatchProcessor processor() {
        return new StudentBatchProcessor();
    }

    @Bean
    public FlatFileItemReader<Student> reader() {
        return new FlatFileItemReaderBuilder<Student>()
                .name("studentItemReader")
                .resource(new ClassPathResource("student_data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName","gpa","age"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {{
                    setTargetType(Student.class);
                }})
                .build();
    }

    @Bean
    public RepositoryItemWriter<Student> writer() {
        return new RepositoryItemWriterBuilder<Student>()
                .repository(this.studentRepository)
                .build();
    }

    // creates JOB
    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                //.listener(listener)
                .flow(step1)
                //.next(step2)
                .end()
                .build();
    }

    // creates Step for a job
    @Bean
    public Step step1(RepositoryItemWriter<Student> writer) {
        return stepBuilderFactory.get("step1")
                .<Student, Student> chunk(10)// data size to be processed at a time
                .reader(reader()) //read from CSV
                .processor(processor()) //conversion
                .writer(writer) //write to DB
                .build();
    }




}
