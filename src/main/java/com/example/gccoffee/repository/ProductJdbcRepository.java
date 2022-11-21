package com.example.gccoffee.repository;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.example.gccoffee.JdbcUtils.*;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.queryForObject("select * from products", productRowMapper);
    }

    @Override
    public Product insert(Product product) {
        var update = jdbcTemplate.update("INSERT INTO products(product_id, produce_name, category, price, description, created_at, updated_at)" +
                " VALUES (UUID_TO_BIN(:productId), :produceName, :category, :price, :description, :createdAt, :updatedAt)", toParamMap(product));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByName(String productName) {
        return Optional.empty();
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        var productId = toUUID(resultSet.getBytes("product_id"));
        var produceName = resultSet.getString("produce_name");
        var category = Category.valueOf(resultSet.getString("category"));
        var price = resultSet.getLong("price");
        var description = resultSet.getString("description");
        var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        var updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Product(productId, produceName, category, price, description, createdAt, updatedAt);
    };

    private Map<String, Object> toParamMap(Product product) {
        var paramMap = new HashMap<String, Object>();
         paramMap.put("productId", product.getProductId().toString().getBytes(StandardCharsets.UTF_8));
         paramMap.put("produceName", product.getProduceName());
         paramMap.put("category", product.getCategory());
         paramMap.put("price", product.getPrice());
         paramMap.put("description", product.getDescription());
         paramMap.put("createdAt", product.getCreatedAt());
         paramMap.put("updatedAt", product.getUpdatedAt());
        return paramMap;
    }
}
