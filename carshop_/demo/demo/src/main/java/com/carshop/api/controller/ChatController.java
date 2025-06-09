package com.carshop.api.controller;

import com.carshop.api.dto.ConversationView;
import com.carshop.api.model.Conversation;
import com.carshop.api.model.Message;
import com.carshop.api.repository.ConversationRepository;
import com.carshop.api.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ChatController {

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private ConversationRepository convRepo;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ConversationView>> getUserConversations(@PathVariable Long id) {
        return ResponseEntity.ok(convRepo.getUserConversationsFullView(id));
    }


    @GetMapping("/{id}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long id) {
        return ResponseEntity.ok(messageRepo.findByConversationId(id));
    }



    // Enviar mensagem de texto
    @PostMapping("/{id}/messages")
    public ResponseEntity<?> sendTextMessage(@PathVariable Long id, @RequestBody Message msg) {
        msg.setConversationId(id);
        msg.setCreatedAt(LocalDateTime.now());
        messageRepo.save(msg);

        // Atualiza o timestamp da conversa
        convRepo.findById(id).ifPresent(conv -> {
            conv.setLastMessageAt(LocalDateTime.now());
            convRepo.save(conv);
        });

        return ResponseEntity.ok("Mensagem de texto enviada");
    }

    // Enviar vídeo
    @PostMapping(value = "/{id}/messages/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sendVideoMessage(
            @PathVariable Long id,
            @RequestParam("senderId") Long senderId,
            @RequestParam("video") MultipartFile videoFile
    ) {
        try {
            if (videoFile.getSize() > 100 * 1024 * 1024) {
                return ResponseEntity.badRequest().body("Vídeo ultrapassa 100 MB");
            }

            Message msg = new Message();
            msg.setConversationId(id);
            msg.setSenderId(senderId);
            msg.setType("video");
            msg.setContentBlob(videoFile.getBytes());
            msg.setCreatedAt(LocalDateTime.now());

            messageRepo.save(msg);

            // Atualiza o timestamp da conversa
            convRepo.findById(id).ifPresent(conv -> {
                conv.setLastMessageAt(LocalDateTime.now());
                convRepo.save(conv);
            });

            return ResponseEntity.ok("Vídeo enviado");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar vídeo");
        }
    }

    @PostMapping
    public ResponseEntity<Conversation> createConversation(@RequestBody Conversation conv) {
        // Verifica se a conversa já existe
        Conversation existing = convRepo.findByBuyerIdAndSellerIdAndItemId(
                conv.getBuyerId(),
                conv.getSellerId(),
                conv.getItemId()
        );

        if (existing != null) {
            return ResponseEntity.ok(existing); // Já existe, retorna ela
        }

        conv.setLastMessageAt(LocalDateTime.now());
        return ResponseEntity.ok(convRepo.save(conv)); // Não existe, salva nova
    }


}
