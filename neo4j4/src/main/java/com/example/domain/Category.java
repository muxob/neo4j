/**
 * Created by ilian on 2/19/2016.
 */

package com.example.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    //@JsonBackReference("parent")
    @Relationship(type="HAS", direction = "INCOMING")
    public Category parent;

    @JsonBackReference
    @Relationship(type="HAS", direction = "OUTGOING")
    public List<Category> subCategories = new ArrayList<>();

    public void has(Category person) {
        subCategories.add(person);
    }

    @Relationship(type="CONTAINS", direction = "OUTGOING")
    public List<Product> products = new ArrayList<>();

    public void contains(Product product) {
        products.add(product);
    }

    public Long getNodeId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
