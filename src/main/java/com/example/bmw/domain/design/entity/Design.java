package com.example.bmw.domain.design.entity;

import com.example.bmw.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "design")
public class Design {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String designName;

    @Column
    private String designUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Design(String designName, String designUrl, User user){
        this.designName = designName;
        this.designUrl = designUrl;
        this.user = user;
    }
}
