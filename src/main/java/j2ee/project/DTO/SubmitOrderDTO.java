package j2ee.project.DTO;

import jakarta.validation.constraints.NotNull;

public class SubmitOrderDTO {
    @NotNull(message = "OrderInfo is mandatory")
    private String orderInfo;

    @NotNull(message = "OrderID is mandatory")
    private Integer orderID;



    public SubmitOrderDTO() {
    }


    public String getOrderInfo() {
        return orderInfo;
    }


    public Integer getOrderID() {
        return orderID;
    }




}
