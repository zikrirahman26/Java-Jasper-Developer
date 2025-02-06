package backendspringboot.com.controller;

import backendspringboot.com.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports/export-report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping()
    public ResponseEntity<?> generateReport() {
        return reportService.exportReport();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> generateReportById(@PathVariable Long userId) {
        return reportService.exportReportById(userId);
    }
}
