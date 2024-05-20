package repository;


import entity.Employee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {
    public boolean existsByNameAndLastName(String name, String lastName) {
        return find("name = ?1 and lastName = ?2", name, lastName).firstResultOptional().isPresent();
    }
}
