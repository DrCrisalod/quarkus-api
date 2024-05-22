package Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.persistence.Id;
import repository.EmployeeRepository;
import repository.JobRepository;
import repository.WorkedHoursRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import dto.EmployeeListResponse;
import dto.EmployeeRequest;
import dto.EmployeeResponse;
import dto.PaymentResponse;
import dto.WorkedHoursRequest;
import dto.WorkedHoursResponse;
import entity.Employee;
import entity.Gender;
import entity.Job;
import entity.WorkedHours;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
public class EmployeeService {
    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    JobRepository jobRepository;
    @Inject
    WorkedHoursRepository workedHoursRepository;

    @Transactional
    public Response addEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByNameAndLastName(request.getName(), request.getLastName())) {
            return Response.status(Response.Status.CONFLICT).entity("Employee already exists").build();
        }

        Job job = jobRepository.findById(request.getJobId());
        if (job == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Job not found").build();
        }

        Gender gender = new Gender();
        gender.setId(request.getGenderId());

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setLastName(request.getLastName());
        employee.setBirthdate(LocalDate.parse(request.getBirthdate()));
        employee.setJob(job);
        employee.setGender(gender);

        employeeRepository.persist(employee);

        return Response.ok(new EmployeeResponse(employee.getId(), true)).build();
    }

    public Response getEmployeesByJob(Long jobId) {
        List<Employee> employees = employeeRepository.find("job.id", jobId).list();
        List<EmployeeResponse> filteredEmployees = employees.stream()
            .sorted((e1, e2) -> e1.getLastName().compareTo(e2.getLastName()))
            .collect(Collectors.groupingBy(Employee::getLastName))
            .values()
            .stream()
            .map(list -> list.stream().findFirst().orElse(null))
            .filter(e -> e != null)
            .map(EmployeeResponse::new)
            .collect(Collectors.toList());

        return Response.ok(filteredEmployees).build();
    }

    @Transactional
    public Response addWorkedHours(Long employeeId, WorkedHoursRequest request) {
        if (!employeeRepository.findByIdOptional(employeeId).isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
        }

        WorkedHours workedHours = new WorkedHours();
        workedHours.setEmployeeId(employeeId);
        workedHours.setWorkedDate(LocalDate.parse(request.getWorkedDate()));
        workedHours.setHoursWorked(request.getHoursWorked());

        workedHoursRepository.persist(workedHours);

        return Response.ok(new WorkedHoursResponse(workedHours.getId(), true)).build();
    }

    public Response calculatePayment(Long employeeId, LocalDate startDate, LocalDate endDate) {
        List<WorkedHours> workedHoursList = workedHoursRepository.find("employeeId = ?1 and workedDate between ?2 and ?3", employeeId, startDate, endDate).list();
        if (workedHoursList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No worked hours found for this period").build();
        }

        Employee employee = employeeRepository.findById(employeeId);
        BigDecimal totalHours = workedHoursList.stream()
            .map(WorkedHours::getHoursWorked)
            .map(BigDecimal::valueOf)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalPayment = totalHours.multiply(employee.getJob().getSalary());

        PaymentResponse paymentResponse = new PaymentResponse(employeeId, totalHours, totalPayment, true);
        return Response.ok(paymentResponse).build();
    }
}

