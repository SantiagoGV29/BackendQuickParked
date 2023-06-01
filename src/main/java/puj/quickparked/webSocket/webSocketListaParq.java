package puj.quickparked.webSocket;

import com.google.gson.Gson;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import puj.quickparked.domain.Mensaje;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class    webSocketListaParq extends TextWebSocketHandler {
    private static final Set<WebSocketSession> sessions = new HashSet<>();
    private static final Map<Long, WebSocketSession> parkingSessions = new HashMap<>();
    private static final Set<Object> objetosCola = new HashSet<>();
    private static final Gson gson = new Gson();
/*
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        if (!objetosCola.isEmpty()) {
            System.out.println("Hay objetos en la cola, se envían");
            for (Object objeto : objetosCola) {
                enviarActualizacion(objeto);
            }
            objetosCola.clear();
        }
        System.out.println("Nueva conexión: " + session.getId());
    }
*/
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String messageContent = message.getPayload();
        System.out.println("Mensaje recibido desde la aplicación móvil de admin: " + messageContent);
        Mensaje mensaje = gson.fromJson(messageContent, Mensaje.class);
        Long parqueaderoId = Long.valueOf(mensaje.getMensaje());
        parkingSessions.put(parqueaderoId, session);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    public static void enviarActualizacion(Object objeto, Long parqueaderoId) {
        String json;
        try {
            if (sessions.isEmpty()) {
                System.out.println("No hay sesiones activas, se agrega a la cola");
                objetosCola.add(objeto);
                return;
            }
            if (parkingSessions.containsKey(parqueaderoId)) {
                WebSocketSession session = parkingSessions.get(parqueaderoId);
                json = gson.toJson(objeto);
                session.sendMessage(new TextMessage(json));
                return;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
