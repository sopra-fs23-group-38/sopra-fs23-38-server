package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Chat;
import ch.uzh.ifi.hase.soprafs23.repository.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
public class ChatService {

  private final Logger log = LoggerFactory.getLogger(ChatService.class);

  private final ChatRepository chatRepository;

  @Autowired
  public ChatService(@Qualifier("chatRepository") ChatRepository chatRepository) {
      this.chatRepository = chatRepository;
  }

  public Chat createChat(Chat newChat) {
    newChat.setCreateTime(new Date());
    newChat = chatRepository.save(newChat);
    chatRepository.flush();
    log.debug("Created Information for Chat: {}", newChat);
    return newChat;
  }

}
