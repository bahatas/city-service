package com.example.cityService.controller;

import com.example.cityService.dto.CityDto;
import com.example.cityService.dto.Group;
import com.example.cityService.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/city")
public class GroupController {

    private final CityService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Group> get(@PathVariable("id") Long id) {
        log.info("Get group request received {}", id);
        Group group = service.getGroup(id);
        return ResponseEntity.ok().body(group);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Group> create(@RequestBody Group group) {
        log.info("Create group request received {}", group);
        Group group1 = service.create(group);
        return ResponseEntity.ok(group1);
    }


    @PostMapping(path = "/info")
    public ResponseEntity<CityDto> create(@RequestBody String cityName) throws IOException {
        log.info("City info req is received {}", cityName);
        CityDto cityInfo = service.getCityInfo(cityName);
        log.info("City info response is  {}", cityInfo);
        return ResponseEntity.ok(cityInfo);
    }

    @GetMapping(path = "/weather/{city}")
    public ResponseEntity<String> getWeather(@PathVariable("city") String city) throws IOException {
        String url = "http://api.weatherstack.com/current?access_key=d903456d087e464711504e2564799e48&query=" + city;

        String result = "error";
        HttpResponse response = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);

            response = client.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity);
               log.info("response body : {}", result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String body = response.getEntity().getContent().toString();
        System.out.println("body = " + body);
        return ResponseEntity.ok().body(body);
        // 200 201 404
        // 403: Access Denied Forbidden Auth
        // 401: Unauth
        // 503:Service unavailable
        // 415: unsupported format of mediatype - (json )
        // 405 : Method Not allowed -> post/get
    }
}
