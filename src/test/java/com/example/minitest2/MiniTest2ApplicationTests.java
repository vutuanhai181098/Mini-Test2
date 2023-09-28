package com.example.minitest2;

import com.example.minitest2.entity.Bike;
import com.example.minitest2.entity.Bus;
import com.example.minitest2.repository.BikeRepository;
import com.example.minitest2.repository.BusRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MiniTest2ApplicationTests {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private BikeRepository bikeRepository;
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
}
