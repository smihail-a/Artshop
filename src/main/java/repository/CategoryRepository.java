package repository;
import entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
    <ListCategory> ListCategory findAllByName(Categories name);
    boolean existsByName(String name);
}

