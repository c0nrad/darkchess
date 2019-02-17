
var DEVELOPMENT_BASE_URL = "http://localhost:8080";
var PRODUCTION_BASE_URL = "";

var BASE_URL = "";
if (process.env.NODE_ENV == "development") {
    BASE_URL = DEVELOPMENT_BASE_URL;
} else {
    BASE_URL = PRODUCTION_BASE_URL;
}

export default BASE_URL