package j2ee.project.DTO;

public class TopCustomerPurchaseDTO {
    private Long id;
    private String name;
    private Long purchase;

    public TopCustomerPurchaseDTO(Long id, String name, Long purchase) {
        this.id = id;
        this.name = name;
        this.purchase = purchase;
    }

    public TopCustomerPurchaseDTO() {
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Long getPurchase() {
        return purchase;
    }
}
