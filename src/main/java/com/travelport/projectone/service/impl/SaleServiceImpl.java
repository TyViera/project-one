package com.travelport.projectone.service.impl;

import com.travelport.projectone.dto.ProductRequest;
import com.travelport.projectone.dto.SaleProductResponse;
import com.travelport.projectone.dto.SaleRequest;
import com.travelport.projectone.dto.SaleResponse;
import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Product;
import com.travelport.projectone.entities.Sale;
import com.travelport.projectone.entities.SaleDetail;
import com.travelport.projectone.persistence.ClientDao;
import com.travelport.projectone.persistence.ProductDao;
import com.travelport.projectone.persistence.SaleDao;
import com.travelport.projectone.service.SaleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleDao saleDao;
    private final ProductDao productDao;
    private final ClientDao clientDao;

    public SaleServiceImpl(SaleDao saleDao, ProductDao productDao, ClientDao clientDao){
        this.productDao = productDao;
        this.saleDao = saleDao;
        this.clientDao = clientDao;
    }

    @Override
    public String incomeReport(Integer productId) {
        return "en fase de desarrollo";
    }

    @Override
    public Sale saveSale(SaleRequest saleRequest) {
        if (!saleRequest.areProductsQuantityValid()) {
            throw new IllegalArgumentException("Product quantities are not valid.");
        }

        Client client = clientDao.findById(saleRequest.getClientId()).orElseThrow(() -> new IllegalArgumentException("Client not found."));
        Sale sale = new Sale();
        sale.setClient(client);
        saleDao.saveSale(sale);

        for (ProductRequest product : saleRequest.getProducts()) {
            Product fproduct = productDao.findById(product.getId()).get();

            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setQuantity(product.getQuantity());
            saleDetail.setProduct(fproduct);
            saleDetail.setSale(sale);
            saleDao.saveSaleDetail(saleDetail);
        }
        return sale;
    }

    @Override
    public List<SaleResponse> findAll(Integer clientId) {
        List<Sale> sales = saleDao.findAll(clientId);

        List<SaleResponse> saleResponses = new ArrayList<>();

        for (Sale sale : sales) {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getId());

            List<SaleDetail> saleDetails = saleDao.findSaleDetailsBySaleId(sale.getId());
            List<SaleProductResponse> productsList = new ArrayList<>();
            for (SaleDetail saleDetail : saleDetails) {
                SaleProductResponse saleProductResponse = new SaleProductResponse();
                Product selectedProduct= saleDetail.getProduct();

                saleProductResponse.setFromProduct(selectedProduct, saleDetail.getQuantity());
                productsList.add(saleProductResponse);
            }

            saleResponse.setProducts(productsList);
            saleResponses.add(saleResponse);
        }

        return saleResponses;
    }

    @Override
    public Object getIncomeReport() {
        return null;
    }

}
