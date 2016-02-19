/**
 * Created by ilian on 2/19/2016.
 */

package com.example;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories("com.example.repository")
@EnableTransactionManagement
@ComponentScan("com.example")
public class GraphDBConfig extends Neo4jConfiguration {

    public static final String URL = System.getenv("NEO4J_URL") != null ? System.getenv("NEO4J_URL") : "http://localhost:7474";

    @Bean
    public Neo4jServer neo4jServer() {
        //return new RemoteServer(URL);
        return new RemoteServer(URL, "neo4j", "movies");
    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory("com.example.domain");
    }

    @Override
    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session getSession() throws Exception {
        return super.getSession();
    }

}
