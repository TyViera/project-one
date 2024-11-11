package com.travelport.projectone.service.impl;

import com.travelport.projectone.dto.ClientIpDto;
import com.travelport.projectone.dto.ProductIpDto;
import com.travelport.projectone.dto.SalesIpDto;
import com.travelport.projectone.dto.SalesPrDto;
import com.travelport.projectone.entities.SaleDetails;
import com.travelport.projectone.entities.Sales;
import com.travelport.projectone.jpa.ClientJpaRepository;
import com.travelport.projectone.jpa.ProductJpaRepository;
import com.travelport.projectone.jpa.SaleDetailJpaRepository;
import com.travelport.projectone.jpa.SaleJpaRepository;
import com.travelport.projectone.service.SalesService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class SalesServiceImpl implements SalesService {

    private final SaleDetailJpaRepository saleDetailDao;
    private final SaleJpaRepository saleDao;
    private final ClientJpaRepository clientDao;
    private final ProductJpaRepository productDao;

    public SalesServiceImpl (SaleDetailJpaRepository saleDetailDao, SaleJpaRepository saleDao, ClientJpaRepository clientDao, ProductJpaRepository productDao) {
        this.saleDetailDao = saleDetailDao;
        this.saleDao = saleDao;
        this.clientDao = clientDao;
        this.productDao = productDao;
    }

    @Override
    public Optional<SalesPrDto> save(SalesIpDto saleCl) {
        var fclient = clientDao.findById(saleCl.getCode_client());
        if (fclient.isEmpty()) {
            return null;
        }

        var sale = new Sales(fclient.get());
        var saleSaved = saleDao.save(sale);

        var newSaleID = saleSaved.getCode_sale(); // id de sale ---
        var newSaleProducts = saleCl.getProducts();

        AtomicBoolean productDrawback = new AtomicBoolean(false); //ChatGPT
        newSaleProducts.forEach(product -> {
            var productO = productDao.findById(product.getCode_product());
            if (productO.isEmpty()) {
                productDrawback.set(true);
            }

            var productF = productO.get();
            if (product.getQuantity() < 1) {
                productDrawback.set(true);
            }

            productDao.save(productF);
            var saleDetails = new SaleDetails(sale, productF, product.getQuantity());
            saleDetailDao.save(saleDetails);
        });

        if (productDrawback.get()) {
            return null;
        }

        var saleF = new SalesPrDto(newSaleID, newSaleProducts);
        return Optional.of(saleF);
    }

    @Override
    public Optional<ClientIpDto> findByClientId(Integer codeClient, String DNI) {
        var fClient = clientDao.existsById(codeClient);
        if (!fClient) {
            return null;
        }

        var sales = saleDao.findAllByClientDNI(DNI);
        var newSales = new ArrayList<SalesPrDto>();

        sales.forEach(sale ->{
            var codeSale = sale.getCode_sale();
            var saleProducts = saleDetailDao.findAllBySaleId(codeSale);

            var newSaleProducts = new ArrayList<ProductIpDto>();
            saleProducts.forEach(saleProduct -> {
                var codeProduct = saleProduct.getProduct().getId();
                var productQuantity = saleProduct.getQuantity();
                newSaleProducts.add(new ProductIpDto(codeProduct, productQuantity));
            });
            newSales.add(new SalesPrDto(codeSale, newSaleProducts));
        });
        return Optional.of(new ClientIpDto(DNI, newSales));
    }
}
