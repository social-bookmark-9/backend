package com.sparta.backend.controller;

import com.sparta.backend.service.ArticleService;
import com.sparta.backend.service.ReminderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.ManyToOne;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ArticleController.class)
@DisplayName("ArticleController 테스트")
public class ArticleControllerTesMvc {
    private MockMvc mvc;

    @MockBean
    private ArticleService articleService;
    @MockBean
    private ReminderService reminderService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(new ArticleController(articleService, reminderService))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("아티클 상세 페이지 가져오기 (JSON)")
    void getArticleTest() {
        // given

    }
}
