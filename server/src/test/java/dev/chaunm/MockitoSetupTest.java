package dev.chaunm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MockitoSetupTest {
    @Mock
    private List<String> names;

    @Test
    void stubsAndVerifiesAMock() {
        when(names.get(0)).thenReturn("Ada");

        assertEquals("Ada", names.get(0));
        verify(names).get(0);
    }
}
