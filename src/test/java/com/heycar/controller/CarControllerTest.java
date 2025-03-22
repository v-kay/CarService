package com.heycar.controller;

import com.heycar.model.Car;
import com.heycar.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearch() throws Exception {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carService.search()).thenReturn(cars);

        mockMvc.perform(get("/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(cars.size()));

        verify(carService, times(1)).search();
    }

    @Test
    public void testQuery() throws Exception {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carService.query(anyString())).thenReturn(cars);

        mockMvc.perform(get("/query").param("q", "color==black"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(cars.size()));

        verify(carService, times(1)).query(anyString());
    }

    @Test
    public void testSave() throws Exception {
        List<Car> cars = Arrays.asList(new Car(), new Car());

        mockMvc.perform(post("/vehicle_listings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"make\":\"BMW\",\"model\":\"X5\",\"year\":2020},{\"make\":\"Audi\",\"model\":\"Q7\",\"year\":2021}]"))
                .andExpect(status().isOk());

        verify(carService, times(1)).saveAll(anyList());
    }

    @Test
    public void testUploadCsv() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "cars.csv", "text/csv", "make,model,year\nBMW,X5,2020\nAudi,Q7,2021".getBytes());

        mockMvc.perform(multipart("/upload_csv/123")
                .file(file)
                .contentType("text/csv")
                .accept("text/csv"))
                .andExpect(status().isOk());

        verify(carService, times(1)).uploadCsv(any(byte[].class), eq("123"));
    }
}