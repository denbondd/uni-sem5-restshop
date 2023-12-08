package ua.nure.st.patterns.labs.dao.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import ua.nure.st.patterns.labs.dao.CategoryDao;
import ua.nure.st.patterns.labs.entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMongoDbDao implements CategoryDao<ObjectId> {

    private static final String COLLECTION_NAME = "categories";

    private final String dbName;

    public CategoryMongoDbDao(String dbName) {
        this.dbName = dbName;
    }

    private Category<ObjectId> mapDocumentToCategory(Document document) {
        return new Category<>(
                document.getObjectId("_id"),
                document.getString("name")
        );
    }

    @Override
    public List<Category<ObjectId>> getAll() {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            List<Document> documents = collection.find().into(new ArrayList<>());

            return documents.stream()
                    .map(this::mapDocumentToCategory)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Category<ObjectId> getById(ObjectId id) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = collection.find(new Document("_id", id)).first();

            return mapDocumentToCategory(document);
        }
    }

    @Override
    public boolean save(String name) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document("name", name);

            InsertOneResult result = collection.insertOne(document);

            return result.wasAcknowledged();
        }
    }

    @Override
    public boolean update(Category<ObjectId> category) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document("name", category.getName());

            InsertOneResult result = collection.insertOne(document);

            return result.wasAcknowledged();
        }
    }

    @Override
    public boolean delete(ObjectId id) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document("_id", id);

            InsertOneResult result = collection.insertOne(document);

            return result.wasAcknowledged();
        }
    }
}
