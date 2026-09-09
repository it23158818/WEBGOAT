/*
 * SPDX-FileCopyrightText: Copyright © 2026 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.csrf;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.owasp.webgoat.container.plugins.LessonTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ForgedReviewsTest extends LessonTest {

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  void missingAntiCsrfTokenShouldFail() throws Exception {
    mockMvc
        .perform(
            post("/csrf/review")
                .param("reviewText", "Test review")
                .param("stars", "5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(false)));
  }

  @Test
  void validAntiCsrfTokenHeaderShouldPassValidation() throws Exception {
    mockMvc
        .perform(
            post("/csrf/review")
                .param("reviewText", "Test review")
                .param("stars", "5")
                .header("X-CSRF-TOKEN", "2aa14227b9a13d0bede0388a7fba9aa9")
                .header("host", "localhost:8080")
                .header("referer", "http://webgoat.org/other"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(true)));
  }
}
