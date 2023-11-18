package mongoblogspringboot.mongoblogspringboot.services;


import com.mongodb.Function;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import org.bson.Document;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

public class MongoGenericService {
    protected MongoDatabase database;
    protected MongoCollection<Document> collection;

    protected MongoClient getMongoClient() {
        return MongoClients.create("mongodb://root:test.123@localhost:27017/?authSource=admin");
    }

    protected  <T> T inTx(Function<MongoCollection<Document>, T> toExecute, String collectionName) {
        MongoClient mongoClient = this.getMongoClient();
        database = mongoClient.getDatabase("mongo-blogs");
        collection = database.getCollection(collectionName);
        try {
            T t = toExecute.apply(collection);
            return t;
        } catch (Exception e) {
            throw e;
        } finally {
            mongoClient.close();
        }
    }

}
