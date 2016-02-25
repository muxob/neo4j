/**
 * Created by ilian on 2/22/2016.
 */

package com.example;

import com.example.domain.Category;
import com.example.repository.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private Catalog catalog;

    @RequestMapping(value = "/rootCategory", method = RequestMethod.GET)
    public String getRootCategory() {
        Category root;
        List<Category> categoryList = catalog.findByName("root");
        if (categoryList.isEmpty()) {
            root = new Category();
            root.setName("root");
            catalog.save(root);
        } else {
            root = categoryList.get(0);

        }
        return "/catalog/" + root.getId();
    }
}
