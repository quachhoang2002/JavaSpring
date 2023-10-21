package j2ee.project.service;

import j2ee.project.repository.WareHouseRepository;
import org.springframework.stereotype.Service;

@Service
public class WareHouseService {
    private final WareHouseRepository wareHouseRepository;

    public WareHouseService(WareHouseRepository wareHouseRepository) {
        this.wareHouseRepository = wareHouseRepository;
    }
}
