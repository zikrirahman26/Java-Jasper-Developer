package backendspringboot.com;

import backendspringboot.com.entity.User;
import backendspringboot.com.repository.UserRepository;
import backendspringboot.com.service.impl.ReportServiceImpl;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ReportServiceImplTest {

    @InjectMocks
    private ReportServiceImpl reportService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExportReport() throws JRException {
        User user1 = User.builder().id(1L).name("John").build();
        User user2 = User.builder().id(2L).name("Jane").build();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        ResponseEntity<?> result = reportService.exportReport();
        assertNotNull(result);
    }

    @Test
    public void testExportReport_EmptyList() throws JRException {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> result = reportService.exportReport();

        assertNotNull(result);
    }

    @Test
    public void testExportReportById_UserNotFound() throws JRException {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = reportService.exportReportById(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("user not found with ID: 1", response.getBody());
    }

    @Test
    public void testExportReportById_Success() {
        Long userId = 1L;
        User user = User.builder().id(userId).name("John").build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = reportService.exportReportById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ByteArrayResource);
    }

    @Test
    public void testExportReportById_ReportGenerationFailed() {
        Long userId = 1L;
        User user = User.builder().id(userId).name("John").build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = reportService.exportReportById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().toString().contains("report generation failed"));
    }
}
