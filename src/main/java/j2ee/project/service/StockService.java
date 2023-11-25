package j2ee.project.service;

import j2ee.project.models.Category;
import j2ee.project.models.Product;
import j2ee.project.models.Stock;
import j2ee.project.repository.ProductRepository;
import j2ee.project.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class StockService {
    private final StockRepository stockRepository;

    private final ProductRepository productRepository;

    public StockService(StockRepository stockRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    public List<Stock> getAllSort(int page, long limit, String sortBy, String sortType,String name) {
        //get and sort by id
        Stream<Stock> data = stockRepository.findWithOrder(sortBy, sortType).stream();


        data = data.filter(product -> product.getProduct().getName().toLowerCase().contains(name.toLowerCase()));
        return data.skip((page - 1) * limit).limit(limit).toList();
    }

    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    public long count(String name) {
        Stream<Stock> data = stockRepository.findAll().stream();
        data = data.filter(product -> product.getProduct().getName().toLowerCase().contains(name.toLowerCase()));
        return data.count();
    }


    public Stock getStockByProductId(int product_id) {
        Product product = productRepository.findById(product_id).orElse(null);
        if (product == null) {
            return null;
        }
        return stockRepository.findByProduct(product).orElse(null);
    }
    @Transactional
    public void decreaseStockQuantity(int productId, int quantity) {
        Stock stock = stockRepository.findByProductId(productId);

        if (stock != null) {
            int currentQuantity = stock.getQuantity();
            if (currentQuantity >= quantity) {
                stock.setQuantity(currentQuantity - quantity);
                stockRepository.save(stock);
            } else {
                // Xử lý tình huống không đủ số lượng sản phẩm trong kho
                throw new RuntimeException("Số lượng sản phẩm không đủ trong kho");
            }
        } else {
            // Xử lý tình huống không tìm thấy sản phẩm trong kho
            throw new RuntimeException("Không tìm thấy sản phẩm trong kho");
        }
    }
}
