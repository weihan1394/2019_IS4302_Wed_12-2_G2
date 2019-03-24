// var express = require('express');
// var proxy = require('express-http-proxy');
// var https = require('https')

// let app = express();
// let port = process.env.PORT || 8323;

// app.use((req, res) => {
//     res.writeHead(200);
//     res.end("hello world\n");
//   });

// app.listen(port, () => console.log('started server.. localhost:8323/'));

var https = require('https');
var fs = require('fs');
var express = require('express');

var options = {
    key: fs.readFileSync( './certbot/localhost.key' ),
    cert: fs.readFileSync( './certbot/localhost.cert' ),
    requestCert: false,
    rejectUnauthorized: false
};

var app = express();
var port = process.env.PORT || 8323;
var server = https.createServer( options, app );

app.use((req, res) => {
    res.writeHead(200);
    res.end("hello world\n");
  });

server.listen( port, function () {
    console.log( 'Express server listening on port ' + server.address().port );
} );