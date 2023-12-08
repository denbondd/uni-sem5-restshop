package ua.nure.st.patterns.labs.dao.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import ua.nure.st.patterns.labs.dao.ShopHasProductDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopHasProductMongoDbDao implements ShopHasProductDao<ObjectId> {

    private static final String COLLECTION_NAME = "shop_has_product";

    private final String dbName;

    public ShopHasProductMongoDbDao(String dbName) {
        this.dbName = dbName;
    }


    @Override
    public List<ProductHasShop<ObjectId>> getProductsInShop(String shopId) {
        ObjectId shopObjectId = new ObjectId(shopId);
        // this not only pull data but also maps product to object from products collection
        // we will use mongodb aggregation framework
        // schema of this table is: {shopId: ObjectId, productId: ObjectId, count: Integer}
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            List<Document> documents = collection.aggregate(
                    List.of(
                            new Document("$match", new Document("shopId", shopObjectId)),
                            new Document("$lookup", new Document()
                                    .append("from", "products")
                                    .append("localField", "productId")
                                    .append("foreignField", "_id")
                                    .append("as", "product")
                            ),
                            new Document("$unwind", new Document("path", "$product")),
                            new Document("$project", new Document()
                                    .append("shopId", 1)
                                    .append("productId", 1)
                                    .append("count", 1)
                                    .append("product", new Document()
                                            .append("_id", "$product._id")
                                            .append("brandId", "$product.brandId")
                                            .append("categoryId", "$product.categoryId")
                                            .append("name", "$product.name")
                                            .append("price", "$product.price")
                                    )
                            )
                    )
            ).into(new ArrayList<>());

            return documents.stream()
                    .map(document -> new ProductHasShop<>(
                            ProductMongoDbDao.mapDocumentToProduct(document.get("product", Document.class)),
                            document.getInteger("count")
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<ShopHasProduct<ObjectId>> getShopsWithProduct(String productId) {
        ObjectId productObjectId = new ObjectId(productId);
        // this not only pull data but also maps shop to object from shops collection
        // we will use mongodb aggregation framework
        // schema of this table is: {shopId: ObjectId, productId: ObjectId, count: Integer}
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            List<Document> documents = collection.aggregate(
                    List.of(
                            new Document("$match", new Document("productId", productObjectId)),
                            new Document("$lookup", new Document()
                                    .append("from", "shops")
                                    .append("localField", "shopId")
                                    .append("foreignField", "_id")
                                    .append("as", "shop")
                            ),
                            new Document("$unwind", new Document("path", "$shop")),
                            new Document("$project", new Document()
                                    .append("shopId", 1)
                                    .append("productId", 1)
                                    .append("count", 1)
                                    .append("shop", new Document()
                                            .append("_id", "$shop._id")
                                            .append("name", "$shop.name")
                                            .append("location", "$shop.location")
                                    )
                            )
                    )
            ).into(new ArrayList<>());

            return documents.stream()
                    .map(document -> new ShopHasProduct<>(
                            ShopMongoDbDao.mapDocumentToShop(document.get("shop", Document.class)),
                            document.getInteger("count")
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean save(String shopId, String productId, Integer count) {
        ObjectId shopObjectId = new ObjectId(shopId);
        ObjectId productObjectId = new ObjectId(productId);
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document()
                    .append("shopId", shopObjectId)
                    .append("productId", productObjectId)
                    .append("count", count);

            return collection.insertOne(document).wasAcknowledged();
        }
    }

    @Override
    public boolean update(String shopId, String productId, Integer count) {
        ObjectId shopObjectId = new ObjectId(shopId);
        ObjectId productObjectId = new ObjectId(productId);
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);
            Document document = new Document()
                    .append("shopId", shopObjectId)
                    .append("productId", productObjectId)
                    .append("count", count);

            return collection.insertOne(document).wasAcknowledged();
        }
    }

    @Override
    public boolean delete(String shopId, String productId) {
        ObjectId shopObjectId = new ObjectId(shopId);
        ObjectId productObjectId = new ObjectId(productId);
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);
            Document document = new Document()
                    .append("shopId", shopObjectId)
                    .append("productId", productObjectId);

            return collection.deleteOne(document).wasAcknowledged();
        }
    }
}
