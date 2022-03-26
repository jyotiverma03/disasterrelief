package com.clover.disasterrelief.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clover.disasterrelief.IntegrationTest;
import com.clover.disasterrelief.domain.Help;
import com.clover.disasterrelief.repository.HelpRepository;
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
 * Integration tests for the {@link HelpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HelpResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/helps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private MockMvc restHelpMockMvc;

    private Help help;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Help createEntity() {
        Help help = new Help().firstName(DEFAULT_FIRST_NAME).lastName(DEFAULT_LAST_NAME).mobileNo(DEFAULT_MOBILE_NO);
        return help;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Help createUpdatedEntity() {
        Help help = new Help().firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).mobileNo(UPDATED_MOBILE_NO);
        return help;
    }

    @BeforeEach
    public void initTest() {
        helpRepository.deleteAll();
        help = createEntity();
    }

    @Test
    void createHelp() throws Exception {
        int databaseSizeBeforeCreate = helpRepository.findAll().size();
        // Create the Help
        restHelpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(help)))
            .andExpect(status().isCreated());

        // Validate the Help in the database
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeCreate + 1);
        Help testHelp = helpList.get(helpList.size() - 1);
        assertThat(testHelp.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testHelp.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testHelp.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
    }

    @Test
    void createHelpWithExistingId() throws Exception {
        // Create the Help with an existing ID
        help.setId("existing_id");

        int databaseSizeBeforeCreate = helpRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHelpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(help)))
            .andExpect(status().isBadRequest());

        // Validate the Help in the database
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = helpRepository.findAll().size();
        // set the field null
        help.setFirstName(null);

        // Create the Help, which fails.

        restHelpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(help)))
            .andExpect(status().isBadRequest());

        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = helpRepository.findAll().size();
        // set the field null
        help.setLastName(null);

        // Create the Help, which fails.

        restHelpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(help)))
            .andExpect(status().isBadRequest());

        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllHelps() throws Exception {
        // Initialize the database
        helpRepository.save(help);

        // Get all the helpList
        restHelpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(help.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)));
    }

    @Test
    void getHelp() throws Exception {
        // Initialize the database
        helpRepository.save(help);

        // Get the help
        restHelpMockMvc
            .perform(get(ENTITY_API_URL_ID, help.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(help.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO));
    }

    @Test
    void getNonExistingHelp() throws Exception {
        // Get the help
        restHelpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewHelp() throws Exception {
        // Initialize the database
        helpRepository.save(help);

        int databaseSizeBeforeUpdate = helpRepository.findAll().size();

        // Update the help
        Help updatedHelp = helpRepository.findById(help.getId()).get();
        updatedHelp.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).mobileNo(UPDATED_MOBILE_NO);

        restHelpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHelp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHelp))
            )
            .andExpect(status().isOk());

        // Validate the Help in the database
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeUpdate);
        Help testHelp = helpList.get(helpList.size() - 1);
        assertThat(testHelp.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testHelp.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testHelp.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
    }

    @Test
    void putNonExistingHelp() throws Exception {
        int databaseSizeBeforeUpdate = helpRepository.findAll().size();
        help.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHelpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, help.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(help))
            )
            .andExpect(status().isBadRequest());

        // Validate the Help in the database
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchHelp() throws Exception {
        int databaseSizeBeforeUpdate = helpRepository.findAll().size();
        help.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHelpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(help))
            )
            .andExpect(status().isBadRequest());

        // Validate the Help in the database
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamHelp() throws Exception {
        int databaseSizeBeforeUpdate = helpRepository.findAll().size();
        help.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHelpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(help)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Help in the database
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateHelpWithPatch() throws Exception {
        // Initialize the database
        helpRepository.save(help);

        int databaseSizeBeforeUpdate = helpRepository.findAll().size();

        // Update the help using partial update
        Help partialUpdatedHelp = new Help();
        partialUpdatedHelp.setId(help.getId());

        partialUpdatedHelp.firstName(UPDATED_FIRST_NAME);

        restHelpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHelp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHelp))
            )
            .andExpect(status().isOk());

        // Validate the Help in the database
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeUpdate);
        Help testHelp = helpList.get(helpList.size() - 1);
        assertThat(testHelp.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testHelp.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testHelp.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
    }

    @Test
    void fullUpdateHelpWithPatch() throws Exception {
        // Initialize the database
        helpRepository.save(help);

        int databaseSizeBeforeUpdate = helpRepository.findAll().size();

        // Update the help using partial update
        Help partialUpdatedHelp = new Help();
        partialUpdatedHelp.setId(help.getId());

        partialUpdatedHelp.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).mobileNo(UPDATED_MOBILE_NO);

        restHelpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHelp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHelp))
            )
            .andExpect(status().isOk());

        // Validate the Help in the database
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeUpdate);
        Help testHelp = helpList.get(helpList.size() - 1);
        assertThat(testHelp.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testHelp.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testHelp.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
    }

    @Test
    void patchNonExistingHelp() throws Exception {
        int databaseSizeBeforeUpdate = helpRepository.findAll().size();
        help.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHelpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, help.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(help))
            )
            .andExpect(status().isBadRequest());

        // Validate the Help in the database
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchHelp() throws Exception {
        int databaseSizeBeforeUpdate = helpRepository.findAll().size();
        help.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHelpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(help))
            )
            .andExpect(status().isBadRequest());

        // Validate the Help in the database
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamHelp() throws Exception {
        int databaseSizeBeforeUpdate = helpRepository.findAll().size();
        help.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHelpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(help)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Help in the database
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteHelp() throws Exception {
        // Initialize the database
        helpRepository.save(help);

        int databaseSizeBeforeDelete = helpRepository.findAll().size();

        // Delete the help
        restHelpMockMvc
            .perform(delete(ENTITY_API_URL_ID, help.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Help> helpList = helpRepository.findAll();
        assertThat(helpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
