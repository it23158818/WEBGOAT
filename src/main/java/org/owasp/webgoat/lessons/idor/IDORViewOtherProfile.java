/*
 * SPDX-FileCopyrightText: Copyright © 2014 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.idor;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import lombok.extern.slf4j.Slf4j;
import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.owasp.webgoat.container.session.LessonSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AssignmentHints({
  "idor.hints.otherProfile1",
  "idor.hints.otherProfile2",
  "idor.hints.otherProfile3",
  "idor.hints.otherProfile4",
  "idor.hints.otherProfile5",
  "idor.hints.otherProfile6",
  "idor.hints.otherProfile7",
  "idor.hints.otherProfile8",
  "idor.hints.otherProfile9"
})
public class IDORViewOtherProfile implements AssignmentEndpoint {

  private final LessonSession userSessionData;

  public IDORViewOtherProfile(LessonSession userSessionData) {
    this.userSessionData = userSessionData;
  }

  @GetMapping(
      path = "/IDOR/profile/{userId}",
      produces = {"application/json"})
  @ResponseBody
  public AttackResult completed(@PathVariable("userId") String userId) {
    Object obj = userSessionData.getValue("idor-authenticated-as");
    String authUserId = (String) userSessionData.getValue("idor-authenticated-user-id");

    if (obj == null || authUserId == null) {
      return failed(this).feedback("Access Denied: Authentication required to view profile.").build();
    }

    UserProfile requestedProfile = new UserProfile(userId);
    boolean isResourceOwner = requestedProfile.isOwner(authUserId);

    // Enforce resource ownership authorization check
    if (!isResourceOwner) {
      return failed(this)
          .feedback("Access Denied: You are not authorized to view another user's profile.")
          .build();
    }

    if (requestedProfile.getUserId() != null && requestedProfile.getUserId().equals(userId)) {
      return success(this)
          .feedback("idor.view.profile.success")
          .output(requestedProfile.profileToMap().toString())
          .build();
    }
    return failed(this).feedback("idor.view.profile.close2").build();
  }
}
