import { Component, OnDestroy, OnInit } from '@angular/core';
import { Socket } from 'ngx-socket-io';
import { ChatMessage } from '../models/websocket';

@Component({
  selector: 'app-live-chat',
  templateUrl: './live-chat.component.html',
  styleUrls: ['./live-chat.component.css'],
})
export class LiveChatComponent implements OnInit, OnDestroy {
  messages: string[] = [];
  useralias!: string;
  message!: string;
  constructor(private socket: Socket) {}

  ngOnInit() {
    this.socket.connect();
    this.socket
      .fromEvent('/topic/public')
      .subscribe((msg: ChatMessage | any) => {
        this.messages.push(`${msg.sender} : ${msg.content}`);
      });
  }

  ngOnDestroy() {
    this.socket.disconnect();
  }

  register(useralias: string) {
    const chatMessage = {
      sender: useralias,
    };
  }

  sendMessage(message: string) {
    const chatMessage = {
      content: message,
    };

    this.socket.emit('/chat.send', chatMessage);
  }
}
