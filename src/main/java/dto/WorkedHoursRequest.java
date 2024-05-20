package dto;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class WorkedHoursRequest {
    public Long employeeId;
    public String workedDate;
    public int hoursWorked;
}
