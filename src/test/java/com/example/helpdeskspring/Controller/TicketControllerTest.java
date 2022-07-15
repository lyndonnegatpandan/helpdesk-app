package com.example.helpdeskspring.Controller;

import com.example.helpdeskspring.Model.*;

import com.example.helpdeskspring.Service.TicketService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class TicketControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TicketService ticketService;

    ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    private void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentationContextProvider){
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentationContextProvider)).build();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void getAllTicket() throws Exception {
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        List<Ticket> allTicket = Arrays.asList(ticket);
        when(ticketService.getAllTicket()).thenReturn(allTicket);
        mockMvc.perform(get("/ticket/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].ticket").description("ticket id"),
                                fieldWithPath("[].title").description("title of the ticket"),
                                fieldWithPath("[].description").description("description of the ticket"),
                                fieldWithPath("[].severity").description("severity of the ticket"),
                                fieldWithPath("[].status").description("status of the ticket"),
                                fieldWithPath("[].assignee").description("employee assigned to the ticket"),
                                fieldWithPath("[].watcher").description("watchers of the ticket")
                        )));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void getTicketById() throws Exception{
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        given(ticketService.getTicketById(ticket.getTicket())).willReturn(Optional.of(ticket));
        mockMvc.perform(get("/ticket/"+ticket.getTicket())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("ticket").description("ticket id"),
                                fieldWithPath("title").description("title of the ticket"),
                                fieldWithPath("description").description("description of the ticket"),
                                fieldWithPath("severity").description("severity of the ticket"),
                                fieldWithPath("status").description("status of the ticket"),
                                fieldWithPath("assignee").description("employee assigned to the ticket"),
                                fieldWithPath("watcher").description("watchers of the ticket")
                        )));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void createTicket() throws Exception{
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        given(ticketService.createTicket(any())).willReturn(ticket);
        mockMvc.perform(post("/ticket/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(ticket)))
                .andExpect(status().isCreated())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("ticket").description("ticket id"),
                                fieldWithPath("title").description("title of the ticket"),
                                fieldWithPath("description").description("description of the ticket"),
                                fieldWithPath("severity").description("severity of the ticket"),
                                fieldWithPath("status").description("status of the ticket"),
                                fieldWithPath("assignee").description("employee assigned to the ticket"),
                                fieldWithPath("watcher").description("watchers of the ticket")
                        )));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void addAssignee() throws Exception {
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        given(ticketService.addAssignee(ticket.getTicket(), employee.getId())).willReturn(ticket);
        mockMvc.perform(put("/ticket/assignee/"+ticket.getTicket()+"/"+employee.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("ticket").description("ticket id"),
                                fieldWithPath("title").description("title of the ticket"),
                                fieldWithPath("description").description("description of the ticket"),
                                fieldWithPath("severity").description("severity of the ticket"),
                                fieldWithPath("status").description("status of the ticket"),
                                fieldWithPath("assignee").description("employee assigned to the ticket"),
                                fieldWithPath("watcher").description("watchers of the ticket")
                        )));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void addWatcher() throws Exception {
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        given(ticketService.addWatcher(ticket.getTicket(), employee.getId())).willReturn(ticket);
        mockMvc.perform(put("/ticket/watcher/"+ticket.getTicket()+"/"+employee.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("ticket").description("ticket id"),
                                fieldWithPath("title").description("title of the ticket"),
                                fieldWithPath("description").description("description of the ticket"),
                                fieldWithPath("severity").description("severity of the ticket"),
                                fieldWithPath("status").description("status of the ticket"),
                                fieldWithPath("assignee").description("employee assigned to the ticket"),
                                fieldWithPath("watcher").description("watchers of the ticket")
                        )));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void updateTicket() throws Exception{
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        ticket.setTicket(1L);
        given(ticketService.updateTicket(anyInt(), any())).willReturn(ticket);
        mockMvc.perform(put("/ticket/update/"+ticket.getTicket())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("ticket").description("ticket id"),
                                fieldWithPath("title").description("title of the ticket"),
                                fieldWithPath("description").description("description of the ticket"),
                                fieldWithPath("severity").description("severity of the ticket"),
                                fieldWithPath("status").description("status of the ticket"),
                                fieldWithPath("assignee").description("employee assigned to the ticket"),
                                fieldWithPath("watcher").description("watchers of the ticket")
                        )));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void deleteTicket() throws Exception{
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        doNothing().when(ticketService).deleteTicket(ticket.getTicket());
        mockMvc.perform(delete("/ticket/delete/"+ticket.getTicket())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));
    }


    public static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}