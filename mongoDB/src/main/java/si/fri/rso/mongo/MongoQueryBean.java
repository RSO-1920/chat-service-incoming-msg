package si.fri.rso.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import si.fri.rso.lib.MongoMessageObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class MongoQueryBean {
    @Inject
    MongoConnection mongoConnection;

    public List<MongoMessageObject> getAllMessages() {

        MongoDatabase database = mongoConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("messages");
        System.out.println("Collection <messages> selected successfully");

        FindIterable<Document> iterDoc = collection.find();
        List<MongoMessageObject> mongoMessageObjectList = new ArrayList<MongoMessageObject>();

        Iterator itMessages = iterDoc.iterator();

        while (itMessages.hasNext()) {
            Document doc = (Document) itMessages.next();
            MongoMessageObject messageObject = new MongoMessageObject(doc.get("_id").toString(),
                                                                        doc.get("message").toString(),
                                                                        doc.get("userName").toString(),
                                                                        Integer.valueOf(doc.get("channelId").toString()) );

            /*System.out.println(doc.get("message").toString());
            System.out.println(doc.get("userName").toString());
            System.out.println( Integer.valueOf(doc.get("channelId").toString()));
            System.out.println( doc.get("_id").toString());
            System.out.println(doc);*/
            mongoMessageObjectList.add(messageObject);
        }
        return mongoMessageObjectList;
    }

    public List<MongoMessageObject> getMessagesOnChannel(Integer channelId) {
        System.out.println("CHANNEL ID QUERY: " + channelId);

        List<MongoMessageObject> mongoMessageObjectList = new ArrayList<MongoMessageObject>();

        MongoDatabase database = mongoConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("messages");

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("channelId", channelId);
        FindIterable<Document> iterDoc = collection.find(whereQuery);
        Iterator itMessages = iterDoc.iterator();

        while (itMessages.hasNext()) {
            Document doc = (Document) itMessages.next();
            MongoMessageObject messageObject = new MongoMessageObject(doc.get("_id").toString(),
                    doc.get("message").toString(),
                    doc.get("userName").toString(),
                    Integer.valueOf(doc.get("channelId").toString()) );
            mongoMessageObjectList.add(messageObject);
        }

        return mongoMessageObjectList;
    }

}
