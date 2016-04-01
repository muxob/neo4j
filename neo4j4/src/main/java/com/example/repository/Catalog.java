/**
 * Created by ilian on 2/19/2016.
 */

package com.example.repository;

import com.example.domain.Category;
import com.example.domain.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "catalog", path = "catalog")
public interface Catalog extends GraphRepository<Category> {
    List<Category> findByName(@Param("name") String name);

    @Query("MATCH (r:Category {name: 'root'}) RETURN r ORDER BY r.name")
    Category root();

    @Query("start c = node({id}) MATCH (c)-[*]->(p:Product) RETURN p ORDER BY p.name")
    List<Product> recursiveCategoryProducts(@Param("id") Long id);

    @Query("start c = node({id}) MATCH (c)<-[*]-(p:Category) RETURN p")
    List<Category> parentCategories(@Param("id") Long id);

    @Override
    @Transactional
    @CacheEvict(value = "cache", key = "#p0", beforeInvocation = true)
    @Query("start c = node({id}) OPTIONAL MATCH (c)-[*]->(child) DETACH DELETE c, child")
    void delete(@Param("id") Long id);
}
