package com.example.helpdeskspring.Controller;

import com.example.helpdeskspring.Model.Users;
import com.example.helpdeskspring.Service.UsersService;
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
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UsersService usersService;



    @BeforeEach
    private void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentationContextProvider){
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentationContextProvider)).build();
    }


    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void getUsers() throws Exception{
        Users users= new Users("admin","admin123","ADMIN");
        List<Users> allUsers= Arrays.asList(users);
        given(usersService.getUsers()).willReturn(allUsers);
        mockMvc.perform(get("/users/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].userId").description("Unique id of the user"),
                                fieldWithPath("[].username").description("Username of the user"),
                                fieldWithPath("[].password").description("Password to access the system"),
                                fieldWithPath("[].role").description("Role of the user(Admin or User)")
                        )));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void createUser() throws Exception{
        Users user= new Users("john","john123","USER");
        user.setUserId(1L);
        given(usersService.createUser(any())).willReturn(user);
        mockMvc.perform(post("/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(user)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("userId").description("Unique id of the user"),
                                fieldWithPath("username").description("Username of the user"),
                                fieldWithPath("password").description("Password to access the system"),
                                fieldWithPath("role").description("Role of the user(Admin or User)")
                        )));
    }

    @Test
    void updateUser() throws Exception{
        Users users= new Users("admin","admin123","ADMIN");
        given(usersService.updateUser(anyInt(), any())).willReturn(users);
        mockMvc.perform(put("/users/update/"+users.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(users)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").description("Unique id of the user"),
                                fieldWithPath("username").description("Updated username of the user"),
                                fieldWithPath("password").description("Password to access the system"),
                                fieldWithPath("role").description("Role of the user(Admin or User)")
                        )));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    void deleteUser() throws Exception{
        Users users= new Users("admin","admin123","ADMIN");
        given(usersService.deleteUser(anyInt())).willReturn(true);
        mockMvc.perform(delete("/users/delete/"+users.getUserId())
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));
    }


    public static String toJson(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}