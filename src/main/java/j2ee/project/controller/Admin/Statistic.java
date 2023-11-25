package j2ee.project.controller.Admin;

import j2ee.project.DTO.MonthSaleReportDTO;
import j2ee.project.DTO.TopCustomerPurchaseDTO;
import j2ee.project.DTO.TopEmployeeSalesDTO;
import j2ee.project.DTO.TopProductSellingDTO;
import j2ee.project.controller.Controller;
import j2ee.project.repository.OrderRepository;
import j2ee.project.repository.ProductRepository;
import j2ee.project.repository.UserRepository;
import j2ee.project.service.StatisticsSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;

@RestController
@RequestMapping("/api/admin/statistic")
public class Statistic extends Controller {


    @Autowired
    private StatisticsSvc statisticsSvc;

    @Autowired
    private OrderRepository order;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private String decimalFormat(long number) {
        DecimalFormat decimalFormat = new DecimalFormat("###");
        return decimalFormat.format(number);
    }

    //get top 5 products
    @GetMapping("/top-products")
    public ResponseEntity<String> getTopProducts() {
        try {

            List<TopProductSellingDTO> listManufacture = statisticsSvc.getTopProducts();
            return this.successResponse("Get top products successfully", listManufacture);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //top customer
    @GetMapping("/top-customers")
    public ResponseEntity<String> getTopCustomers() {
        try {
            List<TopCustomerPurchaseDTO> listManufacture = statisticsSvc.getTopPurchasingUsers();
            return this.successResponse("Get top customers successfully", listManufacture);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //top employee
    @GetMapping("/top-employees")
    public ResponseEntity<String> getTopEmployees() {
        try {
            List<TopEmployeeSalesDTO> listManufacture = statisticsSvc.getTopEmployeeSales();
            return this.successResponse("Get top employees successfully", listManufacture);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //get total profit
    @GetMapping("/total-profit")
    public ResponseEntity<String> getTotalProfit() {
        try {
            Long totalProfit = order.getTotalProfit();
            if (totalProfit == null) {
                totalProfit = 0L;
            }
            return this.successResponse("Get total profit successfully", decimalFormat(totalProfit));
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //get total orders
    @GetMapping("/total-orders")
    public ResponseEntity<String> getTotalOrders() {
        try {
            Long totalOrders = order.getTotalOrders();
            return this.successResponse("Get total orders successfully", decimalFormat(totalOrders));
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //get total products
    @GetMapping("/total-products")
    public ResponseEntity<String> getTotalProducts() {
        try {
            Long totalProducts = productRepository.getTotalProducts();
            return this.successResponse("Get total products successfully", decimalFormat(totalProducts));
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //get total customers
    @GetMapping("/total-customers")
    public ResponseEntity<String> getTotalCustomers() {
        try {
            Long totalCustomers = userRepository.getTotalUsers();
            return this.successResponse("Get total customers successfully", decimalFormat(totalCustomers));
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //profit by year
    @GetMapping("/profit")
    public ResponseEntity<String> getProfitByYear(@RequestParam("year") int year) {
        try {
            List<MonthSaleReportDTO> profitByYear = statisticsSvc.getProfitByYear(year);
            return this.successResponse("Get profit by year successfully", profitByYear);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

}
