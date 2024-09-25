let http = require('http'); //http module
let fs = require('fs'); //FileSystem Module

http.createServer(function (request, response) {
    response.writeHead(200, {"Content-Type": "text/html"});
    console.log(request.url); //replace with writing on custom.log file.
    if ((request.url == "/index.html") || (request.url == "/index")) {
        sendFileContent(response, "index.html", "text/html");   //send first 
    }
    else if ((request.url == "/second.html") || (request.url == "/second")) {
        sendFileContent(response, "second.html", "text/html");   //send second (test).
    }
    else {
        response.writeHead(500, {"Content-Type": "text/html"});
        response.write('<h1>Hello There! This is a test!</h1>');
        response.end();
    }
}).listen(7777);

console.log("Server listening at http://localhost:7777");

//function for sending files from filesystem or showing error message.
function sendFileContent(res, fileName, contentType){
    fs.readFile(fileName, function(err, data) {
        if (err) {
            res.writeHead(404);
            res.write("Not Found");
        }
        else {
            res.writeHead(200,{"Content-Type": contentType});
            res.write(data);
        }
    });
}

//function for User Authentication.

//function for managing database.

//function for managing cookies.

//function for using payement systems.