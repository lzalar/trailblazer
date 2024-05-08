package com.lzalar.raceconsumer.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lzalar.raceconsumer.BaseIntegrationTest;
import com.lzalar.raceconsumer.domain.RaceApplicationView;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static com.lzalar.raceconsumer.constants.RaceApplicationViewTestContants.givenRaceApplicationView;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RaceApplicationControllerTest extends BaseIntegrationTest {

    private static final String BASE_URL = "/api/v1/race-application";

    @Test
    @WithMockUser
    void getRaceApplication() throws Exception {
        RaceApplicationView persistedRaceApplicationView = raceApplicationViewRepository.save(givenRaceApplicationView());
        MvcResult result = mockMvc.perform(get(BASE_URL+ "/" + persistedRaceApplicationView.getRaceApplicationId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        RaceApplicationView raceApplicationView = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(raceApplicationView).isEqualTo(persistedRaceApplicationView);
    }
}
