package cgb.transfert;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import cgb.ServerTransferApp;


@SpringBootTest(classes = ServerTransferApp.class)
@AutoConfigureMockMvc
//@WebMvcTest(Controller.class)
public class ApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAccountById() throws Exception {
        String id = "123456789";
        mockMvc.perform(get("/account/{id}", id).header("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user:secret").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"accountNumber\":\"123456789\",\"solde\":300.0}"));
    }
    
//    @Test
//    public void testCreateTransfer() throws Exception {
//    	JSONObject transferRequest = new JSONObject();
//    	transferRequest.put("sourceAccountNumber", "987654321");
//    	transferRequest.put("destinationAccountNumber", "123456789");
//    	transferRequest.put("amount", 10.0);
//    	transferRequest.put("transferDate", "2025-03-12");
//    	transferRequest.put("description", "test");
//
//    	JSONObject transfer = new JSONObject();
//    	transfer.put("id", 1);
//    	transfer.put("sourceAccountNumber", "987654321");
//    	transfer.put("destinationAccountNumber", "123456789");
//    	transfer.put("amount", 10.0);
//    	transfer.put("transferDate", "2025-03-12");
//    	transfer.put("description", "test");
//    	
//        mockMvc.perform(post("/api/transfers/createTransfer")
//        		.contentType(MediaType.APPLICATION_JSON)
//        		.content(transferRequest.toString())
//        		.header("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user:secret").getBytes(StandardCharsets.UTF_8))))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(transfer.toString()));
//    }

    
    @Test
    public void testCreateAccountValide() throws Exception {
    	JSONObject accountDTO = new JSONObject();
    	accountDTO.put("accountNumber", "FR44123412341234123400");

    	JSONObject account = new JSONObject();
    	account.put("accountNumber", "FR44123412341234123400");
    	account.put("solde", 0.0);
//    	account.put("beneficiaires", "[]");
    	
        mockMvc.perform(post("/account/createAccount")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(accountDTO.toString())
        		.header("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user:secret").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(account.toString()));
    }
    
    @Test
    public void testCreateAccountInvalide() throws Exception {
    	JSONObject accountDTO = new JSONObject();
    	accountDTO.put("accountNumber", "FR44123412341234123400");

    	JSONObject account = new JSONObject();
    	account.put("accountNumber", "FR44123412341234123400");
    	account.put("solde", 1000000.0);
//    	account.put("beneficiaires", "[]");
    	
        mockMvc.perform(post("/account/createAccount")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(accountDTO.toString())
        		.header("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user:secret").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\r\n"
                		+ "    \"status\": \"FAILURE\",\r\n"
                		+ "    \"message\": \"Erreur création compte bancaire : FR44123412341234123400 déjà existant\"\r\n"
                		+ "}"));
    }
    
    @Test
    public void testDeleteAccountValide() throws Exception { 
        String accountNumber = "123456789";
        mockMvc.perform(get("/account/deleteAcccount/{accountNumber}", accountNumber)
        		.header("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user:secret").getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }
    
    
    
    @Test
    void contextLoads() {
    }

}
