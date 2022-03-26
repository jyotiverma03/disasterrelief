package com.clover.disasterrelief.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.clover.disasterrelief.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BaseModelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseModel.class);
        BaseModel baseModel1 = new BaseModel();
        baseModel1.setId("id1");
        BaseModel baseModel2 = new BaseModel();
        baseModel2.setId(baseModel1.getId());
        assertThat(baseModel1).isEqualTo(baseModel2);
        baseModel2.setId("id2");
        assertThat(baseModel1).isNotEqualTo(baseModel2);
        baseModel1.setId(null);
        assertThat(baseModel1).isNotEqualTo(baseModel2);
    }
}
