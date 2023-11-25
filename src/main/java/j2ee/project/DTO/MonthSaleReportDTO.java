package j2ee.project.DTO;

public class MonthSaleReportDTO {
    private Long month;
    private Long total;

    public MonthSaleReportDTO(Long month, Long total) {
        this.month = month;
        this.total = total;
    }

    public MonthSaleReportDTO() {
    }

    public Long getMonth() {
        return month;
    }

    public Long getTotal() {
        return total;
    }

    public void setMonth(Long month) {
        this.month = month;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
