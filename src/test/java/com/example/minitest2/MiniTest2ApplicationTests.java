package com.example.minitest2;

import com.example.minitest2.entity.Bike;
import com.example.minitest2.entity.Bus;
import com.example.minitest2.entity.Employee;
import com.example.minitest2.repository.BikeRepository;
import com.example.minitest2.repository.BusRepository;
import com.example.minitest2.repository.EmployeeRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@SpringBootTest
class MiniTest2ApplicationTests {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    void save() {
        for (int i = 0; i < 5; i++) {
            Bus bus = new Bus(null, "color" + (i+1));
            Bike bike = new Bike(null, "color" + (i+1));
            bikeRepository.save(bike);
            busRepository.save(bus);
        }
    }
    @Test
    void find_all_Bike(){
        List<Bike> bikes = bikeRepository.getAllBike();
        bikes.forEach(System.out::println);
    }

    @Test
    void save_employee(){
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            Employee employee = new Employee(
                    null,
                    faker.internet().emailAddress(),
                    faker.name().firstName(),
                    faker.name().lastName()
            );
            employeeRepository.save(employee);
        }
    }

    @Test
    void test_find_employee(){
        // Named query
       Employee employee = employeeRepository.getEmployeeById(20L);
      System.out.println(employee);
    }
}
