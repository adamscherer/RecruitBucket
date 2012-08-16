package com.annconia.api.repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;


@Repository
public class InMemoryChatRepository implements ChatRepository {

	private final ConcurrentHashMap<String, List<String>> messages = new ConcurrentHashMap<String, List<String>>();

	public List<String> getMessages(String topic, int messageIndex) {
		List<String> chatMessages = this.messages.get(topic);
		if (chatMessages == null) {
			return Collections.<String> emptyList();
		} else {
			Assert.isTrue((messageIndex >= 0) && (messageIndex <= chatMessages.size()), "Invalid messageIndex");
			return chatMessages.subList(messageIndex, chatMessages.size());
		}
	}

	public void addMessage(String topic, String message) {
		initChat(topic);
		this.messages.get(topic).add(message);
	}

	private void initChat(String topic) {
		if (!this.messages.containsKey(topic)) {
			this.messages.putIfAbsent(topic, new CopyOnWriteArrayList<String>());
		}
	}

}