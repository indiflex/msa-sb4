package com.msa.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
    private String status;
    private Long userid;
    private String username;
    private String accountPasswd;
}
