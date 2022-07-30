package fiap.drone.tracker.master.producer.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfiguration {

    @Value("${drone.tracker.master.queue}")
    private String queue;

    @Value("${drone.tracker.master.exchange}")
    private String exchange;

    /**
     * Bean necessário para criar o EXCHANGE da fila
     *
     * @return Exchange criado
     */
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    /**
     * Bean necessário para criar a fila e colocar os argumentos
     * para enviar para a deadletter as mensagens sem sucesso.
     *
     * @return bind
     */
    @Bean
    Queue queue() {
        return QueueBuilder.durable(queue).build();
    }

    /**
     * Bean necessário para criar ligação da fila com a EXCHANGE
     *
     * @return bind
     */
    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(queue);
    }
}
