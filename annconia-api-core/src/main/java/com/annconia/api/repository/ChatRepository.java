package com.annconia.api.repository;

import java.util.List;

public interface ChatRepository {

	List<String> getMessages(String topic, int messageIndex);

	void addMessage(String topic, String message);

}