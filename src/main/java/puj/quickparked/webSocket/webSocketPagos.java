package puj.quickparked.webSocket;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import puj.quickparked.domain.Mensaje;

import java.io.IOException;
import java.util.*;

@Component
public class webSocketPagos extends TextWebSocketHandler {
    private static final Set<WebSocketSession> sessions = new HashSet<>();
    private static final Map<Long, WebSocketSession> parkingSessions = new HashMap<>();
    private static final Map<Long, List<Object>> objetosCola = new HashMap<>();
    private static final Gson gson = new Gson();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("Nueva conexión: " + session.getId());
    }

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
            if(objetosCola.containsKey(parqueaderoId)){
                System.out.println("Hay objetos en la cola, se envían");
                for (Object obj : objetosCola.get(parqueaderoId)) {
                    WebSocketSession session = parkingSessions.get(parqueaderoId);
                    json = gson.toJson(objeto);
                    session.sendMessage(new TextMessage(json));
                }
                objetosCola.get(parqueaderoId).clear();
            }
            if (sessions.isEmpty()) {
                if (!objetosCola.containsKey(parqueaderoId)) {
                    objetosCola.put(parqueaderoId, new ArrayList<>());
                }
                if (!parkingSessions.containsKey(parqueaderoId)) {
                    System.out.println("No hay sesiones activas, se agrega a la cola");
                    objetosCola.get(parqueaderoId).add(objeto);
                }
            } else if (parkingSessions.containsKey(parqueaderoId)) {
                WebSocketSession session = parkingSessions.get(parqueaderoId);
                json = gson.toJson(objeto);
                session.sendMessage(new TextMessage(json));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
