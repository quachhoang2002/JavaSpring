package j2ee.project.POJO;

public class CustomerInfo {
    private String customerName;
    private String shippingAddress;
    private String customerPhone;
    private Integer status;

    public CustomerInfo() {
    }

    public CustomerInfo(String customerName, String shippingAddress, String customerPhone, Integer status) {
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.customerPhone = customerPhone;
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
