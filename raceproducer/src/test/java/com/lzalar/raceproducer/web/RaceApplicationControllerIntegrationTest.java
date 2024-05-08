package com.lzalar.raceproducer.web;

import com.lzalar.raceproducer.BaseIntegrationTest;
import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.domain.race.RaceApplication;
import com.lzalar.raceproducer.web.dto.RaceApplicationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.lzalar.raceproducer.constants.RaceApplicationTestConstants.givenRaceApplication;
import static com.lzalar.raceproducer.constants.RaceApplicationTestConstants.givenRaceApplicationDTO;
import static com.lzalar.raceproducer.constants.RaceTestConstants.RACE_ID;
import static com.lzalar.raceproducer.constants.RaceTestConstants.givenRace;
import static org.assertj.core.api.Assertions.assertThat;


public class RaceApplicationControllerIntegrationTest extends BaseIntegrationTest {

    private static final String BASE_URL = "/api/v1/race/" + RACE_ID + "/application";

    @Test
    @WithMockUser
    public void givenAnyUser_createRaceApplication_successAndEmitEvent() throws Exception {
        raceRepository.save(givenRace());
        RaceApplicationDTO raceApplicationDTO = givenRaceApplicationDTO();

        assertThat(raceApplicationRepository.findAll().size()).isZero();

        verifyQueueIsEmpty();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raceApplicationDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(raceApplicationRepository.findAll().size()).isOne();

        verifyMessageInQueue();
    }

    @Test
    @WithMockUser
    public void givenAnyUserRaceApplicationAlreadyExists_createRaceApplication_successAndEmitEvent() throws Exception {
        Race persistedRace = raceRepository.save(givenRace());
        RaceApplicationDTO raceApplicationDTO = givenRaceApplicationDTO();
        raceApplicationRepository.save(givenRaceApplication(persistedUser, persistedRace));

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raceApplicationDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        assertThat(raceApplicationRepository.findAll().size()).isOne();
    }

    @Test
    @WithMockUser
    public void givenAnyUser_deleteRaceApplication_successAndEmitEvent() throws Exception {
        Race persistedRace = raceRepository.save(givenRace());
        RaceApplication persistedRaceApplication = raceApplicationRepository.save(givenRaceApplication(persistedUser, persistedRace));

        verifyQueueIsEmpty();

        assertThat(raceApplicationRepository.findAll().size()).isOne();

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL+ "/" + persistedRaceApplication.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(raceApplicationRepository.findAll().size()).isZero();

        verifyMessageInQueue();
    }
}
