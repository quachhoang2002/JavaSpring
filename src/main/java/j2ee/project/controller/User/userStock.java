package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.Stock;
import j2ee.project.repository.StockRepository;
import j2ee.project.service.ProductService;
import j2ee.project.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/stock")
public class userStock extends Controller {
    @Autowired
    private StockRepository stockRepository;


    //service
    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;
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
