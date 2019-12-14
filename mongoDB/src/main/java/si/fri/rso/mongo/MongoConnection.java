package si.fri.rso.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class MongoConnection {
    private MongoDatabase database;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object o){
        System.out.println("MONGO CONNECTION");

        MongoClientURI uri = new MongoClientURI("mongodb://user:123456a@ds255728.mlab.com:55728/rso1920-messages?retryWrites=false");
        MongoClient mongo = new MongoClient( uri );

        System.out.println("Connected to the database successfully");
        this.database = mongo.getDatabase("rso1920-messages");
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }
}
