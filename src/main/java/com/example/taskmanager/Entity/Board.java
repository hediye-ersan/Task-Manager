package com.example.taskmanager.Entity;


import jakarta.persistence.*;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "boards", schema = "taskmanager")

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Board her zaman bir User ile ilişkilendirilecekse
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardColumn> boardColumns = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BoardColumn> getBoardColumns() {
        return boardColumns;
    }

    public void setBoardColumns(List<BoardColumn> boardColumns) {
        this.boardColumns = boardColumns;
    }
}
