import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
    url: "http://localhost:8000",
    realm: "flowing",
    clientId: "drop"
});

window.onload = function() {
    keycloak.init({ onLoad: 'check-sso' }).then(authenticated => {
        if (authenticated) {
            // User is authenticated
            document.getElementById('message').textContent = `Hello, ${keycloak.tokenParsed.preferred_username}!`;
            document.getElementById('loginBtn').style.display = 'none';
            document.getElementById('logoutBtn').style.display = 'inline-block';
        } else {
            document.getElementById('message').textContent = 'You are not logged in.';
            document.getElementById('loginBtn').style.display = 'inline-block';
            document.getElementById('logoutBtn').style.display = 'none';
        }
    }).catch(error => {
        console.error("Keycloak initialization failed", error);
    });
};


document.getElementById('loginBtn').addEventListener('click', () => {
    keycloak.login();
});

document.getElementById('logoutBtn').addEventListener('click', () => {
    keycloak.logout();
});

/*
let userProfile = undefined;

keycloak.init({ onLoad: "login-required" }).then(async authenticated => {
    if (authenticated) {
        userProfile = await keycloak.loadUserProfile();
    }

    document.getElementById("readyButton").addEventListener("click", function() {
        console.log("HERE");

        if (!keycloak.authenticated) {
            keycloak.login();
        } else {
            message.style.color = "red";
            message.textContent = "Already answered the question";
        }
    });

    document.getElementById("secretButton").addEventListener("click", async function() {
        console.log("WHY DOES NOTHING WERK:");

        if (!keycloak.authenticated) {
            const message = document.getElementById("message");
            message.style.color = "red";
            message.textContent = "Answer the question first!";
            return;
        }

        const jwt = keycloak.token;
        const headers = {
            Authorization: `Bearer ${jwt}`,
            Accept: "application/json"
        };

        const response = await fetch("http://localhost:8080/api/secrets", { headers: headers });
        const message = document.getElementById("message");

        if (response.ok) {
            message.style.color = "green";
            message.textContent = "Nice work!";
        } else {
            message.style.color = "red";
            message.textContent = "Answer the question first!";
        }
    });
}).catch(console.error);
*/
