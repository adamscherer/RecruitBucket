package com.annconia.api.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.context.request.async.DeferredResult;

import com.annconia.api.repository.ChatRepository;

public class ChatParticipant {

	private final String topic;

	private DeferredResult<List<String>> deferredResult;

	private int messageIndex;

	private final ChatRepository chatRepository;

	private final Object lock = new Object();

	/**
	 * Create a new instance.
	 */
	public ChatParticipant(String topic, ChatRepository chatRepository) {
		this.topic = topic;
		this.chatRepository = chatRepository;
	}

	/**
	 * Return messages starting at the specified index if any are available.
	 * Or hold off and use the DeferredResult when new messages arrive.
	 */
	public List<String> getMessages(DeferredResult<List<String>> deferredResult, int messageIndex) {
		synchronized (this.lock) {
			List<String> messages = this.chatRepository.getMessages(this.topic, messageIndex);
			this.deferredResult = messages.isEmpty() ? deferredResult : null;
			this.messageIndex = messageIndex;
			return messages;
		}
	}

	/**
	 * Handle a new chat message.
	 */
	public void handleMessage(String topic, String message) {
		if (!matchesTopic(topic) || (this.deferredResult == null)) {
			return;
		}
		synchronized (this.lock) {
			if (this.deferredResult != null) {
				List<String> messages = this.chatRepository.getMessages(this.topic, this.messageIndex);
				setResult(messages);
			}
		}
	}

	private void setResult(List<String> messages) {
		try {
			//this.deferredResult.trySet(messages);
		} finally {
			this.deferredResult = null;
		}
	}

	/**
	 * End the DeferredResult.
	 */
	public void exitChat() {
		setResult(Collections.<String> emptyList());
	}

	private boolean matchesTopic(String topic) {
		return this.topic.equals(topic);
	}

}