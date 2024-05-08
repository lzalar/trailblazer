package com.lzalar.raceproducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzalar.raceproducer.repository.RaceApplicationRepository;
import com.lzalar.raceproducer.repository.RaceRepository;
import com.lzalar.raceproducer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseIntegrationTest {


    public static final String RABBITMQ_IMAGE = "rabbitmq:3.13.2-management";


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

    @Container
    static PostgreSQLContainer<PostgreSQLCustomContainer> postgreSQLContainer = PostgreSQLCustomContainer.getInstance();

    @Container
    static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer(RABBITMQ_IMAGE);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
    }


    @BeforeEach
    void setUp() {
        rabbitAdmin.purgeQueue(queue);
        raceRepository.deleteAll();
        raceApplicationRepository.deleteAll();
//        userRepository.save(new User())

//        initMocks(this);
//        userAuthentication = userAuthenticationRepository.save(entityBuilderUtil.buildUserAuthentication(DEFAULT_EMAIL, "", true, Set.of(RESOURCE_MANAGER)));
//        feedbackPersonDTO = UserDTO.builder().id(userAuthentication.getId()).username(userAuthentication.getUsername()).build();
//        when(userAuthenticationService.getLoggedInUser()).thenReturn(userAuthentication);
//        when(userAuthenticationService.findAllByRole(RESOURCE_MANAGER)).thenReturn(of(userAuthentication));
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
