/**
 * Created by ilian on 2/22/2016.
 */

package com.example;

import com.example.domain.Category;
import com.example.repository.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private Catalog catalog;

    @Autowired
    private EntityLinks entityLinks;

    private final static String ROOT_NAME = "root";

    @RequestMapping(value = "/rootCategory", method = RequestMethod.GET)
    public ResourceSupport getRootCategory() {
        Category root;
        List<Category> categoryList = catalog.findByName(ROOT_NAME);
        if (categoryList.isEmpty()) {
            root = new Category();
            root.setName(ROOT_NAME);
            catalog.save(root);
        } else {
            root = categoryList.get(0);

        }

        ResourceSupport categoryResource = new ResourceSupport() {
            public String name = root.getName();
        };

        //categoryResource.add(entityLinks.linkForSingleResource(Category.class, root.getId()).withRel(Link.REL_SELF));
        //categoryResource.add(entityLinks.linkForSingleResource(Category.class, root.getId()).withRel("category"));
        categoryResource.add(entityLinks.linkForSingleResource(Category.class, root.getId()).slash("subCategories").withRel("subCategories"));
        //categoryResource.add(entityLinks.linkForSingleResource(Category.class, root.getId()).slash("parent").withRel("parent"));
        //categoryResource.add(entityLinks.linkForSingleResource(Category.class, root.getId()).slash("products").withRel("products"));

        return categoryResource;
    }
}
