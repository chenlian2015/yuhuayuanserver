<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<!--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Apache Tomcat WebSocket Examples: Chat</title>
    <style type="text/css">

    </style>
    <script type="application/javascript">
    "use strict";

    var Chat = {};

    Chat.socket = null;

    Chat.connect = (function(host) {
        if ('WebSocket' in window) {
            Chat.socket = new WebSocket(host);
        } else if ('MozWebSocket' in window) {
            Chat.socket = new MozWebSocket(host);
        } else {
            Console.log('Error: WebSocket is not supported by this browser.');
            return;
        }

        Chat.socket.onopen = function () {
            Console.log('Info: WebSocket connection opened.');
            document.getElementById('chat').onkeydown = function(event) {
                if (event.keyCode == 13) {
                    Chat.sendMessage();
                }
            };
        };

        Chat.socket.onclose = function () {
            document.getElementById('chat').onkeydown = null;
            Console.log('Info: WebSocket closed.');
        };

        Chat.socket.onmessage = function (message) {
            Console.log(message.data);
        };
    });

    Chat.initialize = function() {
        if (window.location.protocol == 'http:') {
            Chat.connect('ws://' + window.location.host + '/cl/websocket/chat');
        } else {
            Chat.connect('wss://' + window.location.host + '/cl/websocket/chat');
        }
    };

    Chat.sendMessage = (function() {
        var message = document.getElementById('chat').value;
        if (message != '') {
            Chat.socket.send(message);
            document.getElementById('chat').value = '';
        }
    });

    var Console = {};

    Console.log = (function(message) {
        var console = document.getElementById('console');
        var p = document.createElement('p');
        p.style.wordWrap = 'break-word';
        p.innerHTML = message;
        console.appendChild(p);
        while (console.childNodes.length > 25) {
            console.removeChild(console.firstChild);
        }
        console.scrollTop = console.scrollHeight;
    });

    Chat.initialize();


    document.addEventListener("DOMContentLoaded", function() {
        // Remove elements with "noscript" class - <noscript> is not allowed in XHTML
        var noscripts = document.getElementsByClassName("noscript");
        for (var i = 0; i < noscripts.length; i++) {
            noscripts[i].parentNode.removeChild(noscripts[i]);
        }
    }, false);

    </script>
</head>
<body>
<div class="noscript"><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websockets rely on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></div>
<div>
    <p>
        <input type="text" placeholder="type and press enter to chat" id="chat" />
    </p>
    <div id="console-container">
        <div id="console"/>
    </div>
</div>
</body>
</html>