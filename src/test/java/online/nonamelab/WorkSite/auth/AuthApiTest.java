package online.nonamelab.WorkSite.auth;


import online.nonamelab.WorkSite.model.Role;
import online.nonamelab.WorkSite.user.model.User;
import online.nonamelab.WorkSite.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.JsonPathExpectationsHelper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    // ======================
    // 🧹 CLEAN TEST DATA
    // ======================
    @BeforeEach
    void setup() {
        userRepository.deleteAll(); // 🔥 FIX: prevents duplicate email error

        User user = new User();
        user.setEmail("admin@noname.com");
        user.setPassword(encoder.encode("testpassword"));
        user.setRole(Role.ADMIN);
        user.setName("Admin");

        userRepository.save(user);
    }

    // ======================
    // 🔐 LOGIN TESTS
    // ======================

    @Test
    void shouldLoginSuccessfully() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "admin@noname.com",
                                  "password": "testpassword"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.email").value("admin@noname.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andReturn();

        String token = readJson(result, "$.accessToken");
        assertFalse(token.isBlank(), "Token should not be blank");
    }

    @Test
    void shouldReturnUnauthorized_whenPasswordWrong() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "admin@noname.com",
                                  "password": "wrong"
                                }
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorized_whenUserNotFound() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "notfound@noname.com",
                                  "password": "testpassword"
                                }
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnBadRequest_whenValidationFails() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "",
                                  "password": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors").exists());
    }

    // ======================
    // 🔐 SECURITY TESTS
    // ======================

    @Test
    void shouldReturnUnauthorized_whenNoToken() throws Exception {
        mockMvc.perform(get("/api/sites"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorized_whenTokenInvalid() throws Exception {
        mockMvc.perform(get("/api/sites")
                        .header("Authorization", "Bearer invalid.token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorized_whenBearerMissing() throws Exception {
        String token = loginAndGetToken("admin@noname.com", "testpassword");

        mockMvc.perform(get("/api/sites")
                        .header("Authorization", token)) // ❌ missing Bearer
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowAccess_whenTokenValid() throws Exception {
        String token = loginAndGetToken("admin@noname.com", "testpassword");

        mockMvc.perform(get("/api/sites")
                        .header("Authorization", bearer(token)))
                .andExpect(status().isOk());
    }

    // ======================
    // 🧰 HELPERS
    // ======================

    private String loginAndGetToken(String email, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "%s",
                                  "password": "%s"
                                }
                                """.formatted(email, password)))
                .andExpect(status().isOk())
                .andReturn();

        return readJson(result, "$.accessToken");
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }

    private String readJson(MvcResult result, String path) throws Exception {
        JsonPathExpectationsHelper helper = new JsonPathExpectationsHelper(path);
        return String.valueOf(helper.evaluateJsonPath(
                result.getResponse().getContentAsString()
        ));
    }
}
