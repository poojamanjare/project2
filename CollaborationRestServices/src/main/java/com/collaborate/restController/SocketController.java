package com.collaborate.restController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.collaborate.model.Chat;

@Controller
public class SocketController 
{
	private List<String> users=new ArrayList<String>();//to add users-list of userid
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	//==============================================================================
	@SubscribeMapping("/join/{userId}")
	public List<String>join(@DestinationVariable("userId") String userId)
	{
		if(!users.contains(userId))
			users.add(userId);	//adding a newly joined userId to the list
		
		messagingTemplate.convertAndSend("/topic/join", userId);	//to all other users in chatroom
		return users;	//to newly joined user
		
	}
	//=============================================================================
	public void chatRecevied(Chat chat)
	{
		if("all".equals(chat.getTo()))	//group chat
		{
			messagingTemplate.convertAndSend("/queue/chats", chat);
		}
		else	//private chat
		{
			messagingTemplate.convertAndSend("/queue/chats"+chat.getFrom(), chat);
			messagingTemplate.convertAndSend("/queue/chats"+chat.getTo(), chat);
		}
	}
	
	

}
