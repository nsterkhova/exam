package ru.inno.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.DBQueries.CompanyTable;
import ru.inno.helpers.TokenHelper;
import ru.inno.model.Company;
import ru.inno.testData.CommonTestData;
import ru.inno.testData.CommonUsers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ru.inno.DBQueries.CompanyTable.deleteCompany;
import static ru.inno.services.DeleteCompanyService.deleteCompanyResponse;

@Epic("Работа с компаниями")
@Feature("Удаление компании")
@Story("Проверка атрибутов удаленной компании")
@Owner("Natalia Sterkhova")
@DisplayName("Проверка удаления компании")
public class DeleteCompanyTest {

    private final Company companyForTest = CommonTestData.NEW_COMPANY;
    private String token = "";

    @BeforeEach
    public void SetUp(){
        CompanyTable.createCompany(companyForTest);
        token = TokenHelper.generateToken(CommonUsers.ADMIN);
    }

    @AfterEach
    public void TearDown(){
        deleteCompany(companyForTest);
    }

    @Test
    @DisplayName("Проверка сохранения в БД даты удаления компании")
    @Description("Дата удаления компании должна фиксироваться в БД")
    @Severity(SeverityLevel.NORMAL)
    public void setDeletedAtAttributeTest() {
        deleteCompanyResponse(token, companyForTest)
                .then().log().all()
                .statusCode(200);
        Timestamp timestampFromDB = CompanyTable.getCompanyDeletedDate(companyForTest);
        assert timestampFromDB != null;
        checkDate(timestampFromDB);
    }

    @Step("Сравниваем дату удаления компании из БД с датой отправки запроса на удаление")
    private void checkDate(Timestamp timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFromDB = new Date(timestamp.getTime());
        OffsetDateTime localDateUTC = OffsetDateTime.now(ZoneOffset.UTC);
        Date localDate = new Date(localDateUTC.toInstant().toEpochMilli());
        assertThat("Дата удаления компании в БД совпадает с датой выполнения запроса на удаление компании",
                formatter.format(dateFromDB), is(formatter.format(localDate)));
    }
}
