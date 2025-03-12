public class ApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testObtenirUtilisateur() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"nom\":\"Lavoisier\"}"));
    }

    @Test
    void contextLoads() {
    }

}
