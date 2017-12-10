var socket = io.connect('http://localhost:3001');

socket.on('findata', function(data){
    var dataContainer = document.createElement('div');
    dataContainer.innerHTML = data.toString();

    var content = document.getElementById('content');
    content.insertBefore(dataContainer, content.firstChild);
});
