package com.lzalar.raceconsumer.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lzalar.raceconsumer.BaseIntegrationTest;
import com.lzalar.raceconsumer.domain.RaceApplicationView;
import com.lzalar.raceconsumer.web.dto.RaceApplicationPerUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.lzalar.raceconsumer.constants.RaceApplicationViewTestContants.givenRaceApplicationView;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RaceApplicationControllerTest extends BaseIntegrationTest {

    private static final String BASE_URL = "/api/v1/race/application";

    @Test
    @WithMockUser
    void getRaceApplicationPerUser() throws Exception {
        RaceApplicationView persistedRaceApplicationView = raceApplicationViewRepository.save(givenRaceApplicationView());
        RaceApplicationPerUserDTO expected = new RaceApplicationPerUserDTO(persistedRaceApplicationView.getUserId(), List.of(persistedRaceApplicationView));
        MvcResult result = mockMvc.perform(get(BASE_URL + "/user/" + persistedRaceApplicationView.getUserId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        RaceApplicationPerUserDTO raceApplicationPerUserDTO = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(raceApplicationPerUserDTO).isEqualTo(expected);
    }

    @Test
    @WithMockUser
    void getRaceApplication() throws Exception {
        RaceApplicationView persistedRaceApplicationView = raceApplicationViewRepository.save(givenRaceApplicationView());
        MvcResult result = mockMvc.perform(get(BASE_URL + "/" + persistedRaceApplicationView.getRaceApplicationId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        RaceApplicationView raceApplicationView = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(raceApplicationView).isEqualTo(persistedRaceApplicationView);
    }
}
