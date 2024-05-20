package repository;

import entity.WorkedHours;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class WorkedHoursRepository implements PanacheRepository<WorkedHours> {
}
