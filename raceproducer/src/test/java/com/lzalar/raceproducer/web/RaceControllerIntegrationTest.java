package com.lzalar.raceproducer.web;

import com.lzalar.raceproducer.BaseIntegrationTest;
import com.lzalar.raceproducer.constants.RaceTestConstants;
import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


//@Transactional
public class RaceControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser(roles = "administrator")
    public void testCreateRace() throws Exception {
        RaceDTO raceDTO = RaceTestConstants.givenRaceDTOBuilder()
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
    @WithMockUser(roles = "administrator")
    public void testEditRace() throws Exception {
        RaceDTO raceDTO = RaceTestConstants.givenRaceDTOBuilder()
                .name("otherName")
                .build();

        Race expected = RaceTestConstants.givenRaceBuilder()
                .name("otherName")
                .build();

        Race persistedRace = raceRepository.save(RaceTestConstants.givenRace());

        verifyQueueIsEmpty();

        Assertions.assertThat(persistedRace).isNotEqualTo(expected);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/race/" + persistedRace.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raceDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());


        persistedRace = raceRepository.findById(persistedRace.getId()).orElseThrow();

        assertThat(persistedRace).isEqualTo(expected);
        verifyMessageInQueue();
    }

    @Test
    @WithMockUser(roles = "administrator")
    public void testDeleteRace() throws Exception {
        Race persistedRace = raceRepository.save(RaceTestConstants.givenRace());

        verifyQueueIsEmpty();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/race/" + persistedRace.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(raceRepository.findAll().size()).isZero();

        verifyMessageInQueue();
    }
}
