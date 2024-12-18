import Keycloak from "keycloak-js";

const keycloakBaseUrl = process.env.KEYCLOAK_URL;
const backendBaseUrl = process.env.BACKEND_URL;
const headerId = "header";
const getRealmsBtnId = "getRealmsBtn";
const createRealmBtnId = "createRealmBtn";
const deleteRealmBtnId = "deleteRealmBtn";
const logoutBtnId = "logoutBtn";
const inputRealmNameId = "inputRealmName";
const realmsListId = "realmsList";
let userProfile;

document.addEventListener("DOMContentLoaded", async function() {
  // Initialize Keycloak
  const keycloak = new Keycloak({
    url: keycloakBaseUrl,
    realm: "my-realm",
    clientId: "js-client"
  });
  console.log(keycloak);

  await keycloak.init({
    onLoad: "login-required" //check-sso | login-required
  }).then(async authenticated => {
    console.log(authenticated);
    if (authenticated) {
      userProfile = await keycloak.loadUserProfile();
      document.getElementById(headerId).innerText = `Hello ${userProfile.firstName} ${userProfile.lastName}`;
    }
  }).catch(error => {
    console.log(error);
  });

  document.getElementById(getRealmsBtnId).addEventListener("click", function () {
    let list = document.getElementById(realmsListId);
    list.innerHTML = "";

    fetch(`${backendBaseUrl}/admin/realms`, {
      method: "GET"
    }).then(res => res.json())
      .then(data => {
        for (const realm of data) {
          console.log(realm)
          const listItem = document.createElement("li");
          listItem.innerText = `Id: ${realm.id}\nName: ${realm.realm}`;
          listItem.classList.add("list-group-item");
          list.appendChild(listItem);
        }
      })
      .catch(e => {
        console.error(e);
      });
  });

  document.getElementById(createRealmBtnId).addEventListener("click", function () {
    let realmName = document.getElementById(inputRealmNameId).value;

    fetch(`${backendBaseUrl}/admin/realms/${realmName}`, {
      method: "POST"
    }).then(res => {
      console.log(res);
      document.getElementById(getRealmsBtnId).click();
    }).catch(e => {
      console.error(e);
    });
  });

  document.getElementById(deleteRealmBtnId).addEventListener("click", function () {
    let realmName = document.getElementById(inputRealmNameId).value;

    fetch(`${backendBaseUrl}/admin/realms/${realmName}`, {
      method: "DELETE"
    }).then(res => {
      console.log(res);
      document.getElementById(getRealmsBtnId).click();
    }).catch(e => {
      console.error(e);
    });
  });

  document.getElementById(logoutBtnId).addEventListener("click", async function () {
    await keycloak.logout();
  });
});
