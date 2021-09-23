package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferFromSecondToFirstCard() {
        var amount = 9999;
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        var initialBalanceToCard = dashboardPage.getFirstCardBalance();
        var initialBalanceFromCard = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.validChoosePay1();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataHelper.getSecondCard(), amount);
        var dashboardPage1 = transferPage.validPayCard();
        var actual = dashboardPage1.getFirstCardBalance();
        var expected = initialBalanceToCard + amount;
        var actual2 = dashboardPage1.getSecondCardBalance();
        var expected2 = initialBalanceFromCard - amount;
        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var amount = 999;
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        var initialBalanceToCard = dashboardPage.getSecondCardBalance();
        var initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        var transferPage = dashboardPage.validChoosePay2();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataHelper.getFirstCard(), amount);
        var dashboardPage1 = transferPage.validPayCard();
        var actual1 = dashboardPage1.getSecondCardBalance();
        var expected1 = initialBalanceToCard + amount;
        var actual2 = dashboardPage1.getFirstCardBalance();
        var expected2 = initialBalanceFromCard - amount;
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

//    @Test
//    void shouldNotTransferMoreThanLimit() {
//        var loginPage = new LoginPageV2();
//        var authInfo = DataHelper.getAuthInfo();
//        var verificationPage = loginPage.validLogin(authInfo);
//        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
//        var dashboardPage = verificationPage.validVerify(verificationCode);
//        dashboardPage.checkHeadingYourCards();
//        var initialBalanceFromCard = dashboardPage.getSecondCardBalance();
//        var transferPage = dashboardPage.validChoosePay1();
//        transferPage.checkHeadingPaymentCards();
//        var amount = 1 + initialBalanceFromCard;
//        transferPage.setPayCardNumber(DataHelper.getSecondCard(), amount);
//        transferPage.validPayExtendAmount();
//    }

//    @Test
//    void shouldNotTransferMoreThanLimit2() {
//        var loginPage = new LoginPageV2();
//        var authInfo = DataHelper.getAuthInfo();
//        var verificationPage = loginPage.validLogin(authInfo);
//        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
//        var dashboardPage = verificationPage.validVerify(verificationCode);
//        dashboardPage.checkHeadingYourCards();
//        var initialBalanceFromCard = dashboardPage.getFirstCardBalance();
//        var transferPage = dashboardPage.validChoosePay2();
//        transferPage.checkHeadingPaymentCards();
//        var amount = 1 + initialBalanceFromCard;
//        transferPage.setPayCardNumber(DataHelper.getFirstCard(), amount);
//        transferPage.validPayExtendAmount();
//    }

    @Test
    void shouldNotTransferFromInvalidCard() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        var transferPage = dashboardPage.validChoosePay1();
        transferPage.checkHeadingPaymentCards();
        var amount = 3000;
        transferPage.setPayCardNumber(DataHelper.getInvalidCard(), amount);
        transferPage.invalidPayCard();
    }

    @Test
    void shouldNotTransferFromInvalidCard2() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        var transferPage = dashboardPage.validChoosePay2();
        transferPage.checkHeadingPaymentCards();
        var amount = 900;
        transferPage.setPayCardNumber(DataHelper.getInvalidCard(), amount);
        transferPage.invalidPayCard();
    }
}
