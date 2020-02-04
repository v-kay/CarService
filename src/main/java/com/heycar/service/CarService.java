package com.heycar.service;

import com.heycar.dao.CarDao;
import com.heycar.model.Car;
import com.heycar.util.CsvUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    public CarDao carDao;


    /**
     * Fetch all records from the database.
     *
     * @return {@code List<Car>}
     */
    public List<Car> search() {
        return carDao.search();
    }


    /**
     * Query car details bases on certain parameters.
     *
     * @param query The filter criteria eg. color==black
     * @return {@code List<Car>}
     */
    public List<Car> query(final String query) {
        return carDao.query(query);
    }


    /**
     * Store car details into database.
     *
     * @param carDetails list containing detail of cars.
     */
    public void saveAll(final List<Car> carDetails) {
        carDao.saveAll(carDetails);
    }


    /**
     * Store car details into database.
     *
     * @param content  csv file content containing details
     * @param dealerId id of the dealer uploading file.
     */
    public void uploadCsv(final byte[] content, String dealerId) {
        InputStream stream = new ByteArrayInputStream(content);
        List<Car> carDetails = CsvUtils.read(Car.class, stream);
        carDao.saveAll(carDetails);
    }
}

