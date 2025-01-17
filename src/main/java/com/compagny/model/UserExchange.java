package com.compagny.model;


import com.compagny.model.type.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserExchange {
    private String id;
    private UserType type;
    private LocalDateTime registrationDate;
}