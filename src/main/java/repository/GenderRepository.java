package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import entity.Gender;

@ApplicationScoped
public class GenderRepository implements PanacheRepository<Gender> {
}
