export interface ChatMessage {
  content: string;
  sender: string;
  type: MessageType;
}

export enum MessageType {
  CHAT,
  LEAVE,
  JOIN,
}
