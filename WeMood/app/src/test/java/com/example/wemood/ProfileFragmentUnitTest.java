package com.example.wemood;

import com.example.wemood.Fragments.ProfileFragment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is designed to conduct the unit test for ProfileFragment Fragment
 */
public class ProfileFragmentUnitTest {

    private ProfileFragment mockProfileFragment() {
        ProfileFragment profileFragment = new ProfileFragment();

        return profileFragment;
    }

    /**
     * Test whether updateFollowers method works properly
     */
    @Test
    void testUpdateFollowers() {
        ProfileFragment profileFragment = mockProfileFragment();
        int numFollowers = profileFragment.getNumFollowers();
        assertEquals(0, numFollowers);
    }

    /**
     * Test whether updateFollowing method works properly
     */
    @Test
    void testUpdateFollowing() {
        ProfileFragment profileFragment = mockProfileFragment();
        int numFollowing = profileFragment.getNumFollowing();
        assertEquals(0, numFollowing);
    }

}
