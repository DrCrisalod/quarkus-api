package resource;

import Service.EmployeeService;
import dto.EmployeeRequest;
import dto.WorkedHoursRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;


@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    @Inject
    EmployeeService employeeService;

    @POST
    public Response addEmployee(EmployeeRequest request) {
        return employeeService.addEmployee(request);
    }

    @GET
    @Path("/by-job/{jobId}")
    public Response getEmployeesByJob(@PathParam("jobId") Long jobId) {
        return employeeService.getEmployeesByJob(jobId);
    }

    @POST
    @Path("/worked-hours")
    public Response addWorkedHours(WorkedHoursRequest request) {
        return employeeService.addWorkedHours(request);
    }

    @GET
    @Path("/payments/{employeeId}")
    public Response calculatePayments(@PathParam("employeeId") Long employeeId) {
        return employeeService.calculatePayments(employeeId);
    }
}
