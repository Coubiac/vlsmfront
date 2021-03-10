package com.begr.vlsmfront.controller;


import com.begr.vlsmfront.configuration.WireMockInitializer;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {WireMockInitializer.class})
public class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WireMockServer wireMockServer;

    @AfterEach
    public void afterEach() {
        this.wireMockServer.resetAll();
    }


    @Test
    public void home_page_exist() throws Exception {
        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accueil"))
                .andExpect(content().string(containsString("Application de calcul de sous réseaux IPV4")));
    }

    @Test
    public void when_submit_form_page_is_rendered_with_subnets_attribute() throws Exception {
        /*
        On mock la réponse de l'API
        */
        this.wireMockServer.stubFor(
                WireMock.post("/")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("payload/post-response.json"))
        );


        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("cidr", "192.168.46.0/24"),
                        new BasicNameValuePair("subnets[0].name", "test"),
                        new BasicNameValuePair("subnets[0].neededSize", "25")
                )))))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("subnets"))
                .andExpect(content().string(containsString("Nouveau calcul")));
    }

    @Test
    public void with_a_wrong_input_the_page_is_rendered_with_a_flash_message() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("cidr", "192.168.46.0/24"),
                        new BasicNameValuePair("subnets[0].name", "test"),
                        new BasicNameValuePair("subnets[0].neededSize", "-1")
                )))))
                .andExpect(flash().attribute("error", containsString("La taille du réseau doit être positive")));
    }
    @Test
    public void when_api_respond_with_error_the_message_is_in_flashmessage() throws Exception {
        /*
        On mock la réponse de l'API
        */
        this.wireMockServer.stubFor(
                WireMock.post("/")
                        .willReturn(aResponse()
                                .withStatus(500)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("payload/post-error.json"))
        );
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("cidr", "192.168.46.0/24"),
                        new BasicNameValuePair("subnets[0].name", "test"),
                        new BasicNameValuePair("subnets[0].neededSize", "10")
                )))))
                .andExpect(flash()
                        .attribute("error",
                                containsString("The global Network is too small for containing those subnets")));

    }

}
