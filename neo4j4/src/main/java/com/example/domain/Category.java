/**
 * Created by ilian on 2/19/2016.
 */

package com.example.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Category {

    @GraphId
    private Long id;

    private String name;

    @Relationship(type="HAS", direction = "INCOMING")
    public Category parent;

    @Relationship(type="HAS", direction = "OUTGOING")
    public List<Category> subCategories;

    public void has(Category person) {
        if (subCategories == null) {
            subCategories = new ArrayList<>();
        }
        subCategories.add(person);
    }

    @Relationship(type="CONTAINS", direction = "OUTGOING")
    public List<Product> products;

    public void contains(Product product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
