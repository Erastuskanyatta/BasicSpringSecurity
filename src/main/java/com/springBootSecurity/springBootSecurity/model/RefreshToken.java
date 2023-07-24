package com.springBootSecurity.springBootSecurity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "RefreshToken")
public class RefreshToken {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long tokenId;
    public  String refreshToken;
    public Instant expiryTime;

    @OneToOne
    @JoinColumn(name = "appUser_id" , referencedColumnName = "appUser_id")
    private AppUser appUser;
}
