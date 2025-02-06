package backendspringboot.com.service.impl;

import backendspringboot.com.entity.User;
import backendspringboot.com.repository.UserRepository;
import backendspringboot.com.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;

    public ReportServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private ResponseEntity<?> generateReport(String reportTemplate, Map<String, Object> parameters, List<User> users, String fileName) {
        try {
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
            InputStream reportStream = getClass().getClassLoader().getResourceAsStream(reportTemplate);

            if (reportStream == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("report template file not found.");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String timestamp = now.format(formatter);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + fileName + "_" + timestamp + ".pdf");
            headers.add("Content-Type", "application/pdf");

            ByteArrayResource resource = new ByteArrayResource(pdfBytes);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        } catch (JRException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("report generation failed: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> exportReport() {
        List<User> users = userRepository.findAll();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "User Report");

        return generateReport("reports/reports-users.jrxml", parameters, users, "ReportAllUser");
    }

    @Override
    public ResponseEntity<?> exportReportById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("user not found with ID: " + userId);
        }

        User user = userOptional.get();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "User Report");
        parameters.put("UserId", userId);

        return generateReport("reports/reports-user-by-id.jrxml", parameters, Collections.singletonList(user), "ReportUser");
    }
}
