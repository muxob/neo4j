/**
 * Created by ilian on 2/19/2016.
 */

package com.example;

import org.neo4j.io.fs.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class GraphApp implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        FileUtils.deleteRecursively(new File(GraphDBConfig.EMBEDDED_DATABASE_NAME));
        SpringApplication.run(GraphApp.class, args);
    }

	@Autowired
	Catalog catalog;

	@Autowired
	ProductRepository productRepository;

	public void run(String... args) throws Exception {

		Category root = new Category();
		root.setName("root");
		catalog.save(root);

		Category cat1 = new Category();
		cat1.setName("cat 1");
		catalog.save(cat1);

		root = catalog.findByName(root.getName()).get(0);
		root.has(cat1);
		catalog.save(root);

		Category cat2 = new Category();
		cat2.setName("cat 2");
		catalog.save(cat2);

		root = catalog.findByName(root.getName()).get(0);
		root.has(cat2);
		catalog.save(root);

			Product prod20 = new Product();
			prod20.setName("product 20");
			productRepository.save(prod20);

			cat2 = catalog.findByName(cat2.getName()).get(0);
			cat2.contains(prod20);
			catalog.save(cat2);

			Category cat21 = new Category();
			cat21.setName("cat 2.1.");
			catalog.save(cat21);

			cat2 = catalog.findByName(cat2.getName()).get(0);
			cat2.has(cat21);
			catalog.save(cat2);

				Product prod21 = new Product();
				prod21.setName("product 21");
				productRepository.save(prod21);

				cat21 = catalog.findByName(cat21.getName()).get(0);
				cat21.contains(prod21);
				catalog.save(cat21);

				Product prod22 = new Product();
				prod22.setName("product 22");
				productRepository.save(prod22);

				cat21 = catalog.findByName(cat21.getName()).get(0);
				cat21.contains(prod22);
				catalog.save(cat21);

				Product prod23 = new Product();
				prod23.setName("product 23");
				productRepository.save(prod23);

				cat21 = catalog.findByName(cat21.getName()).get(0);
				cat21.contains(prod23);
				catalog.save(cat21);

		Category cat3 = new Category();
		cat3.setName("cat 3");
		catalog.save(cat3);

		root = catalog.findByName(root.getName()).get(0);
		root.has(cat3);
		catalog.save(root);

		Product prod30 = new Product();
		prod30.setName("product 30");
		productRepository.save(prod30);

		cat3 = catalog.findByName(cat3.getName()).get(0);
		cat3.contains(prod30);
		catalog.save(cat3);
	}

}
