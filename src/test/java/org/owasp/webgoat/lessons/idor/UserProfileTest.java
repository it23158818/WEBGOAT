/*
 * SPDX-FileCopyrightText: Copyright © 2026 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.idor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UserProfileTest {

  @Test
  void testIsOwnerReturnsTrueForMatchingUserId() {
    UserProfile profile = new UserProfile("2342384");
    assertTrue(profile.isOwner("2342384"), "isOwner should return true when authUserId matches profile userId");
  }

  @Test
  void testIsOwnerReturnsFalseForDifferentUserId() {
    UserProfile profile = new UserProfile("2342384");
    assertFalse(profile.isOwner("2342388"), "isOwner should return false when authUserId does not match profile userId");
  }

  @Test
  void testIsOwnerReturnsFalseForNullAuthUserId() {
    UserProfile profile = new UserProfile("2342384");
    assertFalse(profile.isOwner(null), "isOwner should return false when authUserId is null");
  }
}
