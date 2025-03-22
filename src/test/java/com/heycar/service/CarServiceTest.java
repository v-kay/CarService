package com.heycar.service;

import com.heycar.dao.CarDao;
import com.heycar.model.Car;
import com.heycar.util.CsvUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CarServiceTest {

    @Mock
    private CarDao carDao;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearch() {
        List<Car> expectedCars = Arrays.asList(new Car(), new Car());
        when(carDao.search()).thenReturn(expectedCars);

        List<Car> actualCars = carService.search();

        assertEquals(expectedCars, actualCars);
        verify(carDao, times(1)).search();
    }

    @Test
    public void testQuery() {
        String query = "color==black";
        List<Car> expectedCars = Arrays.asList(new Car(), new Car());
        when(carDao.query(query)).thenReturn(expectedCars);

        List<Car> actualCars = carService.query(query);

        assertEquals(expectedCars, actualCars);
        verify(carDao, times(1)).query(query);
    }

    @Test
    public void testSaveAll() {
        List<Car> carDetails = Arrays.asList(new Car(), new Car());

        carService.saveAll(carDetails);

        verify(carDao, times(1)).saveAll(carDetails);
    }

    @Test
    public void testUploadCsv() {
        String csvContent = "code,make,model,year\n" +
                "dealer123,BMW,X5,2020\n" +
                "dealer123,Audi,Q7,2021";

        byte[] content = csvContent.getBytes(StandardCharsets.UTF_8);
        String dealerId = "dealer123";

        List<Car> carDetails = Arrays.asList(new Car(), new Car());

        try (MockedStatic<CsvUtils> mockedCsvUtils = mockStatic(CsvUtils.class)) {
            mockedCsvUtils.when(() -> CsvUtils.read(eq(Car.class), any(InputStream.class)))
                    .thenReturn(carDetails);

            carService.uploadCsv(content, dealerId);

            verify(carDao, times(1)).saveAll(carDetails);
        }
    }
}