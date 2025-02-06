package frontendvaadin.com.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import frontendvaadin.com.dto.ApiResponse;
import frontendvaadin.com.dto.UserResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Route("users")
@PageTitle("Users")
public class UserView extends VerticalLayout {

    private final RestTemplate restTemplate;
    private final Grid<UserResponse> grid = new Grid<>(UserResponse.class);

    public UserView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        Button addUserButton = new Button("Add User", e -> getUI().ifPresent(ui -> ui.navigate(AddUserView.class)));
        addUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button printButton = new Button("Print", e -> printReport());
        printButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        configureGrid();
        refreshUsers();

        add(addUserButton, grid, printButton);
    }

    private void configureGrid() {
        grid.setColumns("name", "email", "phone");
        grid.getColumnByKey("name").setHeader("Full Name").setSortable(true);
        grid.getColumnByKey("email").setHeader("Email Address").setSortable(true);
        grid.getColumnByKey("phone").setHeader("Phone Number").setSortable(true);

        grid.addComponentColumn(user -> {
            Button viewButton = new Button("View", e -> getUI().ifPresent(ui -> ui.navigate(GetUserView.class, user.getId())));
            viewButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Button editButton = new Button("Edit", e -> getUI().ifPresent(ui -> ui.navigate(UpdateUserView.class, user.getId())));
            editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            return new com.vaadin.flow.component.orderedlayout.HorizontalLayout(viewButton, editButton);
        }).setHeader("Actions");
    }

    private void refreshUsers() {
        String url = "http://localhost:8080/api/users";

        ApiResponse<List<UserResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ApiResponse<List<UserResponse>>>() {}
        ).getBody();

        if (response != null && response.getData() != null) {
            grid.setItems(response.getData());
        }
    }

    private void printReport() {
        String url = "http://localhost:8080/reports/export-report";

        UI.getCurrent().getPage().executeJs("window.location.href = $0;", url);
        Notification.show("Report is being downloaded...", 3000, Notification.Position.TOP_CENTER);
    }
}
