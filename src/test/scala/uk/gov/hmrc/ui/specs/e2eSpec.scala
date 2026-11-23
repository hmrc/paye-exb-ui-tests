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

package uk.gov.hmrc.ui.specs

import org.openqa.selenium.By
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.verbs.ShouldVerb
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, GivenWhenThen}
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.ui.pages.*
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.{Agent, Individual, Organisation}
import uk.gov.hmrc.ui.tags.*
import uk.gov.hmrc.selenium.webdriver.Driver.instance

class e2eSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("paye-expenses-and-benefits End to End Journeys") {

    Scenario(
      "Login into PAYE-EXB as Agent",
      e2eJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Agent)
      Then("the PAYE-EXB page is shown")
      HomePage.verifyPageTitle(HomePage.pageTitle)
    }
    Scenario(
      "Login into PAYE-EXB as an Org",
      e2eJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation)
      Then("the PAYE-EXB page is shown")
      HomePage.verifyPageTitle(HomePage.pageTitle)
    }
  }
}
