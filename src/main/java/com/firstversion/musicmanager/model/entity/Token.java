package com.firstversion.musicmanager.model.entity;

import com.firstversion.musicmanager.model.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column(nullable = false)
    private String token;
    @ManyToOne
    @Column(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private TokenType tokenType;  // ACCESS, REFRESH

    private boolean expired;

    private boolean revoked;

    // getters and setters
}
