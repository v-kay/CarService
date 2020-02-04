package com.heycar.dao;

import com.heycar.model.Car;
import com.heycar.repositories.CarRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarDao {

    @Autowired
    public CarRepository carRepository;

    @Autowired
    public EntityManager entityManager;


    /**
     * Provide all records from database.
     *
     * @return {@code List<Car>}
     */
    public List<Car> search() {
        List<Car> carDetails = new ArrayList<>();
        carRepository.findAll().forEach(carDetails::add);
        return carDetails;
    }

    /**
     * Persist list of records in database.
     *
     * @param carDetails list containing details of car
     */
    public void saveAll(List<Car> carDetails) {
        carRepository.saveAll(carDetails);
    }


    /**
     * Query database using a filter criteria.
     *
     * @param query filter criteria eg. color==black
     * @return list containing details of car
     */
    public List<Car> query(final String query) {
        final String[] criteria = query.split("==");
        final String finalQuery = new StringBuilder().append("SELECT cd FROM Car cd where cd.").
                    append(criteria[0]).append("=").append(":").append(criteria[0]).toString();
        final Query q = entityManager.createQuery(finalQuery).setParameter(criteria[0], criteria[1]);
        return q.getResultList();
    }
}
