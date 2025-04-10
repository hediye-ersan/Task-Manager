package com.example.taskmanager.Service;

import com.example.taskmanager.Entity.BoardColumn;
import com.example.taskmanager.Repository.BoardColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardColumnService {

    @Autowired
    private BoardColumnRepository boardColumnRepository;

    public BoardColumn createBoardColumn(BoardColumn boardColumn) {
        return boardColumnRepository.save(boardColumn);
    }

    public List<BoardColumn> getAllBoardColumns() {
        return boardColumnRepository.findAll();
    }

    public Optional<BoardColumn> getBoardColumnById(Long id) {
        return boardColumnRepository.findById(id);
    }

    public void deleteBoardColumn(Long id) {
        boardColumnRepository.deleteById(id);
    }


}
