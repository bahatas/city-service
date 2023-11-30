package com.example.cityService.service;


import com.example.cityService.dto.CityDto;
import com.example.cityService.dto.Group;
import com.example.cityService.exceptions.CityNotFoundException;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CityService {

    public Group getGroup(Long id) {
        Group bestGroup = Group.builder()
                .id(1L)
                .name("Best Group")
                .courseType("java -devs")
                .batch("EU-11")
                .build();
        log.info("Retruning group :{}", bestGroup.getName());

        return bestGroup;
    }

    public Group create(Group group) {
        // store db
        log.info("Created group :{}", group.getName());

        return group;
    }

    public CityDto getCityInfo(String cityName) {

        return getAllCities().stream()
                .filter(cityDto -> cityName.equals(cityDto.getName()))
                .findFirst()
                .orElseThrow(() -> new CityNotFoundException("City not available : " + cityName));
    }

    private List<CityDto> getAllCities() {
        List<CityDto> cities = new ArrayList<>();
        String filePath = "src/main/resources/cities.csv";
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
             cities = new CsvToBeanBuilder<CityDto>(reader)
                    .withType(CityDto.class)
                    .build()
                    .parse();
             log.info("Total city is {}", cities.size());
             cities.forEach(each-> System.out.println(each.getName()));
            System.out.println("cities.get(0) = " + cities.get(0));
        } catch (Exception e){
            log.info("An exception occured while reading file {}",e.getLocalizedMessage());
            return cities;
        }

        return cities;
    }
}
