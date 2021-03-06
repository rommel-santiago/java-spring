package com.sebnsab.demo.bootstrap;

import com.sebnsab.demo.model.relationship.DetailBiDirectional;
import com.sebnsab.demo.model.relationship.DetailUniDirectional;
import com.sebnsab.demo.model.relationship.Product;
import com.sebnsab.demo.model.relationship.Transaction;
import com.sebnsab.demo.others.AsyncDemo;
import com.sebnsab.demo.others.TransactionalDemo;
import com.sebnsab.demo.repository.DetailBiDirectionalRepository;
import com.sebnsab.demo.repository.DetailUniDirectionalRepository;
import com.sebnsab.demo.repository.ProductRepository;
import com.sebnsab.demo.repository.TransactionRepository;
import com.sebnsab.demo.service.ServiceDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DetailBiDirectionalRepository detailBiDirectionalRepository;

    @Autowired
    private DetailUniDirectionalRepository detailUniDirectionalRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ApplicationContext context;

    @Autowired
    private ServiceDemo serviceDemo;

    @Autowired
    private AsyncDemo asyncDemo;

    @Autowired
    private TransactionalDemo transactionalDemo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initializeData();
        transactionalCalls();
        asyncCalls();
        //fillUpLogs();

    }

    public void initializeData() {

        Product product1 = new Product("Product1");
        Product product2 = new Product("Product2");

        productRepository.save(product1);
        productRepository.save(product2);


        Transaction tran1 = new Transaction();

        tran1.setDescription("First Transaction");
        tran1.setDateCreated(java.sql.Date.valueOf(LocalDate.now()));
        tran1.setDateModified(tran1.getDateCreated());
        transactionRepository.save(tran1);

        DetailBiDirectional dbd1 = new DetailBiDirectional(tran1);
        DetailBiDirectional dbd2 = new DetailBiDirectional(tran1);

        dbd1.setProduct(product1);
        dbd2.setProduct(product2);

        detailBiDirectionalRepository.save(dbd1);
        detailBiDirectionalRepository.save(dbd2);

        detailUniDirectionalRepository.save(new DetailUniDirectional(tran1.getId()));
        detailUniDirectionalRepository.save(new DetailUniDirectional(tran1.getId()));

        Transaction retrieved = transactionRepository.getById(1L);

        System.out.println(retrieved);

        //Get hold of Spring's Application Context
        context.getBeanDefinitionNames();

        log.info("Data Pre Loaded. Successful");

        log.debug("Show debug");

    }



    private void transactionalCalls() {
        try {
            transactionalDemo.addProducts();
        }
        catch (Exception e ){
            System.out.println(e.getMessage());
        }

        List<Product> products = productRepository.findAll();

    }

    public void fillUpLogs() {
        for (int i = 0; i < 200000 ; i++) {
            log.info("Loading Chooba Fill Me Up a lot");

        }
    }

    private void asyncCalls() {
        for (int i = 0; i <  5; i++) {
            asyncDemo.asyncCallDemo();
        }

    }

}
