package si.fri.rso.mongo;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Iterator;

@ApplicationScoped
public class MongoQuery {

    @Inject
    MongoConnection mongoConnection;

    public void getAllMessages() {

        MongoDatabase database = mongoConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("messages");
        System.out.println("Collection sampleCollection selected successfully");
        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int i = 1;

        // Getting the iterator
        Iterator it = iterDoc.iterator();

        while (it.hasNext()) {
            Document doc = (Document) it.next();
            System.out.println(doc.get("message"));
            System.out.println(doc.get("userName"));
            System.out.println(doc.get("channelId"));
            System.out.println(doc.get("_id"));
            System.out.println(doc);

            i++;
        }
    }

}
