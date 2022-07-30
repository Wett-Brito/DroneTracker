# Trabalho de Integrações

    Esse projeto é responsável por gerenciar drones.
    Através de algumas medições é validado se o ambiente 
    está nas condições ideais.

    O sistema possui dois sistema, um sendd o producer
    e outro sendo o consumer.
    
    Producer:
        Producer é responsável por fazer o cadastro dos drones
        E também por parar os drone.

        Para criar um drone deve-se usar a rota:
        /drone-alert/start-drone
        
        Para desativar um drone deve-se usar a rota:
        /drone-alert/stop-drone/id/{id}

        É possível possuir multiplos drone, mas os
        drones precisam ter id diferentes.

        Um exemplo de entrada para criar um drone é 
        {
            "id_drone": 22,
            "longitude": 100,
            "latitude": 200,
            "temperatura": 50,
            "umidade": 10,
	        "ativarRastreamento": 1
        }

        Através de um gerador aleatório que é executado a cada
        10 segundos, um acumulativo de perigo de umidade ou
        temperatura é acionado.
        Caso o drone identifique o perigo é enviado uma mensagem
        para o consumer.

        Para funcionar é necessário inserir as configurações no
        properties
        spring.rabbitmq.addresses=
        drone.tracker.master.queue=
        drone.tracker.master.exchange=

    Consumer:
        O consumer recebe uma mensagem quando o drone detecta o
        perigo e é necessário desativar.
        
        Para isso, é lida a mensagem e enviado um email.

        Para funcionar é necessário inserir as configurações
        no properties
    
        spring.rabbitmq.addresses=
        spring.rabbitmq.listener.simple.retry.enabled=true
        spring.rabbitmq.listener.simple.retry.initial-interval=3s
        spring.rabbitmq.listener.simple.retry.max-attempts=3
        spring.rabbitmq.listener.simple.retry.max-interval=3s
        spring.rabbitmq.listener.simple.retry.multiplier=1
        
        spring.mail.host=smtp.office365.com
        spring.mail.port=587
        spring.mail.username=
        spring.mail.password=
        spring.mail.properties.mail.smtp.auth=true
        spring.mail.properties.mail.smtp.starttls.enable=true
        spring.mail.properties.mail.smtp.starttls.required=true
        spring.mail.properties.mail.smtp.ssl.enable=false
        
        drone.tracker.master.queue=
        
        server.port=8083

        email.to=
        email.from=

        Os campos email.to e email.from, estão com um email "dronetrackerfiap@hotmail.com"
        gerado exclusivamente para esse projeto, aonde foi
        configurado para conseguir enviar email a partir desse
        do spring.mail.

        
    

    
    