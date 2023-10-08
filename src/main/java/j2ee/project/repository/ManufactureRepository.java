package j2ee.project.repository;

import j2ee.project.models.Manufacture;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManufactureRepository extends JpaRepository<Manufacture, Integer> {

    //find with sortBy and SortType


    List<Manufacture> findAll(Sort sort);

    default List<Manufacture> findWithOrder(String sortBy, String sortType){
       if (sortBy == null || sortBy.equals("")){
           sortBy = "id";
       }
       if (sortType == null || sortType.equals("")){
           sortType = "ASC";
       }
        Sort.Direction direction = Sort.Direction.fromString(sortType);
        Sort sort = Sort.by(direction, sortBy);
       return findAll(sort);
   }
}
