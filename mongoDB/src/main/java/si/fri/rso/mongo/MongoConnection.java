package si.fri.rso.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class MongoConnection {
    private MongoDatabase database;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object o){
        // Creating a Mongo client

        // mongodb://<dbuser>:<dbpassword>@ds255728.mlab.com:55728/rso1920-messages
        System.out.println("MONGO CONNECTION");

        MongoClientURI uri = new MongoClientURI("mongodb://user:123456a@ds255728.mlab.com:55728/rso1920-messages");
        MongoClient mongo = new MongoClient( uri );

        System.out.println("Connected to the database successfully");
        // Accessing the database
        this.database = mongo.getDatabase("rso1920-messages");
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }
}
