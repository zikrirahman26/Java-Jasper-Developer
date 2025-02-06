package frontendvaadin.com.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import frontendvaadin.com.dto.ApiResponse;
import frontendvaadin.com.dto.UserRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@Route("update-user")
@PageTitle("Update User")
public class UpdateUserView extends VerticalLayout implements HasUrlParameter<Long> {

    private final RestTemplate restTemplate;
    private final TextField nameField = new TextField("Full Name");
    private final TextField emailField = new TextField("Email Address");
    private final TextField phoneField = new TextField("Phone Number");
    private Long userId;

    public UpdateUserView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        Button updateButton = new Button("Update", e -> updateUser());
        updateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button backButton = new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate(UserView.class)));

        FormLayout formLayout = createFormLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout(updateButton, backButton);
        buttonLayout.setWidthFull();

        add(formLayout, buttonLayout);
        setAlignItems(Alignment.START);
        setWidth("400px");
        formLayout.setWidth("100%");
    }

    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(nameField, emailField, phoneField);
        formLayout.setWidth("100%");
        return formLayout;
    }

    public void setParameter(BeforeEvent event, @OptionalParameter Long userId) {
        if (userId != null) {
            this.userId = userId;
            String url = "http://localhost:8080/api/users/" + userId;

            ApiResponse<UserRequest> response = restTemplate.exchange(
                    url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<ApiResponse<UserRequest>>() {}
            ).getBody();

            if (response != null && response.getData() != null) {
                UserRequest user = response.getData();
                nameField.setValue(user.getName());
                emailField.setValue(user.getEmail());
                phoneField.setValue(user.getPhone());
            }
        }
    }

    private void updateUser() {
        if (userId == null) {
            Notification.show("User ID is missing!", 3000, Notification.Position.TOP_CENTER);
            return;
        }

        UserRequest userRequest = new UserRequest();
        userRequest.setName(nameField.getValue());
        userRequest.setEmail(emailField.getValue());
        userRequest.setPhone(phoneField.getValue());

        String url = "http://localhost:8080/api/users/" + userId;
        HttpEntity<UserRequest> entity = new HttpEntity<>(userRequest);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, entity, ApiResponse.class);
            Notification.show("User updated successfully!", 3000, Notification.Position.TOP_CENTER);
            getUI().ifPresent(ui -> ui.navigate(UserView.class));
        } catch (Exception e) {
            Notification.show("An error occurred: " + e.getMessage(), 3000, Notification.Position.TOP_CENTER);
        }
    }
}
