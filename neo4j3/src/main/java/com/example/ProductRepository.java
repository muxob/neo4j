/**
 * Created by ilian on 2/19/2016.
 */

package com.example;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends GraphRepository<Product> {
}
