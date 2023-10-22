package j2ee.project.controller.Admin;

import j2ee.project.controller.Controller;
import j2ee.project.models.Product;
import j2ee.project.models.Stock;
import j2ee.project.repository.StockRepository;
import j2ee.project.service.ProductService;
import j2ee.project.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/stock")
public class StockController extends Controller {
    @Autowired
    private StockRepository stockRepository;


    //service
    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<String> importStock(@RequestBody Stock stock) {
        try {
            Product product = productService.getProductById(stock.getProduct().getId()).get();
            if (product == null) {
                return errorResponse("Product not found.");
            }
            stock.setProduct(product);
            Optional<Stock> existingWareHouse = stockRepository.findByProduct(stock.getProduct());

            if (existingWareHouse.isPresent()) {
                Stock storedWareHouse = existingWareHouse.get();
                int newQuantity = storedWareHouse.getQuantity() + stock.getQuantity(); // Tính toán quantity mới
                stock.setId(storedWareHouse.getId());
                stock.setQuantity(newQuantity); // Cập nhật quantity theo giá trị mới
            }

            Stock updatedData = stockRepository.save(stock);
            return successResponse("Updated successfully in the warehouse.", updatedData);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }


    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> get(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "8") int size,
                                              @RequestParam(defaultValue = "id") String sortBy,
                                              @RequestParam(defaultValue = "ASC") String sortType
    ) {
        try {
            List<Stock> products = stockService.getAllSort(page, size, sortBy, sortType);
            long totalItems = stockService.count();
            Map<String, Object> metaData = buildPage(totalItems, page, size);
            return this.successResponse("Get all stock successfully", products, metaData);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @GetMapping("/product/{id}")
    @ResponseBody
    public ResponseEntity<String> getStockByProductID(@PathVariable int id) {
        try {
            Stock stock = stockService.getStockByProductId(id);
            if (stock == null) {
                return errorResponse("Stock not found.");
            }
            return this.successResponse("Get stock successfully", stock);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }

    }
}
