package com.example.jwt_security.infrastructure;

import com.example.jwt_security.config.RabbitConfig;
import com.example.jwt_security.dto.EmployeeCreatedEventDTO;
import com.example.jwt_security.dto.EventEnvelope;
import com.example.jwt_security.service.impl.AuthService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class RabbitmqEventListener {

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    public  RabbitmqEventListener(AuthService authService, ObjectMapper objectMapper) {
        this.authService = authService;
        this.objectMapper = objectMapper;
    }
    private <T> T convert(Object data, Class<T> clazz) {
        return objectMapper.convertValue(data, clazz);
    }

    @RabbitListener(queues = RabbitConfig.PROFILE_ONBOARDING_QUEUE)
    public void employeeCreated(EventEnvelope<?> event){
        switch (event.eventType()){

            case "EMPLOYEE_CREATED" -> {
                EmployeeCreatedEventDTO data = convert(event.data(), EmployeeCreatedEventDTO.class);
                authService.createDefaultUser(data.email());
            }

            case "Employee_CREATED" -> {

            }
        }
    }


}
