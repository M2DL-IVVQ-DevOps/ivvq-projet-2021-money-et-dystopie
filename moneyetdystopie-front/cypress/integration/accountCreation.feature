Feature: Form Create New Account

  I want to check form account creation behavior

  Scenario: Submit an empty form with customer account
    Given the account creation form
    When i select customer account
    When i submit account creation form
    Then an error should be displayed on the account creation form

  Scenario: Submit an empty form with seller account
    Given the account creation form
    When i select seller account
    When i submit account creation form
    Then an error should be displayed on the account creation form

  Scenario: Submit a form without lastName for a seller account
    Given the account creation form
    When i select seller account
    When i fill the firstName with "Christophe" in account creation form
    When i fill the email with "christophe@castaner.com" in account creation form
    When i fill the password with "alexandreBenalla1" in account creation form
    When i fill the shop with "Matraque Shop" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form without firstName for a seller account
    Given the account creation form
    When i select seller account
    When i fill the lastName with "Balkany" in account creation form
    When i fill the email with "patoche@balkanouille.com" in account creation form
    When i fill the password with "Argent794" in account creation form
    When i fill the shop with "Prison" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form without email for a seller account
    Given the account creation form
    When i select seller account
    When i fill the lastName with "Kosciusko-Morizet" in account creation form
    When i fill the firstName with "Nathalie" in account creation form
    When i fill the password with "pygargue1843" in account creation form
    When i fill the shop with "DC Comics" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form with forbidden email for a seller account
    Given the account creation form
    When i select seller account
    When i fill the lastName with "Kosciusko-Morizet" in account creation form
    When i fill the firstName with "Nathalie" in account creation form
    When i fill the email with "ceci n'est pas un email" in account creation form
    When i fill the password with "pygargue1843" in account creation form
    When i fill the shop with "DC Comics" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form without password for a seller account
    Given the account creation form
    When i select seller account
    When i fill the lastName with "Tibery" in account creation form
    When i fill the firstName with "Jean" in account creation form
    When i fill the email with "tibery98@paris.com" in account creation form
    When i fill the shop with "Velib" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form without shop for a seller account
    Given the account creation form
    When i select seller account
    When i fill the lastName with "Valls" in account creation form
    When i fill the firstName with "Manuel" in account creation form
    When i fill the email with "manuel@valls.nc" in account creation form
    When i fill the password with "quarante-neuf-trois-493" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form without lastName for a customer account
    Given the account creation form
    When i select customer account
    When i fill the firstName with "Valérie" in account creation form
    When i fill the email with "valerie.pecresse@gmail.com" in account creation form
    When i fill the password with "viveLePétrole67" in account creation form
    When i fill the address with "Plateforme Offshore qqpart dans la Baltique" in account creation form
    When i fill the nickName with "Valoche" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form without firstName for a customer account
    Given the account creation form
    When i select customer account
    When i fill the lastName with "Schiappa" in account creation form
    When i fill the email with "egalite@france.fr" in account creation form
    When i fill the password with "enfaitsijesuisuneplanquée1" in account creation form
    When i fill the address with "Cachée quelque part" in account creation form
    When i fill the nickName with "Marloche" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form without email for a customer account
    Given the account creation form
    When i select customer account
    When i fill the lastName with "Fillon" in account creation form
    When i fill the firstName with "François" in account creation form
    When i fill the password with "jiraijusquaubout" in account creation form
    When i fill the address with "Au bout" in account creation form
    When i fill the nickName with "Normalement j'aurais du gagner" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form with forbidden email for a customer account
    Given the account creation form
    When i select customer account
    When i fill the lastName with "Fillon" in account creation form
    When i fill the firstName with "François" in account creation form
    When i fill the email with "Je me suis trompé en tapant le mail" in account creation form
    When i fill the password with "jiraijusquaubout" in account creation form
    When i fill the address with "Au bout" in account creation form
    When i fill the nickName with "Normalement j'aurais du gagner" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form without password for a customer account
    Given the account creation form
    When i select customer account
    When i fill the lastName with "Trump" in account creation form
    When i fill the firstName with "Donald" in account creation form
    When i fill the email with "donald.trump@georgia.us" in account creation form
    When i fill the address with "White House" in account creation form
    When i fill the nickName with "Stopthecount" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form without address for a customer account
    Given the account creation form
    When i select customer account
    When i fill the lastName with "Taubira" in account creation form
    When i fill the firstName with "Christiane" in account creation form
    When i fill the email with "christiane.taubira@yahoo.fr" in account creation form
    When i fill the password with "barreau" in account creation form
    When i fill the nickName with "Phoenix Wright" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a form without nickName for a customer account
    Given the account creation form
    When i select customer account
    When i fill the lastName with "Juppé" in account creation form
    When i fill the firstName with "Alain" in account creation form
    When i fill the email with "argent@finance.fr" in account creation form
    When i fill the password with "Password1" in account creation form
    When i fill the address with "Bordeaux (toute la ville)" in account creation form
    When i submit account creation form
    Then an error should be displayed on the account creation form
    Then success popup not visible

  Scenario: Submit a valid form for a customer account
    Given the account creation form
    When i select customer account
    When i fill the lastName with "Balkany" in account creation form
    When i fill the firstName with "Isabelle" in account creation form
    When i fill the email with "valoche@balkany.fr" in account creation form
    When i fill the password with "Argent" in account creation form
    When i fill the nickName with "nickname" in account creation form
    When i fill the address with "Plateau de TPMP" in account creation form
    When i submit valid account creation form
    Then no error should be displayed on the account creation form
    Then success popup visible

  Scenario: Submit a valid form for a seller account
    Given the account creation form
    When i select seller account
    When i fill the lastName with "Delga" in account creation form
    When i fill the firstName with "Carole" in account creation form
    When i fill the email with "carloche@delga.occitanie" in account creation form
    When i fill the password with "dommagepour2021" in account creation form
    When i fill the shop with "Occitanie Midi-Pyrénnées" in account creation form
    When i submit valid account creation form
    Then no error should be displayed on the account creation form
    Then success popup visible

  Scenario: Submit a valid form for a customer and seller account
    Given the account creation form
    When i select seller account
    When i select customer account
    When i fill the lastName with "Lassalle" in account creation form
    When i fill the firstName with "Jean" in account creation form
    When i fill the email with "filsdeberger@chevre.com" in account creation form
    When i fill the password with "ViveLeBearn" in account creation form
    When i fill the nickName with "Jeanloche" in account creation form
    When i fill the address with "Pyrénnées Atlantiques (Tout le département)" in account creation form
    When i fill the shop with "Béarnet" in account creation form
    When i submit valid account creation form
    Then no error should be displayed on the account creation form
    Then success popup visible
