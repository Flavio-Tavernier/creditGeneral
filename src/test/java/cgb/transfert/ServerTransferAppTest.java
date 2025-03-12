package cgb.transfert;

import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.Controller;



@SpringBootTest(classes = ServerTransferApp.class)
@AutoConfigureMockMvc
class ServerTransferAppTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Test
    public void testObtenirAccounts() throws Exception {
//        mockMvc.perform(get("/test/accounts"))
//               .andExpect(status().isOk())
//               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//               .andExpect(content().json("{\"accountNumber\":\"123456789\",\"solde\":690.0},{\"accountNumber\":\"987654321\",\"solde\":110.0},{\"accountNumber\":\"456789123\",\"solde\":2000.0}"));
    }
    
	@Test
	void contextLoads() {
	}
}







