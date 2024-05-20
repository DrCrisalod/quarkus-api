package dto;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor


public class WorkedHoursResponse {
    public Long id;
    public boolean success;

    public WorkedHoursResponse(Long id, boolean success) {
        this.id = id;
        this.success = success;
    }
}
