package test;


import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.ws.rs.core.Response;
import repository.EmployeeRepository;
import repository.JobRepository;



import dto.EmployeeRequest;



@QuarkusMain
public class EmployeeServiceTest {

    @InjectMocks
    EmployeeService employeeService;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    JobRepository jobRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setName("Juan");
        request.setLastName("Perez");
        request.setBirthdate("1983-01-01");
        request.setGenderId(1L);
        request.setJobId(1L);

        when(employeeRepository.existsByNameAndLastName(anyString(), anyString())).thenReturn(false);
        when(JobRepository.existsById(anyLong())).thenReturn(true);

        Response response = employeeService.addEmployee(request);

        verify(employeeRepository, times(1)).persist(any(Employee.class));
        assert response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
