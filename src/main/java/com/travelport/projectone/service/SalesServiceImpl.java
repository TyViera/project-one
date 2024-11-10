package com.travelport.projectone.service;

import com.travelport.projectone.entities.Sales;
import com.travelport.projectone.persistence.SalesDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalesServiceImpl implements SalesService {
    private final SalesDao salesDao;

    public SalesServiceImpl(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    @Override
    public Sales save(Sales product) {
        salesDao.save(product);
        return product;
    }

    @Override
    public Sales update(Integer id, Sales sales) {
        var fsales = salesDao.getSalesById(id);
        if (fsales.isEmpty()) {
            return null;
        }
        if (sales.getSalesId() == null) {
            return null;
        }
        salesDao.update(sales);
        return sales;
    }

    @Override
    public Optional<Sales> getSalesById(Integer id) {
        return salesDao.getSalesById(id);
    }

    @Override
    public void deleteById(Integer id) {

        salesDao.deleteById(id);
    }
}
