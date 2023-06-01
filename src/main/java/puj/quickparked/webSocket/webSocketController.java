package puj.quickparked.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class webSocketController implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;
    public webSocketController(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(), "/ParquederosRT").setAllowedOrigins("*");
        registry.addHandler(new webSocketPagos(), "/PagosRT").setAllowedOrigins("*");
    }
}
