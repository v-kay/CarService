package com.heycar.controller;

import com.heycar.model.Car;
import com.heycar.service.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "cardetail", description = "Operations pertaining to searching cars")
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    /**
     * Fetch all records from the database.
     *
     * @return {@code List<Car>}
     */
    @ApiOperation(value = "Search car details.", response = List.class)
    @GetMapping("/search")
    public List<Car> search() {
        return carService.search();
    }


    /**
     * Query car details bases on certain parameters.
     *
     * @param query The filter criteria eg. color==black
     * @return {@code List<Car>}
     */
    @ApiOperation(value = "Query car details.", response = List.class)
    @GetMapping("/query")
    public List<Car> query(@RequestParam("q") String query) {
        return carService.query(query);
    }


    /**
     * Store car details into database.
     *
     * @param carDetails list containing detail of cars.
     */
    @ApiOperation(value = "Save car details.")
    @PostMapping(value = "/vehicle_listings")
    public void save(@RequestBody List<Car> carDetails) {
        carService.saveAll(carDetails);
    }

    /**
     * Store car details into database.
     *
     * @param file     csv file containing details
     * @param dealerId id of the dealer uploading file.
     * @throws IOException
     */
    @ApiOperation(value = "Save car details using csv file.")
    @PostMapping(value = "/upload_csv/{dealer_id}", consumes = "text/csv")
    public void uploadCsv(@RequestBody @RequestParam("file") MultipartFile file, @PathVariable("dealer_id") String dealerId) throws IOException {
        carService.uploadCsv(file.getBytes(), dealerId);
    }
}
