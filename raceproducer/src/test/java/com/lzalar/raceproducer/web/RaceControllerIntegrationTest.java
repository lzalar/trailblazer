package com.lzalar.raceproducer.web;

import com.lzalar.raceproducer.BaseIntegrationTest;
import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.domain.race.RaceApplication;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.lzalar.raceproducer.constants.RaceApplicationTestConstants.givenRaceApplication;
import static com.lzalar.raceproducer.constants.RaceTestConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


public class RaceControllerIntegrationTest extends BaseIntegrationTest {

    public static final String BASE_URL = "/api/v1/race";

    @Test
    @WithMockUser(roles = "administrator")
    public void givenAdministrator_createRace_successAndEmitEvent() throws Exception {
        RaceDTO raceDTO = givenRaceDTOBuilder()
                .id(null)
                .build();

        assertThat(raceRepository.findAll().size()).isZero();

        verifyQueueIsEmpty();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raceDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());


        assertThat(raceRepository.findAll().size()).isOne();

        verifyMessageInQueue();
    }

    @Test
    @WithMockUser(roles = "administrator")
    public void givenAdministrator_editRace_successAndEmitEvent() throws Exception {
        RaceDTO raceDTO = givenRaceDTOBuilder()
                .name("otherName")
                .build();

        Race expected = givenRaceBuilder()
                .name("otherName")
                .build();

        Race persistedRace = raceRepository.save(givenRace());

        verifyQueueIsEmpty();

        assertThat(persistedRace).isNotEqualTo(expected);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + persistedRace.getId())
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
    public void givenAdministrator_deleteRace_successAndEmitEvent() throws Exception {
        Race persistedRace = raceRepository.save(givenRace());

        verifyQueueIsEmpty();

        assertThat(raceRepository.findAll().size()).isOne();

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + persistedRace.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(raceRepository.findAll().size()).isZero();

        verifyMessageInQueue();
    }

    @Test
    @WithMockUser(roles = "administrator")
    public void givenRaceApplicationsExistOnRace_deleteRace_deleteRaceApplications() throws Exception {
        Race persistedRace = raceRepository.save(givenRace());
        raceApplicationRepository.save(givenRaceApplication(persistedUser, persistedRace));

        verifyQueueIsEmpty();

        assertThat(raceApplicationRepository.findAll().size()).isOne();

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + persistedRace.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(raceRepository.findAll().size()).isZero();
        assertThat(raceApplicationRepository.findAll().size()).isZero();

        verifyMessageInQueue();
    }

    @Test
    @WithMockUser(roles = "applicant")
    public void givenApplicant_deleteRace_forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + RACE_ID))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "applicant")
    public void givenApplicant_editRace_forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + RACE_ID))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "applicant")
    public void givenApplicant_createRace_forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + RACE_ID))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void givenNoUser_deleteRace_unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + RACE_ID))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
