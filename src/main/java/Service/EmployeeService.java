package Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import repository.EmployeeRepository;
import repository.JobRepository;
import repository.WorkedHoursRepository;

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
import entity.WorkedHours;
import lombok.RequiredArgsConstructor;


@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;
    private final WorkedHoursRepository workedHoursRepository;

    @Transactional
    public Response addEmployee(EmployeeRequest request) {
    	// Verificar si el empleado ya existe
        if (employeeRepository.existsByNameAndLastName(request.getName(), request.getLastName())) {
            return Response.status(Response.Status.CONFLICT).entity("Employee already exists").build();
        }
        // Verificar si el trabajo existe
        if (!jobRepository.findByIdOptional(request.getJobId()).isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Job not found").build();
        }

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setLastName(request.getLastName());
        employee.setBirthdate(LocalDate.parse(request.getBirthdate()));
        employee.setJob(jobRepository.findById(request.getJobId()));

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
            .flatMap(List::stream)
            .map(EmployeeResponse::new)
            .collect(Collectors.toList());

        return Response.ok(new EmployeeListResponse(filteredEmployees, true)).build();
    }

    @Transactional
    public Response addWorkedHours(WorkedHoursRequest request) {
        if (!employeeRepository.findByIdOptional(request.getEmployeeId()).isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
        }

        WorkedHours workedHours = new WorkedHours();
        workedHours.setEmployeeId(request.getEmployeeId());
        workedHours.setWorkedDate(LocalDate.parse(request.getWorkedDate()));
        workedHours.setHoursWorked(request.getHoursWorked());

        workedHoursRepository.persist(workedHours);

        return Response.ok(new WorkedHoursResponse(workedHours.getId(), true)).build();
    }

    public Response calculatePayments(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId);
        if(employee == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
        }

        List<WorkedHours> workedHoursList = workedHoursRepository.find("employeeId", employeeId).list();
        int totalHours = workedHoursList.stream().mapToInt(WorkedHours::getHoursWorked).sum();
        double payment = totalHours * employee.getJob().getSalary().doubleValue();

        return Response.ok(new PaymentResponse(totalHours, payment)).build();
    }
}
