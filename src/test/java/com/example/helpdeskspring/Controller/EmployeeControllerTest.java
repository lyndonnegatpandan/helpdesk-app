package com.example.helpdeskspring.Controller;

import com.example.helpdeskspring.Model.Department;
import com.example.helpdeskspring.Model.Employee;
import com.example.helpdeskspring.Service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class EmployeeControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    ObjectMapper objectMapper = new ObjectMapper();


    @MockBean
    private EmployeeService employeeService;



    @BeforeEach
    private void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentationContextProvider){
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentationContextProvider)).build();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void getEmployees() throws Exception{
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        List<Employee> allEmployee = Arrays.asList(employee);
        when(employeeService.getEmployees()).thenReturn(allEmployee);
        mockMvc.perform(get("/employee/list")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].id").description("Employee id"),
                                fieldWithPath("[].employeeNumber").description("Employee Number"),
                                fieldWithPath("[].firstName").description("First Name"),
                                fieldWithPath("[].middleName").description("Middle Name"),
                                fieldWithPath("[].lastName").description("Last Name"),
                                fieldWithPath("[].department").description("Department")
                        )));



    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void addEmployee() throws Exception {
          Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
          given(employeeService.createEmployee(any())).willReturn(employee);
          mockMvc.perform(post("/employee/add")
                          .contentType(MediaType.APPLICATION_JSON)
                          .content(toJson(employee)))
                  .andExpect(status().isCreated())
                  .andDo(document("{methodName}",
                          preprocessRequest(prettyPrint()),
                          preprocessResponse(prettyPrint()),
                          responseFields(
                                  fieldWithPath("id").description("Employee id"),
                                  fieldWithPath("employeeNumber").description("Employee Number"),
                                  fieldWithPath("firstName").description("First Name"),
                                  fieldWithPath("middleName").description("Middle Name"),
                                  fieldWithPath("lastName").description("Last Name"),
                                  fieldWithPath("department").description("Department")
                          )));


    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void getEmployeeById() throws Exception {
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        given(employeeService.getEmployeeById(employee.getId())).willReturn(Optional.of(employee));
        mockMvc.perform(get("/employee/"+employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").description("Employee id"),
                                fieldWithPath("employeeNumber").description("Employee Number"),
                                fieldWithPath("firstName").description("First Name"),
                                fieldWithPath("middleName").description("Middle Name"),
                                fieldWithPath("lastName").description("Last Name"),
                                fieldWithPath("department").description("Department")
                        )));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void updateEmployee() throws Exception{
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        Long testId = 1L;
        employee.setId(testId);
        given(employeeService.updateEmployee(anyInt(), any())).willReturn(employee);
        mockMvc.perform(RestDocumentationRequestBuilders.put("/employee/update/"+testId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("Employee id"),
                                fieldWithPath("employeeNumber").description("Employee Number"),
                                fieldWithPath("firstName").description("First Name"),
                                fieldWithPath("middleName").description("Middle Name"),
                                fieldWithPath("lastName").description("Last Name"),
                                fieldWithPath("department").description("Department")
                        )));


    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void deleteEmployee() throws Exception {
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        doNothing().when(employeeService).deleteEmployee(employee.getId());
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/employee/delete/"+employee.getId())
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    @Test
    public void myTest(){
        assertNotNull(mockMvc);
    }

    public static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}