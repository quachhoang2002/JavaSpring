package j2ee.project.service;

import j2ee.project.DTO.MonthSaleReportDTO;
import j2ee.project.DTO.TopCustomerPurchaseDTO;
import j2ee.project.DTO.TopEmployeeSalesDTO;
import j2ee.project.DTO.TopProductSellingDTO;
import j2ee.project.repository.OrderDetailsRepository;
import j2ee.project.repository.OrderRepository;
import j2ee.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsSvc {

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public StatisticsSvc(ProductRepository productRepository, OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }




    public List<TopProductSellingDTO> getTopProducts() {
        List<Object[]> list = productRepository.findBestSeller();
        List<TopProductSellingDTO> list1 = new ArrayList<>();
        for (Object[] objects : list) {
            //id , quantity , name
            //GET TYPE OF OBJECTS[0] TO LON
            System.out.println(objects[0] + " --" + objects[1] + " --" + objects[2]);
            Long id = convertToNumber(objects[0]);
            Long quantity = convertToNumber(objects[1]);
            String name = (String) objects[2];
            list1.add(new TopProductSellingDTO(id, name, quantity));
        }
        return list1;
    }

    public List<TopCustomerPurchaseDTO> getTopPurchasingUsers() {
        List<Object[]> list = orderRepository.findTopPurchasingUsers();
        List<TopCustomerPurchaseDTO> list1 = new ArrayList<>();
        for (Object[] objects : list) {
            //id , quantity , name
            //GET TYPE OF OBJECTS[0] TO LON
            System.out.println(objects[0] + " --" + objects[1] + " --" + objects[2]);
            Long id = convertToNumber(objects[0]);
            String name = (String) objects[1];
            Long quantity = convertToNumber(objects[2]);
            list1.add(new TopCustomerPurchaseDTO(id, name, quantity));
        }
        return list1;
    }

    public List<TopEmployeeSalesDTO> getTopEmployeeSales() {
        List<Object[]> list = orderRepository.findTopEmployeeSales();
        List<TopEmployeeSalesDTO> list1 = new ArrayList<>();
        for (Object[] objects : list) {
            //id , quantity , name
            //GET TYPE OF OBJECTS[0] TO LON
            System.out.println(objects[0] + " --" + objects[1] + " --" + objects[2]);
            Long id = convertToNumber(objects[0]);
            String name = (String) objects[1];
            Long quantity = convertToNumber(objects[2]);
            list1.add(new TopEmployeeSalesDTO(id, name, quantity));
        }
        return list1;
    }

    //profit by year
    public List<MonthSaleReportDTO> getProfitByYear(int year) {
        List<Object[]> data = orderRepository.findTotalProfitByYear(year);
        List<MonthSaleReportDTO> list = new ArrayList<>();
        for (Object[] objects : data) {
            //id , quantity , name
            //GET TYPE OF OBJECTS[0] TO LON
            Long month = convertToNumber(objects[0]);
            Long profit = convertToNumber(objects[1]);
            list.add(new MonthSaleReportDTO(month, profit));
        }
        return list;
    }

    public static  Long convertToNumber(Object object){
        return (Number) object == null ? null : ((Number) object).longValue();
    }






}
