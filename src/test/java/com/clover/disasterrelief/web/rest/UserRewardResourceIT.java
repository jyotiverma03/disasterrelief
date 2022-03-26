package com.clover.disasterrelief.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clover.disasterrelief.IntegrationTest;
import com.clover.disasterrelief.domain.UserReward;
import com.clover.disasterrelief.domain.enumeration.BadgeLevel;
import com.clover.disasterrelief.repository.UserRewardRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link UserRewardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserRewardResourceIT {

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final BadgeLevel DEFAULT_BADGE_LEVEL = BadgeLevel.SILVER;
    private static final BadgeLevel UPDATED_BADGE_LEVEL = BadgeLevel.GOLD;

    private static final String ENTITY_API_URL = "/api/user-rewards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UserRewardRepository userRewardRepository;

    @Autowired
    private MockMvc restUserRewardMockMvc;

    private UserReward userReward;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserReward createEntity() {
        UserReward userReward = new UserReward().rating(DEFAULT_RATING).userId(DEFAULT_USER_ID).badgeLevel(DEFAULT_BADGE_LEVEL);
        return userReward;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserReward createUpdatedEntity() {
        UserReward userReward = new UserReward().rating(UPDATED_RATING).userId(UPDATED_USER_ID).badgeLevel(UPDATED_BADGE_LEVEL);
        return userReward;
    }

    @BeforeEach
    public void initTest() {
        userRewardRepository.deleteAll();
        userReward = createEntity();
    }

    @Test
    void createUserReward() throws Exception {
        int databaseSizeBeforeCreate = userRewardRepository.findAll().size();
        // Create the UserReward
        restUserRewardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userReward)))
            .andExpect(status().isCreated());

        // Validate the UserReward in the database
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeCreate + 1);
        UserReward testUserReward = userRewardList.get(userRewardList.size() - 1);
        assertThat(testUserReward.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testUserReward.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserReward.getBadgeLevel()).isEqualTo(DEFAULT_BADGE_LEVEL);
    }

    @Test
    void createUserRewardWithExistingId() throws Exception {
        // Create the UserReward with an existing ID
        userReward.setId("existing_id");

        int databaseSizeBeforeCreate = userRewardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRewardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userReward)))
            .andExpect(status().isBadRequest());

        // Validate the UserReward in the database
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllUserRewards() throws Exception {
        // Initialize the database
        userRewardRepository.save(userReward);

        // Get all the userRewardList
        restUserRewardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userReward.getId())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].badgeLevel").value(hasItem(DEFAULT_BADGE_LEVEL.toString())));
    }

    @Test
    void getUserReward() throws Exception {
        // Initialize the database
        userRewardRepository.save(userReward);

        // Get the userReward
        restUserRewardMockMvc
            .perform(get(ENTITY_API_URL_ID, userReward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userReward.getId()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.badgeLevel").value(DEFAULT_BADGE_LEVEL.toString()));
    }

    @Test
    void getNonExistingUserReward() throws Exception {
        // Get the userReward
        restUserRewardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewUserReward() throws Exception {
        // Initialize the database
        userRewardRepository.save(userReward);

        int databaseSizeBeforeUpdate = userRewardRepository.findAll().size();

        // Update the userReward
        UserReward updatedUserReward = userRewardRepository.findById(userReward.getId()).get();
        updatedUserReward.rating(UPDATED_RATING).userId(UPDATED_USER_ID).badgeLevel(UPDATED_BADGE_LEVEL);

        restUserRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserReward.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserReward))
            )
            .andExpect(status().isOk());

        // Validate the UserReward in the database
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeUpdate);
        UserReward testUserReward = userRewardList.get(userRewardList.size() - 1);
        assertThat(testUserReward.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testUserReward.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserReward.getBadgeLevel()).isEqualTo(UPDATED_BADGE_LEVEL);
    }

    @Test
    void putNonExistingUserReward() throws Exception {
        int databaseSizeBeforeUpdate = userRewardRepository.findAll().size();
        userReward.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userReward.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userReward))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserReward in the database
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchUserReward() throws Exception {
        int databaseSizeBeforeUpdate = userRewardRepository.findAll().size();
        userReward.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userReward))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserReward in the database
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamUserReward() throws Exception {
        int databaseSizeBeforeUpdate = userRewardRepository.findAll().size();
        userReward.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRewardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userReward)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserReward in the database
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateUserRewardWithPatch() throws Exception {
        // Initialize the database
        userRewardRepository.save(userReward);

        int databaseSizeBeforeUpdate = userRewardRepository.findAll().size();

        // Update the userReward using partial update
        UserReward partialUpdatedUserReward = new UserReward();
        partialUpdatedUserReward.setId(userReward.getId());

        restUserRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserReward.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserReward))
            )
            .andExpect(status().isOk());

        // Validate the UserReward in the database
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeUpdate);
        UserReward testUserReward = userRewardList.get(userRewardList.size() - 1);
        assertThat(testUserReward.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testUserReward.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserReward.getBadgeLevel()).isEqualTo(DEFAULT_BADGE_LEVEL);
    }

    @Test
    void fullUpdateUserRewardWithPatch() throws Exception {
        // Initialize the database
        userRewardRepository.save(userReward);

        int databaseSizeBeforeUpdate = userRewardRepository.findAll().size();

        // Update the userReward using partial update
        UserReward partialUpdatedUserReward = new UserReward();
        partialUpdatedUserReward.setId(userReward.getId());

        partialUpdatedUserReward.rating(UPDATED_RATING).userId(UPDATED_USER_ID).badgeLevel(UPDATED_BADGE_LEVEL);

        restUserRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserReward.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserReward))
            )
            .andExpect(status().isOk());

        // Validate the UserReward in the database
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeUpdate);
        UserReward testUserReward = userRewardList.get(userRewardList.size() - 1);
        assertThat(testUserReward.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testUserReward.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserReward.getBadgeLevel()).isEqualTo(UPDATED_BADGE_LEVEL);
    }

    @Test
    void patchNonExistingUserReward() throws Exception {
        int databaseSizeBeforeUpdate = userRewardRepository.findAll().size();
        userReward.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userReward.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userReward))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserReward in the database
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchUserReward() throws Exception {
        int databaseSizeBeforeUpdate = userRewardRepository.findAll().size();
        userReward.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userReward))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserReward in the database
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamUserReward() throws Exception {
        int databaseSizeBeforeUpdate = userRewardRepository.findAll().size();
        userReward.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRewardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userReward))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserReward in the database
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteUserReward() throws Exception {
        // Initialize the database
        userRewardRepository.save(userReward);

        int databaseSizeBeforeDelete = userRewardRepository.findAll().size();

        // Delete the userReward
        restUserRewardMockMvc
            .perform(delete(ENTITY_API_URL_ID, userReward.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserReward> userRewardList = userRewardRepository.findAll();
        assertThat(userRewardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
