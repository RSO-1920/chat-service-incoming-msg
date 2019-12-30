package si.fri.rso.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import si.fri.rso.config.AppConfigProperties;


import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class MongoConnection {

    @Inject
    private AppConfigProperties appConfigProperties;

    private MongoDatabase database;

    @PostConstruct
    public void init(){
        System.out.println("MONGO CONNECTION");

        MongoClientURI uri = new MongoClientURI(appConfigProperties.getMongoUrlConnection());
        MongoClient mongo = new MongoClient( uri );

        System.out.println("Connected to the database successfully");
        this.database = mongo.getDatabase(appConfigProperties.getMongoDatabase());
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }
}
