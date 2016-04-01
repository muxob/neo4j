/**
 * Created by ilian on 2/22/2016.
 */

package com.example;

import com.example.domain.Category;
import com.example.domain.Product;
import com.example.repository.Catalog;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private Catalog catalog;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityLinks entityLinks;

    private final static String ROOT_NAME = "root";

    @RequestMapping(value = "/rootCategory", method = RequestMethod.GET)
    public Resource<Category> getRootCategory() {
        Category root;
        List<Category> categoryList = catalog.findByName(ROOT_NAME);
        if (categoryList.isEmpty()) {
            root = new Category();
            root.setName(ROOT_NAME);
            catalog.save(root);
        } else {
            root = categoryList.get(0);

        }

        return toHateoas(root);
    }

    @RequestMapping(value = "/catalog", method = RequestMethod.POST)
    @Transactional
    public Resource<Category> createSubcategory(@RequestBody Category request) {
        Category newCategory = new Category();
        newCategory.setName(request.getName());
        catalog.save(newCategory);

        Category parent = catalog.findByName(request.parent.getName()).get(0);
        parent.has(newCategory);
        catalog.save(parent);

        for (Product p : request.products) {
            Product newProduct = new Product();
            newProduct.setName(p.getName());
            productRepository.save(newProduct);

            newCategory = catalog.findByName(newCategory.getName()).get(0);
            newCategory.contains(newProduct);
            catalog.save(newCategory);
        }

        return toHateoas(newCategory);
    }

    private Resource<Category> toHateoas(Category category) {
        Resource<Category> categoryResource = new Resource<Category>(category);

        categoryResource.add(entityLinks.linkForSingleResource(Category.class, category.getNodeId()).withRel(Link.REL_SELF));
        categoryResource.add(entityLinks.linkForSingleResource(Category.class, category.getNodeId()).withRel("category"));
        categoryResource.add(entityLinks.linkForSingleResource(Category.class, category.getNodeId()).slash("subCategories").withRel("subCategories"));
        categoryResource.add(entityLinks.linkForSingleResource(Category.class, category.getNodeId()).slash("parent").withRel("parent"));
        categoryResource.add(entityLinks.linkForSingleResource(Category.class, category.getNodeId()).slash("products").withRel("products"));

        return categoryResource;
    }
}
