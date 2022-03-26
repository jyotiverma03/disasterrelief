package com.clover.disasterrelief.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.clover.disasterrelief.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserRewardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserReward.class);
        UserReward userReward1 = new UserReward();
        userReward1.setId("id1");
        UserReward userReward2 = new UserReward();
        userReward2.setId(userReward1.getId());
        assertThat(userReward1).isEqualTo(userReward2);
        userReward2.setId("id2");
        assertThat(userReward1).isNotEqualTo(userReward2);
        userReward1.setId(null);
        assertThat(userReward1).isNotEqualTo(userReward2);
    }
}
