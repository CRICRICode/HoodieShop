
export const ADDRESS_AUTHENTICATION_SERVER = "http://localhost:8080";
export const ADDRESS_BACK_END = "http://localhost:8082"
export const REALM = "SecurityShop";
export const CLIENT_ID = "client_rest_api";
export const REQUEST_LOGIN = ADDRESS_AUTHENTICATION_SERVER+"/realms/" + REALM + "/protocol/openid-connect/token";
export const REQUEST_LOGOUT = ADDRESS_AUTHENTICATION_SERVER+"/realms/" + REALM + "/protocol/openid-connect/logout";

