package dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
    public Integer genderId;
    public Long jobId;
    public String name;
    public String lastName;
    public String birthdate;
	
}
