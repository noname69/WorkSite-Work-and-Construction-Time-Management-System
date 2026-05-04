package online.nonamelab.WorkSite.user;

import online.nonamelab.WorkSite.model.Role;
import online.nonamelab.WorkSite.model.User;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    private String adminToken;

    // ======================
    // SETUP
    // ======================
    @BeforeEach
    void setup() throws Exception {

        userRepository.deleteAll();

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@noname.com");
        admin.setPassword(encoder.encode("testpassword"));
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);

        adminToken = login("admin@noname.com", "testpassword");
    }

    // ======================
    // CREATE USER (ADMIN ONLY)
    // ======================
    @Test
    void shouldCreateUser_whenAdmin() throws Exception {

        mockMvc.perform(post("/api/users")
                        .header("Authorization", bearer(adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Test User",
                          "email": "test@noname.com",
                          "password": "password123",
                          "role": "WORKER"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@noname.com"))
                .andExpect(jsonPath("$.role").value("WORKER"));
    }

    // ======================
    // GET ALL USERS
    // ======================
    @Test
    void shouldGetAllUsers() throws Exception {

        mockMvc.perform(get("/api/users")
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ======================
    // GET USER BY ID
    // ======================
    @Test
    void shouldGetUserById() throws Exception {

        User user = new User();
        user.setName("Worker");
        user.setEmail("worker@noname.com");
        user.setPassword(encoder.encode("pass"));
        user.setRole(Role.WORKER);

        user = userRepository.save(user);

        mockMvc.perform(get("/api/users/" + user.getId())
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("worker@noname.com"));
    }

    // ======================
    // UPDATE USER
    // ======================
    @Test
    void shouldUpdateUser() throws Exception {

        User user = new User();
        user.setName("Old Name");
        user.setEmail("old@noname.com");
        user.setPassword(encoder.encode("pass"));
        user.setRole(Role.WORKER);

        user = userRepository.save(user);

        mockMvc.perform(put("/api/users/" + user.getId())
                        .header("Authorization", bearer(adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "New Name",
                          "email": "new@noname.com",
                          "role": "WORKER"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.email").value("new@noname.com"));
    }

    // ======================
    // DELETE USER
    // ======================
    @Test
    void shouldDeleteUser() throws Exception {

        User user = new User();
        user.setName("ToDelete");
        user.setEmail("delete@noname.com");
        user.setPassword(encoder.encode("pass"));
        user.setRole(Role.WORKER);

        user = userRepository.save(user);

        mockMvc.perform(delete("/api/users/" + user.getId())
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isNoContent());
    }

    // ======================
    // GET ME
    // ======================
    @Test
    void shouldGetMe() throws Exception {

        mockMvc.perform(get("/api/users/me")
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@noname.com"));
    }

    // ======================
    // HELPERS
    // ======================
    private String login(String email, String password) throws Exception {

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "email": "%s",
                          "password": "%s"
                        }
                        """.formatted(email, password)))
                .andReturn();

        return new JsonPathExpectationsHelper("$.accessToken")
                .evaluateJsonPath(result.getResponse().getContentAsString())
                .toString();
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}