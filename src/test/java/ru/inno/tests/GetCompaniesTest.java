package ru.inno.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.model.Company;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ru.inno.services.CompanyService.getCompanyListResponse;

@Epic("Работа с компаниями")
@Feature("Получение списка компаний")
@Story("Фильтрация списка компаний")
@Owner("Natalia Sterkhova")
@DisplayName("Проверка работы фильтров в запросе на получение списка компаний")
public class GetCompaniesTest {

    @Test
    @DisplayName("Проверка получения списка только активных компаний")
    @Description("Список компаний фильтруется по признаку `active` = true")
    @Severity(SeverityLevel.NORMAL)
    public void filterByStatusTest(){
        List<Company> companies = getCompanyListResponse("true")
                .then().log().all()
                .extract().jsonPath()
                .getList("$", Company.class);
        List<Company> activeCompanies = step("Получаем из ответа список компаний с признаком active = true", () ->
                companies.stream().filter(Company::getIsActive).toList()
        );
        step("Проверяем, что количество активных компаний в ответе метода и общее количество компаний в ответе совпадают", () ->{
            assertThat("Количество всех компаний в ответе и количество только активных компаний в ответе совпадают",
                    activeCompanies.size(), is(companies.size()));
        });
    }
}
