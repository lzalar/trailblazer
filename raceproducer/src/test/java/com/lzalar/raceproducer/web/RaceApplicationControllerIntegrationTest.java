package com.lzalar.raceproducer.web;

import com.lzalar.raceproducer.BaseIntegrationTest;
import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.lzalar.raceproducer.constants.RaceApplicationTestConstants.RACE_APPLICATION_ID;
import static com.lzalar.raceproducer.constants.RaceTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;


public class RaceApplicationControllerIntegrationTest extends BaseIntegrationTest {

    private static final String BASE_URL = "/api/v1/race/" + RACE_ID + "/application";

    @Test
    @WithMockUser
    public void givenAnyUser_createRaceApplication_successAndEmitEvent() throws Exception {
        RaceDTO raceDTO = givenRaceDTOBuilder()
                .id(null)
                .build();

        assertThat(raceRepository.findAll().size()).isZero();

        verifyQueueIsEmpty();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/race")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raceDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());


        assertThat(raceRepository.findAll().size()).isOne();

        verifyMessageInQueue();
    }

    @Test
    @WithMockUser
    public void givenAnyUser_deleteRaceApplication_successAndEmitEvent() throws Exception {
        Race persistedRace = raceRepository.save(givenRace());

        verifyQueueIsEmpty();

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + persistedRace.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(raceRepository.findAll().size()).isZero();

        verifyMessageInQueue();
    }

    @Test
    public void givenNoUser_createRaceApplication_unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + RACE_APPLICATION_ID))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
