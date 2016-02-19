/**
 * Created by ilian on 2/19/2016.
 */

package com.example.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Category {

    @GraphId
    private Long id;

    private String name;

    @Relationship(type="HAS", direction = "INCOMING")
    public Category parent;

    @Relationship(type="HAS", direction = "OUTGOING")
    public Set<Category> subCategories;

    public void has(Category person) {
        if (subCategories == null) {
            subCategories = new HashSet<>();
        }
        subCategories.add(person);
    }

    @Relationship(type="CONTAINS", direction = "OUTGOING")
    public Set<Product> products;

    public void contains(Product product) {
        if (products == null) {
            products = new HashSet<>();
        }
        products.add(product);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
