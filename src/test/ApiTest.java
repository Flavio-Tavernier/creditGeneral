import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest(classes = ServerTransferApp.class)
@AutoConfigureMockMvc
//@WebMvcTest(Controller.class)
public class ApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAccountById() throws Exception {
        Long id = "123456789";
        mockMvc.perform(get("/account/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"accountNumber\":123456789,\"solde\":\"690.0\"}"));
    }

    @Test
    void contextLoads() {
    }

}
