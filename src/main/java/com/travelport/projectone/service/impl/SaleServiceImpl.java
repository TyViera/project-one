package com.travelport.projectone.service.impl;

import com.travelport.projectone.dto.ClientSalesListDto;
import com.travelport.projectone.dto.ProductDto;
import com.travelport.projectone.dto.PurchaseDto;
import com.travelport.projectone.dto.SaleDto;
import com.travelport.projectone.entity.Sale;
import com.travelport.projectone.entity.SaleProduct;
import com.travelport.projectone.jpa.ClientJpaRepository;
import com.travelport.projectone.jpa.ProductJpaRepository;
import com.travelport.projectone.jpa.SaleJpaRepository;
import com.travelport.projectone.jpa.SaleProductJpaRepository;
import com.travelport.projectone.service.SaleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleJpaRepository saleDao;
    private final SaleProductJpaRepository saleProductDao;
    private final ClientJpaRepository clientDao;
    private final ProductJpaRepository productDao;

    public SaleServiceImpl(SaleJpaRepository saleDao, SaleProductJpaRepository saleProductDao, ClientJpaRepository clientDao, ProductJpaRepository productDao) {
        this.saleDao = saleDao;
        this.saleProductDao = saleProductDao;
        this.clientDao = clientDao;
        this.productDao = productDao;
    }

    @Override
    public Optional<SaleDto> save(PurchaseDto req) {
        var client = clientDao.findById(req.getClientNif());
        if (client.isEmpty()) return Optional.empty();

        var newSale = new Sale(client.get());
        var savedSale = saleDao.save(newSale);

        var newSaleId = savedSale.getId();
        var newSaleProductList = req.getProductList();

        newSaleProductList.forEach(inputProduct -> {
            var productOptional = productDao.findById(inputProduct.getProductId());
            if (productOptional.isEmpty()) return;

            var product = productOptional.get();

            var productTimesSold = product.getTimesSold();
            product.setTimesSold(productTimesSold + inputProduct.getAmount());
            productDao.save(product);

            var newSaleProduct = new SaleProduct(newSale, product, inputProduct.getAmount());
            saleProductDao.save(newSaleProduct);
        });

        var sale = new SaleDto(newSaleId, newSaleProductList);
        return Optional.of(sale);
    }

    @Override
    public Optional<ClientSalesListDto> findSalesByClientId(String clientNif) {
        var isClientExists = clientDao.existsById(clientNif);
        if (!isClientExists) return Optional.empty();

        var dbSalesList = saleDao.findAllByClientNif(clientNif);
        var salesList = new ArrayList<SaleDto>();

        dbSalesList.forEach(sale -> {
            var saleId = sale.getId();
            var dbSaleProductsList = saleProductDao.findAllBySaleId(saleId);
            var saleProductsList = new ArrayList<ProductDto>();

            dbSaleProductsList.forEach(saleProduct -> {
                var productId = saleProduct.getProduct().getId();
                var productAmount = saleProduct.getAmount();
                saleProductsList.add(new ProductDto(productId, productAmount));
            });

            salesList.add(new SaleDto(saleId, saleProductsList));
        });

        return Optional.of(new ClientSalesListDto(clientNif, salesList));
    }
}
