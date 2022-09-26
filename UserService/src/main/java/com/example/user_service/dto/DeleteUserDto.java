package com.example.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserDto {

    private Long oldUserId;
    private Long newUserId;

}
