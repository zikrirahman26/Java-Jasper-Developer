package backendspringboot.com.service;

import org.springframework.http.ResponseEntity;

public interface ReportService {

    ResponseEntity<?> exportReport();

    ResponseEntity<?> exportReportById(Long userId);
}
