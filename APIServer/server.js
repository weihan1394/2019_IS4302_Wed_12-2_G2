var express = require('express');
var proxy = require('express-http-proxy');

let app = express();
let port = process.env.PORT || 8323;

app.listen(port, () => console.log('started server.. localhost:8323/'));