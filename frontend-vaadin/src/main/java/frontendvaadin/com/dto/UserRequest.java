package frontendvaadin.com.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email must be a valid email address")
    private String email;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10,13}$", message = "Phone must be a valid phone number")
    private String phone;

}
