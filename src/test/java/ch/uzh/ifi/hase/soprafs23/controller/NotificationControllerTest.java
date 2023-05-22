package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import ch.uzh.ifi.hase.soprafs23.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationRepository mockNotificationRepository;

    @Test
    void testListNotifications() throws Exception {
        // Setup
        // Configure NotificationRepository.listNotifications(...).
        final Notification notification = new Notification();
        notification.setId(0);
        notification.setToUserId(0);
        notification.setUrl("url");
        notification.setContent("content");
        notification.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<Notification> notifications = List.of(notification);
        when(mockNotificationRepository.listNotifications(0)).thenReturn(notifications);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/notification/list")
                        .param("toUserId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"id\":0,\"toUserId\":0,\"url\":\"url\",\"content\":\"content\",\"createTime\":\"Jan 1, 2020, 12:00:00 AM\"}]");
    }

    @Test
    void testListNotifications_NotificationRepositoryReturnsNoItems() throws Exception {
        // Setup
        when(mockNotificationRepository.listNotifications(0)).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/notification/list")
                        .param("toUserId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":\"false\"}");
    }
}
