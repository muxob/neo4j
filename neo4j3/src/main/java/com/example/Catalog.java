/**
 * Created by ilian on 2/19/2016.
 */

package com.example;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "catalog", path = "catalog")
public interface Catalog extends GraphRepository<Category> {
    List<Category> findByName(@Param("name") String name);
}
