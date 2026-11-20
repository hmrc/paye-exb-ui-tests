/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.By
import uk.gov.hmrc.ui.pages.AuthWizard.{click, sendKeys}
import uk.gov.hmrc.ui.util.Env
import uk.gov.hmrc.ui.util.Users.{LoginTypes, UserTypes}
import scala.annotation.unused

object AuthWizard extends BasePage {

  override def pageUrl: String = s"${Env.baseUrl}/auth-login-stub/gg-sign-in"

  override def pageTitle: String = "auth login stub"

  val url: String = s"${Env.baseUrl}/auth-login-stub/gg-sign-in"

  val redirectUrl: By     = By.id("redirectionUrl")
  val affinityGroup: By   = By.id("affinityGroupSelect")
  val confidenceLevel: By = By.id("confidenceLevel")
  val nino: By            = By.id("nino")
  val enrolmentKey: By    = By.id(s"enrolment[0].name") // Enrolment Key
  val enrolmentId: By     = By.name(s"enrolment[0].taxIdentifier[0].name") // Identifier Name
  val enrolmentValue: By  = By.name(s"enrolment[0].taxIdentifier[0].value") // Identifier Value
  val enrolmentId1: By    = By.name("enrolment[0].taxIdentifier[1].name")
  val enrolmentValue1: By = By.name("enrolment[0].taxIdentifier[1].value")
  val addIdentifier: By   = By.id("add-ident-btn-0")
  val oAuthIdToken: By    = By.id("idToken")
  val credId: By          = By.id("authorityId")
  val saPreset: By        = By.id("presets-dropdown")
  val btnSubmit: By       = By.id("submit")

  def buildRedirectUrl(
    @unused loginType: LoginTypes,
    @unused userType: UserTypes
  ): String =
    if (
      Env.baseUrl.equals(
        s"http://localhost:9949/auth-login-stub/gg-sign-in?continue=http://localhost:20113/pay-as-you-earn-expenses-benefits-frontend"
      )
    ) s"http://localhost:20113/pay-as-you-earn-expenses-benefits-frontend"
    else s"/pay-as-you-earn-expenses-benefits-frontend"

  def fillInputs(userType: UserTypes): this.type = {
    userType match {
      case UserTypes.Agent =>
        driver.findElement(affinityGroup).sendKeys("Agent")
        fillPayeEnrolmentAgent()

      case UserTypes.Organisation =>
        driver.findElement(affinityGroup).sendKeys("Organisation")
        fillPayeEnrolmentOrg()
    }
    this
  }

  private def fillPayeEnrolmentAgent(): Unit = {
    driver.findElement(enrolmentKey).sendKeys("IR-PAYE-AGENT")
    driver.findElement(enrolmentId).sendKeys("IRAgentReference")
    driver.findElement(enrolmentValue).sendKeys("AA1216")
  }

  private def fillPayeEnrolmentOrg(): Unit = {
    driver.findElement(enrolmentKey).sendKeys("IR-PAYE")
    driver.findElement(enrolmentId).sendKeys("TaxOfficeNumber")
    driver.findElement(enrolmentValue).sendKeys("754")

    click(addIdentifier)
    driver.findElement(enrolmentId1).sendKeys("TaxOfficeReference")
    driver.findElement(enrolmentValue1).sendKeys("BZ00056")
  }

  def login(loginType: LoginTypes, userType: UserTypes): Unit = {
    AuthWizard.navigateToPage(url)
    sendKeys(redirectUrl, buildRedirectUrl(loginType, userType))
    fillInputs(userType)
    click(btnSubmit)
  }
}
