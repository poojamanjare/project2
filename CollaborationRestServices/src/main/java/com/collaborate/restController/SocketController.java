package com.collaborate.restController;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.collaborate.model.Chat;

@Controller
public class SocketController 
{
	private static final Log logger=LogFactory.getLog(SocketController.class);
	private List<String> users=new ArrayList<String>();//to add users-list of userid
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	
	public SocketController(SimpMessagingTemplate messagingTemplate) 
	{
		this.messagingTemplate=messagingTemplate;
	}
	
	//==============================================================================
	@SubscribeMapping("/join/{userId}")
	public List<String>join(@DestinationVariable("userId") String userId)
	{
		System.out.println("88888888888888888888888888");
		if(!users.contains(userId))
			users.add(userId);	//adding a newly joined userId to the list
		
		System.out.println("========JOIN==========="+userId);
		messagingTemplate.convertAndSend("/topic/join", userId);	//to all other users in chatroom
		return users;	//to newly joined user
		
	}
	//=============================================================================
	@MessageMapping(value="/chat")		//iff the msg is snd to dest
	public void chatRecevied(Chat chat)
	{
		if("all".equals(chat.getTo()))	//group chat
		{
			System.out.println("IN CHAT REVEIEVD "+ chat.getMessage()+" "+chat.getFrom()+" to "+ chat.getTo());
			messagingTemplate.convertAndSend("/queue/chats", chat);
		}
		else	//private chat
		{
			System.out.println("CHAT TO " + chat.getTo() + " From " + chat.getFrom() + " Message " + chat.getMessage());
			messagingTemplate.convertAndSend("/queue/chats/"+chat.getFrom(), chat);
			messagingTemplate.convertAndSend("/queue/chats/"+chat.getTo(), chat);
		}
	}
}