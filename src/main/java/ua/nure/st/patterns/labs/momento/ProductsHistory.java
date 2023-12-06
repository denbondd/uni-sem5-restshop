package ua.nure.st.patterns.labs.momento;

import ua.nure.st.patterns.labs.entity.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ProductsHistory {

    private final Map<Long, List<Product>> history = new HashMap<>();
    private final Function<Product, Boolean> restoreProduct;

    public ProductsHistory(Function<Product, Boolean> restoreProduct) {
        this.restoreProduct = restoreProduct;
    }

    public void push(Long id, Product product) {
        List<Product> products = history.getOrDefault(id, new ArrayList<>());
        products.add(product);

        history.put(id, products);
    }

    public boolean undo(Long id) {
        List<Product> products = history.getOrDefault(id, List.of());
        // if no history or only one state
        if (products.isEmpty() || products.size() == 1) {
            return false;
        }

        // get second last element because last is current state
        Product product = products.get(products.size() - 2);
        // remove last element because this state should be lost
        history.put(id, products.subList(0, products.size() - 1));

        return restoreProduct.apply(product);
    }
}
