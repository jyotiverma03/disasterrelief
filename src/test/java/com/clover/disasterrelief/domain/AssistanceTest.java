package com.clover.disasterrelief.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.clover.disasterrelief.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssistanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assistance.class);
        Assistance assistance1 = new Assistance();
        assistance1.setId("id1");
        Assistance assistance2 = new Assistance();
        assistance2.setId(assistance1.getId());
        assertThat(assistance1).isEqualTo(assistance2);
        assistance2.setId("id2");
        assertThat(assistance1).isNotEqualTo(assistance2);
        assistance1.setId(null);
        assertThat(assistance1).isNotEqualTo(assistance2);
    }
}
