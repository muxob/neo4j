/**
 * Created by ilian on 2/19/2016.
 */

package com.example;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

@Configuration
@EnableNeo4jRepositories
public class GraphDBConfig extends Neo4jConfiguration {

    public static final String EMBEDDED_DATABASE_NAME = "catalog.db";

    public GraphDBConfig() {
        setBasePackage("com.example");
    }

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase(EMBEDDED_DATABASE_NAME);
    }

}
