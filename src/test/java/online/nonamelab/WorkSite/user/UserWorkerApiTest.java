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
class UserWorkerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    private String workerToken;

    @BeforeEach
    void setup() throws Exception {

        userRepository.deleteAll();

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@noname.com");
        admin.setPassword(encoder.encode("testpassword"));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);

        User worker = new User();
        worker.setName("Worker");
        worker.setEmail("worker@noname.com");
        worker.setPassword(encoder.encode("testpassword"));
        worker.setRole(Role.WORKER);
        userRepository.save(worker);

        workerToken = login("worker@noname.com", "testpassword");
    }

    // ❌ WORKER CANNOT CREATE USER
    @Test
    void workerShouldNotCreateUser() throws Exception {

        mockMvc.perform(post("/api/users")
                        .header("Authorization", bearer(workerToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Hack",
                          "email": "hack@noname.com",
                          "password": "123456",
                          "role": "WORKER"
                        }
                        """))
                .andExpect(status().isForbidden());
    }

    // ❌ WORKER CANNOT DELETE USER
    @Test
    void workerShouldNotDeleteUser() throws Exception {

        User user = new User();
        user.setName("Target");
        user.setEmail("target@noname.com");
        user.setPassword(encoder.encode("pass"));
        user.setRole(Role.WORKER);

        user = userRepository.save(user);

        mockMvc.perform(delete("/api/users/" + user.getId())
                        .header("Authorization", bearer(workerToken)))
                .andExpect(status().isForbidden());
    }

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