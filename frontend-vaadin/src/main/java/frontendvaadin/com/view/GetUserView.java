package frontendvaadin.com.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import frontendvaadin.com.dto.ApiResponse;
import frontendvaadin.com.dto.UserResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@Route("user")
@PageTitle("User Details")
public class GetUserView extends VerticalLayout implements HasUrlParameter<Long> {

    private final RestTemplate restTemplate;
    private final TextField nameField = new TextField("Full Name");
    private final TextField emailField = new TextField("Email");
    private final TextField phoneField = new TextField("Phone");
    private Long userId;

    public GetUserView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        Button printButton = new Button("Print", e -> printReportById(userId));
        printButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button backButton = new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate(UserView.class)));

        FormLayout formLayout = createFormLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout(backButton, printButton);
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

        nameField.setReadOnly(true);
        emailField.setReadOnly(true);
        phoneField.setReadOnly(true);
        return formLayout;
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long userId) {
        if (userId != null) {
            this.userId = userId;
            String url = "http://localhost:8080/api/users/" + userId;
            ApiResponse<UserResponse> response = restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<ApiResponse<UserResponse>>() {}).getBody();
            if (response != null && response.getData() != null) {
                UserResponse user = response.getData();
                nameField.setValue(user.getName());
                emailField.setValue(user.getEmail());
                phoneField.setValue(user.getPhone());
            }
        }
    }

    private void printReportById(Long userId) {
        String url = "http://localhost:8080/reports/export-report/" + userId;

        UI.getCurrent().getPage().executeJs("window.location.href = $0;", url);
        Notification.show("Report is being downloaded...", 3000, Notification.Position.TOP_CENTER);
    }


}
