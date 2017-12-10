var express = require('express');
var app = express();
var bodyParser = require('body-parser');

// Set port
var port = process.env.PORT || 3001;

app.use(bodyParser.urlencoded({ extended: false } ));

app.use('/', express.static('public'));

// Create webserver instance
var server = app.listen(port, () => {
    console.log('Starting consumer, listening on: ' + port);
});

// Socket IO
var io = require("socket.io")(server);

// Kafka client
var kafka = require('kafka-node'),
    Consumer = kafka.Consumer,
    client = new kafka.Client(),
    consumer = new Consumer(
        client,
        [
            {
                topic: 'findata',
                partition: 0
            }
        ],
        {
            autoCommit: false
        }
    );

var messageQueue = [];

// When new records come in, they will be displayed on the web-client
consumer.on('message', function (message) {
    messageQueue.push(message.value);
    io.sockets.emit("findata", message.value);
});

io.on('connection', (socket) => {
    // Show all
    for (message of messageQueue) {
        socket.emit("findata", message);
    }

    // Show only latest
    /*while (messageQueue.length > 0)
        socket.emit("findata", messageQueue.shift());*/

    socket.on('disconnect', () => {
        console.log('user disconnected');
    });
});