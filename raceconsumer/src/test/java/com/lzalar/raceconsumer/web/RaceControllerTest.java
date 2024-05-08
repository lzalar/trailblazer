package com.lzalar.raceconsumer.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lzalar.raceconsumer.BaseIntegrationTest;
import com.lzalar.raceconsumer.domain.RaceView;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static com.lzalar.raceconsumer.constants.RaceViewTestContants.givenRaceView;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class RaceControllerTest extends BaseIntegrationTest {

    private static final String BASE_URL = "/api/v1/race";

    @Test
    @WithMockUser
    void getSingleRace() throws Exception {
        RaceView persistedRaceView = raceViewRepository.save(givenRaceView());
        MvcResult result = mockMvc.perform(get(BASE_URL+ "/" + persistedRaceView.getId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        RaceView raceView = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(raceView).isEqualTo(persistedRaceView);
    }
}
