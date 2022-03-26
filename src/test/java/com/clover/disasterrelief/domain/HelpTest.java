package com.clover.disasterrelief.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.clover.disasterrelief.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HelpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Help.class);
        Help help1 = new Help();
        help1.setId("id1");
        Help help2 = new Help();
        help2.setId(help1.getId());
        assertThat(help1).isEqualTo(help2);
        help2.setId("id2");
        assertThat(help1).isNotEqualTo(help2);
        help1.setId(null);
        assertThat(help1).isNotEqualTo(help2);
    }
}
