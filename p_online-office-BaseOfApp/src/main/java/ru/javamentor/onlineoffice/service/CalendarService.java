package ru.javamentor.onlineoffice.service;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class CalendarService {

    private final static Log logger = LogFactory.getLog(CalendarService.class);
    private static final String APPLICATION_NAME = "";
    private static HttpTransport httpTransport;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static com.google.api.services.calendar.Calendar client;

    GoogleClientSecrets clientSecrets;
    GoogleAuthorizationCodeFlow flow;
    Credential credential;

    @Value("${google.client.client-id}")
    private String clientId;
    @Value("${google.client.client-secret}")
    private String clientSecret;
    @Value("${google.client.redirectUri}")
    private String redirectURI;

    public String code;

    DateTime now = new DateTime(System.currentTimeMillis());

    public void setCode(String code) throws IOException {
        this.code = code;
        getToken();
    }

    public void getToken() throws IOException {
        TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();
        credential = flow.createAndStoreCredential(response, "userID");
        client = new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }

    public String authorize() throws Exception {
        AuthorizationCodeRequestUrl authorizationUrl;
        if (flow == null) {
            GoogleClientSecrets.Details web = new GoogleClientSecrets.Details();
            web.setClientId(clientId);
            web.setClientSecret(clientSecret);
            clientSecrets = new GoogleClientSecrets().setWeb(web);
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets,
                    Collections.singleton(CalendarScopes.CALENDAR)).build();
        }
        authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectURI);
        System.out.println("cal authorizationUrl->" + authorizationUrl);
        return authorizationUrl.build();
    }

    public List<Event> eventList(String calendar) throws IOException {
        Events events = client.events().list(calendar)
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        return events.getItems();
    }

    public void createEvent(String calendar, String summary, DateTime dateTimeStart, DateTime dateTimeEnd) throws IOException {
        Event event = new Event().setSummary(summary).setStart(new EventDateTime().setDateTime(dateTimeStart)).setEnd(new EventDateTime().setDateTime(dateTimeEnd));
        client.events().insert(calendar, event).execute();
    }

    public void createEvent(String calendar, String summary, String description, DateTime dateTimeStart, DateTime dateTimeEnd) throws IOException {
        Event event = new Event().setSummary(summary).setDescription(description).setStart(new EventDateTime().setDateTime(dateTimeStart)).setEnd(new EventDateTime().setDateTime(dateTimeEnd));
        client.events().insert(calendar, event).execute();
    }

    public List<CalendarListEntry> getCalendarList() throws IOException {
        return client.calendarList().list().execute().getItems();
    }
}
