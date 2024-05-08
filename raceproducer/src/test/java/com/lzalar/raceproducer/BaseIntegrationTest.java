package com.lzalar.raceproducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzalar.raceproducer.constants.UserTestConstants;
import com.lzalar.raceproducer.domain.user.User;
import com.lzalar.raceproducer.repository.RaceApplicationRepository;
import com.lzalar.raceproducer.repository.RaceRepository;
import com.lzalar.raceproducer.repository.UserRepository;
import com.lzalar.raceproducer.service.auth.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseIntegrationTest {

    @Value("${rabbitmq.queue.name}")
    protected String queue;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected RaceRepository raceRepository;
    @Autowired
    protected RaceApplicationRepository raceApplicationRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RabbitAdmin rabbitAdmin;
    protected User persistedUser;
    @MockBean
    private AuthenticationService userAuthenticationService;

    @Container
    static PostgreSQLContainer<PostgreSQLSharedContainer> postgreSQLContainer = PostgreSQLSharedContainer.getInstance();

    @Container
    static RabbitMQContainer rabbitMQContainer = RabbitMQSharedContainer.getInstance();


    @BeforeEach
    void setUp() {
        rabbitAdmin.purgeQueue(queue);
        raceApplicationRepository.deleteAll();
        raceRepository.deleteAll();
        userRepository.deleteAll();
        persistedUser = userRepository.save(UserTestConstants.givenUser());
        when(userAuthenticationService.getCurrentUser()).thenReturn(persistedUser);
        when(userAuthenticationService.getCurrentUserId()).thenReturn(persistedUser.getId());
    }



    protected void verifyMessageInQueue() {
        await().atMost(5,TimeUnit.SECONDS).until(this::verifyOneMessageInQueue, Boolean.TRUE::equals);
    }

    protected boolean verifyOneMessageInQueue() {
        return rabbitAdmin.getQueueInfo(queue).getMessageCount() == 1;
    }

    protected void verifyQueueIsEmpty(){
        assertThat(rabbitAdmin.getQueueInfo(queue).getMessageCount()).isEqualTo(0);
    }
}
