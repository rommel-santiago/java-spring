package com.sebnsab.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebnsab.demo.model.relationship.Product;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


public class UtilitiesTest {

    @Test
    public void jsonToObjectTest() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Product product =  objectMapper.readValue(new File("./target/classes/json/product.json"), Product.class);


        System.out.println(product);

    }

}
