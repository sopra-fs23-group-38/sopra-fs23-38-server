package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Mockito.*;

public class AnswerTest {
    @Mock
    Date change_time;
    @InjectMocks
    Answer answer;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme