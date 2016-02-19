/**
 * Created by ilian on 2/19/2016.
 */

package com.example;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Category {

    @GraphId
    private Long id;

    private String name;

    @RelatedTo(type="HAS", direction= Direction.INCOMING)
    public @Fetch
    Category parent;

    @RelatedTo(type="HAS", direction= Direction.OUTGOING)
    public @Fetch Set<Category> subCategories;

    public void has(Category person) {
        if (subCategories == null) {
            subCategories = new HashSet<>();
        }
        subCategories.add(person);
    }

    @RelatedTo(type="CONTAINS", direction= Direction.OUTGOING)
    public @Fetch Set<Product> products;

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
