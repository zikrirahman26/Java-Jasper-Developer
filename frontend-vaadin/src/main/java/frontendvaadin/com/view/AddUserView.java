package frontendvaadin.com.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.notification.Notification;
import frontendvaadin.com.dto.UserRequest;
import frontendvaadin.com.dto.ApiResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Route("add-user")
@PageTitle("Add User")
public class AddUserView extends VerticalLayout {

    private final RestTemplate restTemplate;
    private final TextField nameField;
    private final TextField emailField;
    private final TextField phoneField;

    public AddUserView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        nameField = new TextField("Full Name");
        emailField = new TextField("Email Address");
        phoneField = new TextField("Phone Number");

        Button saveButton = new Button("Save", e -> saveUser());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button backButton = new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate(UserView.class)));

        FormLayout formLayout = new FormLayout();
        formLayout.add(nameField, emailField, phoneField);

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, backButton);
        buttonLayout.setWidthFull();

        add(formLayout, buttonLayout);
        setAlignItems(Alignment.START);
        setWidth("400px");
        formLayout.setWidth("100%");
    }

    private void saveUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName(nameField.getValue());
        userRequest.setEmail(emailField.getValue());
        userRequest.setPhone(phoneField.getValue());

        String url = "http://localhost:8080/api/users";
        HttpEntity<UserRequest> entity = new HttpEntity<>(userRequest);

        try {
            ResponseEntity<ApiResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    ApiResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                getUI().ifPresent(ui -> ui.navigate(UserView.class));
            } else {
                String errorMessage = response.getBody() != null && response.getBody().getMessage() != null
                        ? response.getBody().getMessage()
                        : "An error occurred.";

                Notification.show(errorMessage, 3000, Notification.Position.TOP_CENTER);
            }
        } catch (Exception e) {
            Notification.show("An unexpected error occurred: " + e.getMessage(), 3000, Notification.Position.TOP_CENTER);
        }
    }

}
